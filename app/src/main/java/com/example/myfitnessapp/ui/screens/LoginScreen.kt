package com.example.myfitnessapp.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myfitnessapp.R
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.viewmodels.repositories.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(navController: NavController, onLoginSuccess: (User) -> Unit) {
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
        Text(text = "Login", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Mot de passe") }, visualTransformation = PasswordVisualTransformation())

        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val user = authRepository.loginUser(email, password)
                    withContext(Dispatchers.Main) {
                        if (user != null) {
                            onLoginSuccess(user)
                            navController.navigate("home_screen") {
                                popUpTo("LoginScreen") { inclusive = true }
                            }
                        } else {
                            errorMessage = "Identifiants incorrects"
                        }
                    }
                } catch (e: Exception) {
                    Log.e("LoginScreen", "Erreur lors de la connexion", e)
                    withContext(Dispatchers.Main) {
                        errorMessage = "Erreur lors de la connexion"
                    }
                }
            }
        }) {
            Text("Connexion")
        }

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red)
        }
    }
}