package com.example.myfitnessapp.ui.screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myfitnessapp.R
import com.example.myfitnessapp.models.datas.User
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.viewmodels.repositories.ExerciseRepository
import com.google.android.gms.auth.api.identity.*
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

@Composable
fun SignUpWithGoogleScreen(
    modifiers: Modifiers,
    navController: NavHostController,
    user: User,
    repository: ExerciseRepository
) {
    val context = LocalContext.current
    val auth = remember { Firebase.auth }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val oneTapClient = remember(context) { Identity.getSignInClient(context) }

    // Google Sign-In Launcher
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        try {
            val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
            val googleIdToken = credential.googleIdToken

            if (googleIdToken != null) {
                val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                auth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navController.navigate("home_screen") {
                                popUpTo("sign_up_with_google_screen") { inclusive = true }
                            }
                        } else {
                            errorMessage = task.exception?.message ?: "Google Sign-in failed."
                        }
                    }
            } else {
                errorMessage = "Google Sign-in failed. No ID token received."
            }
        } catch (e: ApiException) {
            errorMessage = "Google Sign-in failed: ${e.localizedMessage ?: e.message ?: "Unknown error"}"
        } catch (e: Exception) {
            errorMessage = "Unexpected error: ${e.localizedMessage ?: e.message ?: "Unknown error"}"
        }
    }

    Column(modifier = modifiers.containerModifier) {
        Text(text = "Sign Up with Google")
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(context.getString(R.string.web_client_id))
                        .setFilterByAuthorizedAccounts(false)
                        .build()
                )
                .setAutoSelectEnabled(true)
                .build()

            oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener { result ->
                    try {
                        googleSignInLauncher.launch(
                            IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                        )
                    } catch (e: Exception) {
                        errorMessage = "Google Sign-in failed: ${e.message}"
                    }
                }
                .addOnFailureListener { e ->
                    errorMessage = "Google Sign-in failed: ${e.message}"
                }
        }) {
            Text(text = "Sign in with Google")
        }

        // Display error messages if any
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            LaunchedEffect(it) { errorMessage = null }
        }
    }
}
