package com.example.myfitnessapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AuthScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Bienvenue", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.fillMaxHeight(0.02f))

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
    }
}