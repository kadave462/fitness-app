package com.example.myfitnessapp.ui.views

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.*
import com.example.myfitnessapp.R
import com.example.myfitnessapp.models.entities.User
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import org.json.JSONObject
import java.security.SecureRandom
import kotlin.random.Random

/**
 * Data class to hold Google user information aligned with your app's User model
 */
data class GoogleUserInfo(
    val id: String,
    val email: String,
    val displayName: String,  // Maps to pseudonym
    val givenName: String,    // Maps to firstName
    val familyName: String,   // Maps to lastName
    val pictureUrl: String    // Maps to profilePictureUri
)

/**
 * Converts GoogleUserInfo to a partial User object with a temporary password
 * The missing fields (weight, height, birthdate, etc.) will need to be filled by the user
 */
fun GoogleUserInfo.toPartialUser(tempPasswordHash: String): User {
    return User(
        id = Random.nextInt(1, Int.MAX_VALUE), // Generate random ID as in your RegistrationScreen
        email = this.email,
        passwordHash = tempPasswordHash,
        pseudonym = this.displayName,
        firstName = this.givenName,
        lastName = this.familyName,
        weight = 0.0,  // Default value, user will fill in
        height = 0,    // Default value, user will fill in
        birthdate = "", // Default value, user will fill in
        gender = "Homme", // Default value, matches your ViewModel default
        level = "Débutant", // Default value, matches your ViewModel default
        profilePictureUri = this.pictureUrl.takeIf { it.isNotEmpty() }
    )
}

/**
 * Main authentication function - handles both sign-in and sign-up with Google
 */
suspend fun authenticateWithGoogle(
    context: Context,
    credentialManager: CredentialManager,
    onSuccess: (GoogleUserInfo) -> Unit,
    onFailure: (String) -> Unit
) {
    try {
        // First try sign-in with existing accounts
        val signInResult = tryGoogleSignIn(context, credentialManager)

        if (signInResult != null) {
            // Successfully signed in with an existing account
            onSuccess(signInResult)
            return
        }

        // If sign-in fails, try sign-up with any Google account
        val signUpResult = tryGoogleSignUp(context, credentialManager)

        if (signUpResult != null) {
            // Successfully authenticated with a Google account
            onSuccess(signUpResult)
        } else {
            // Both sign-in and sign-up failed
            onFailure("Authentication failed")
        }
    } catch (e: GetCredentialException) {
        Log.e("Auth", "Google authentication error: ${e.message}")
        onFailure("Authentication failed: ${e.message}")
    }
}

/**
 * Attempts to sign in with an existing Google account previously used with this app
 */
private suspend fun tryGoogleSignIn(
    context: Context,
    credentialManager: CredentialManager
): GoogleUserInfo? {
    return try {
        // Create a sign-in request for previously authorized accounts only
        val signInRequest = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(true)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .setNonce(generateNonce())
                    .build()
            )
            .build()

        // Attempt to get credentials
        val result = credentialManager.getCredential(context, signInRequest)

        // Process the result
        var userInfo: GoogleUserInfo? = null
        handleCredentialWithUserInfo(result) { info ->
            userInfo = info
        }

        userInfo
    } catch (e: GetCredentialException) {
        Log.d("Auth", "Sign-in with existing account failed: ${e.message}")
        null
    }
}

/**
 * Attempts to sign up with any Google account
 */
private suspend fun tryGoogleSignUp(
    context: Context,
    credentialManager: CredentialManager
): GoogleUserInfo? {
    return try {
        // Create a sign-up request that shows all Google accounts
        val signUpRequest = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .setAutoSelectEnabled(false)
                    .setNonce(generateNonce())
                    .build()
            )
            .build()

        // Attempt to get credentials
        val result = credentialManager.getCredential(context, signUpRequest)

        // Process the result
        var userInfo: GoogleUserInfo? = null
        handleCredentialWithUserInfo(result) { info ->
            userInfo = info
        }

        if (userInfo != null) {
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(context, "✅ Google authentication successful", Toast.LENGTH_SHORT).show()
            }
        }

        userInfo
    } catch (e: GetCredentialException) {
        Log.e("Auth", "Sign-up with Google failed: ${e.message}")
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(context, "❌ Google authentication failed", Toast.LENGTH_SHORT).show()
        }
        null
    }
}

/**
 * Processes the credential response and extracts user information
 */
private fun handleCredentialWithUserInfo(result: GetCredentialResponse, onResult: (GoogleUserInfo?) -> Unit) {
    val credential = result.credential

    when (credential) {
        is CustomCredential -> {
            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                try {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val idToken = googleIdTokenCredential.idToken

                    // Extract user information from token
                    val userInfo = extractUserInfoFromIdToken(idToken)
                    onResult(userInfo)
                } catch (e: GoogleIdTokenParsingException) {
                    Log.e("Auth", "⚠️ Token parsing error: ${e.message}")
                    onResult(null)
                }
            } else {
                Log.e("Auth", "❌ Unexpected credential type: ${credential.type}")
                onResult(null)
            }
        }
        else -> {
            Log.e("Auth", "❌ Unrecognized credential type: ${credential.type}")
            onResult(null)
        }
    }
}

/**
 * Extracts user information from the JWT ID token
 */
private fun extractUserInfoFromIdToken(idToken: String): GoogleUserInfo? {
    return try {
        // ID Token is a JWT with 3 parts separated by dots
        val parts = idToken.split(".")
        if (parts.size != 3) {
            Log.e("Auth", "Invalid ID token format")
            return null
        }

        // Decode the payload (middle part)
        val payload = parts[1]
        val decodedBytes = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            java.util.Base64.getUrlDecoder().decode(payload)
        } else {
            android.util.Base64.decode(payload, android.util.Base64.URL_SAFE)
        }

        val decodedPayload = String(decodedBytes)
        val jsonObject = JSONObject(decodedPayload)

        GoogleUserInfo(
            id = jsonObject.optString("sub", ""),
            email = jsonObject.optString("email", ""),
            displayName = jsonObject.optString("name", ""),
            givenName = jsonObject.optString("given_name", ""),
            familyName = jsonObject.optString("family_name", ""),
            pictureUrl = jsonObject.optString("picture", "")
        )
    } catch (e: Exception) {
        Log.e("Auth", "Error extracting user info: ${e.message}")
        null
    }
}

/**
 * Generates a secure nonce for the authentication request
 */
private fun generateNonce(): String {
    val secureRandom = SecureRandom()
    val bytes = ByteArray(32)
    secureRandom.nextBytes(bytes)

    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    } else {
        android.util.Base64.encodeToString(
            bytes,
            android.util.Base64.URL_SAFE or android.util.Base64.NO_PADDING or android.util.Base64.NO_WRAP
        )
    }
}

/**
 * Stores Google user information in SharedPreferences for later use with registration
 */
fun storeGoogleUserInfo(context: Context, userInfo: GoogleUserInfo) {
    val sharedPreferences = context.getSharedPreferences("google_auth", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    editor.putString("google_id", userInfo.id)
    editor.putString("google_email", userInfo.email)
    editor.putString("google_display_name", userInfo.displayName)
    editor.putString("google_given_name", userInfo.givenName)
    editor.putString("google_family_name", userInfo.familyName)
    editor.putString("google_picture_url", userInfo.pictureUrl)

    editor.apply()
}

/**
 * Retrieves stored Google user information from SharedPreferences
 */
fun getGoogleUserInfo(context: Context): GoogleUserInfo? {
    val sharedPreferences = context.getSharedPreferences("google_auth", Context.MODE_PRIVATE)

    val id = sharedPreferences.getString("google_id", null) ?: return null
    val email = sharedPreferences.getString("google_email", null) ?: return null
    val displayName = sharedPreferences.getString("google_display_name", null) ?: ""
    val givenName = sharedPreferences.getString("google_given_name", null) ?: ""
    val familyName = sharedPreferences.getString("google_family_name", null) ?: ""
    val pictureUrl = sharedPreferences.getString("google_picture_url", null) ?: ""

    return GoogleUserInfo(
        id = id,
        email = email,
        displayName = displayName,
        givenName = givenName,
        familyName = familyName,
        pictureUrl = pictureUrl
    )
}

/**
 * Clears stored Google user information from SharedPreferences
 */
fun clearGoogleUserInfo(context: Context) {
    val sharedPreferences = context.getSharedPreferences("google_auth", Context.MODE_PRIVATE)
    sharedPreferences.edit().clear().apply()
}