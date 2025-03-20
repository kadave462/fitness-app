package com.example.myfitnessapp.ui.screens//package com.example.myfitnessapp.ui.screens
//
//import android.app.Activity
//import android.content.Intent
//import android.widget.Toast
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.layout.Column
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.myfitnessapp.R
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.common.api.ApiException
//import com.google.android.gms.tasks.Task
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.GoogleAuthProvider
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
//
//@Composable
//fun SignUpWithGoogleScreen() {
//    val context = LocalContext.current
//    val auth = Firebase.auth
//
//    // Initialize GoogleSignInOptions
//    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//        .requestIdToken(context.getString(R.string.web_client_id))
//        .requestEmail()
//        .build()
//
//    // Initialize GoogleSignInClient
//    val googleSignInClient = GoogleSignIn.getClient(context, gso)
//
//    val googleSignInLauncher = rememberLauncherFqorActivityResult(
//        contract = ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        val resultCode = result.resultCode // Capture resultCode for logging
//
//        if (resultCode == Activity.RESULT_OK) {
//            android.util.Log.d("GoogleSignInResult", "Result was OK (RESULT_OK), resultCode: $resultCode") // Log OK case
//            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//            handleSignInResult(task, auth, context)
//        } else {
//            android.util.Log.d("GoogleSignInResult", "Result was NOT OK (Cancelled?), resultCode: $resultCode") // Log Cancelled case
//            Toast.makeText(context, "Google Sign-in Cancelled", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    Column {
//        Text(text = "Sign Up with Google (GoogleSignInOptions)")
//
//        Button(onClick = {
//            signInGoogle(googleSignInClient, googleSignInLauncher)
//        }) {
//            Text(text = "Sign in with Google")
//        }
//
//        // Sign-Out Button
//        Button(onClick = {
//            auth.signOut()
//            googleSignInClient.signOut()
//            Toast.makeText(context, "Signed Out Successfully", Toast.LENGTH_SHORT).show()
//            // In a real app, you would typically navigate back to the Login/Signup screen after sign-out
//        }) {
//            Text(text = "Sign Out")
//        }
//    }
//}
//
//private fun signInGoogle(
//    googleSignInClient: GoogleSignInClient,
//    launcher: androidx.activity.result.ActivityResultLauncher<Intent>
//) {
//    val signInIntent = googleSignInClient.signInIntent
//    launcher.launch(signInIntent)
//}
//
//private fun handleSignInResult(
//    task: Task<GoogleSignInAccount>,
//    auth: FirebaseAuth,
//    context: android.content.Context
//) {
//    try {
//        val account: GoogleSignInAccount? = task.result
//        if (account != null) {
//            Toast.makeText(context, "Google Sign-in Successful!", Toast.LENGTH_SHORT).show() // <-- Added Toast here!
//            updateUI(account, auth, context) // Proceed to Firebase Authentication
//        }
//    } catch (e: ApiException) {
//        Toast.makeText(context, "Google Sign-in failed: ${e.message}", Toast.LENGTH_SHORT).show()
//    }
//}
//
//private fun updateUI(
//    account: GoogleSignInAccount,
//    auth: FirebaseAuth,
//    context: android.content.Context
//) {
//    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
//    auth.signInWithCredential(credential)
//        .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                Toast.makeText(context, "Firebase Authentication Successful!", Toast.LENGTH_SHORT).show()
//                // In a real app, navigate to home screen or next step
//            } else {
//                Toast.makeText(
//                    context,
//                    "Firebase Authentication Failed: ${task.exception?.message}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun SignupScreenGoogleSignInOptionsPreview() {
//    SignUpWithGoogleScreen()
//}