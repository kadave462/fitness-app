package com.example.myfitnessapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.myfitnessapp.R
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.ui.views.authenticateWithGoogle
import com.example.myfitnessapp.ui.views.storeGoogleUserInfo
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    navController: NavController,
    onUserAuthenticated: (User) -> Unit
){
val context = LocalContext.current
val coroutineScope = rememberCoroutineScope()

// Get database and DAO
val database = AppDatabase.getDatabase(context)
val userDao = database.getUserDao()

// Initialize CredentialManager
val credentialManager = remember { CredentialManager.create(context) }

// State for error message
var errorMessage by remember { mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Bienvenue", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("login_screen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Se connecter",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("signup_screen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Créer un compte",
                style = MaterialTheme.typography.headlineMedium
            )
        }


        Spacer(modifier = Modifier.height(10.dp))
        Text("Sign in with Google")
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = {
                coroutineScope.launch {
                    authenticateWithGoogle(
                        context = context,
                        credentialManager = credentialManager,
                        onSuccess = { userInfo ->
                            // First check if the user already exists in the database
                            coroutineScope.launch {
                                val existingUser = userDao.getUserByEmail(userInfo.email)

                                if (existingUser != null) {
                                    // User exists - Sign In case
                                    // Call the onUserAuthenticated callback with the existing user
                                    onUserAuthenticated(existingUser)

                                    // Then navigate to home screen
                                    navController.navigate("home_screen") {
                                        popUpTo("auth_screen") { inclusive = true }
                                    }
                                } else {
                                    // User doesn't exist - Sign Up case
                                    // Create a temporary password hash for Google users
                                    val tempPasswordHash = "google_auth_${System.currentTimeMillis()}"

                                    // Store Google user info for pre-filling registration form
                                    storeGoogleUserInfo(context, userInfo)

                                    // Navigate to registration screen with email and password hash
                                    navController.navigate("registration_screen/${userInfo.email}/$tempPasswordHash")
                                }
                            }
                        },
                        onFailure = { message ->
                            errorMessage = message
                        }
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Google")
            }
        }
    }
}