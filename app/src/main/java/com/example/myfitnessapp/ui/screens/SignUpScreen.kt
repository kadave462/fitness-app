package com.example.myfitnessapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.viewmodels.repositories.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SignupScreen(navController: NavController) {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val authRepository = remember { AuthRepository(database.getUserDao()) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Inscription", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            visualTransformation = PasswordVisualTransformation()
        )

        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                val success = authRepository.registerUser(email, password)
                withContext(Dispatchers.Main) {
                    if (success) {
                        navController.navigate("RegistrationScreen/$email") {
                            popUpTo("SignupScreen") { inclusive = true }
                        }
                    } else {
                        errorMessage = "Email déjà utilisé"
                    }
                }
            }
        }) {
            Text("Continuer")
        }

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red)
        }
    }
}