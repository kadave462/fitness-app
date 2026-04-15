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
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException

/**
 * Signs up the user using Google authentication.
 * Must be called inside a coroutine.
 */
suspend fun signUpWithGoogle(
    context: Context,
    credentialManager: CredentialManager,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    try {
        // Build the Google sign-up request
        val signUpRequest = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false) // Allow any Google account
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .setAutoSelectEnabled(true)
                    .setNonce(generateNonce())
                    .build()
            )
            .build()

        // Fetch credentials (Suspending function, must be inside a coroutine)
        val result = credentialManager.getCredential(context, signUpRequest)

        handleCredential(result) { success ->
            if (success) {
                // Show success message on the main thread
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(context, "✅ Sign-Up Successful!", Toast.LENGTH_SHORT).show()
                    Log.e("Auth", "⚠️ successful")
                    delay(1000L) // Wait 1 second
                    onSuccess()
                }
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(context, "❌ Authentication failed!", Toast.LENGTH_SHORT).show()
                    Log.e("Auth", "⚠️ authentication failed")
                    onFailure("Authentication failed!")
                }
            }
        }
    } catch (e: GetCredentialException) {
        CoroutineScope(Dispatchers.Main).launch {
            Log.e("Auth", "Sign up error: ${e.message}")
            Toast.makeText(context, "❌ Sign-Up failed!", Toast.LENGTH_SHORT).show()
        }
    }
}

/**
 * Handles the credential response.
 */
fun handleCredential(result: GetCredentialResponse, onResult: (Boolean) -> Unit) {
    val credential = result.credential
    Log.d("Auth", "Received Credential: ${credential.data}")

    when (credential) {
        is CustomCredential -> {
            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                try {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val idToken = googleIdTokenCredential.idToken
                    Log.d("Auth", "Google ID token received: $idToken")
                    onResult(true) // Success
                } catch (e: GoogleIdTokenParsingException) {
                    Log.e("Auth", "⚠️ Token parsing error: ${e.message}")
                    onResult(false)
                }
            } else {
                Log.e("Auth", "❌ Unexpected credential type: ${credential.type}")
                onResult(false)
            }
        }
        else -> {
            Log.e("Auth", "❌ Unrecognized credential type: ${credential.type}")
            onResult(false)
        }
    }
}

/**
 * Generates a 256-bit nonce and returns a URL-safe Base64 encoded string.
 */
fun generateNonce(): String {
    val secureRandom = java.security.SecureRandom()
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
