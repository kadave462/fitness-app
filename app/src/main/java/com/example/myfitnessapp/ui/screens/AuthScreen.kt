package com.example.myfitnessapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.navigation.NavController
import com.example.myfitnessapp.R
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.viewmodels.utils.authenticateWithGoogle
import com.example.myfitnessapp.viewmodels.utils.storeGoogleUserInfo
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    navController: NavController,
    onUserAuthenticated: (User) -> Unit
){
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val database = AppDatabase.getDatabase(context)
    val userDao = database.getUserDao()

    val credentialManager = remember { CredentialManager.create(context) }

    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        Image(
            painter = painterResource(id = R.drawable.fitness_illu),
            contentDescription = "Illustration de l'application",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .height(300.dp)
        )

        Text(
            text = "L'appli qui te fera sortir de ton canapé !",
            style = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Center)
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        Button(
            onClick = { navController.navigate("login_screen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Se connecter",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.fillMaxHeight(0.01f))

        Button(
            onClick = { navController.navigate("signup_screen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Créer un compte",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.fillMaxHeight(0.02f))

        OutlinedButton(
            onClick = {
                coroutineScope.launch {
                    authenticateWithGoogle(
                        context = context,
                        credentialManager = credentialManager,
                        onSuccess = { userInfo ->
                            coroutineScope.launch {
                                val existingUser = userDao.getUserByEmail(userInfo.email)

                                if (existingUser != null) {
                                    onUserAuthenticated(existingUser)

                                    navController.navigate("home_screen") {
                                        popUpTo("auth_screen") { inclusive = true }
                                    }
                                } else {
                                    val tempPasswordHash = "google_auth_${System.currentTimeMillis()}"

                                    storeGoogleUserInfo(context, userInfo)

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