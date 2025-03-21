package com.example.myfitnessapp.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myfitnessapp.R

@Composable
fun AuthScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.a),
            contentDescription = "banner",
            modifier = Modifier.fillMaxWidth().height(300.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Welcome to Our Fitness App")
        Text(text = "Best Fitness Application That you can use")
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { navController.navigate("LoginScreen") },
            modifier = Modifier.fillMaxWidth().height(60.dp)
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = { navController.navigate("registration_screen") },
            modifier = Modifier.fillMaxWidth().height(60.dp)
        ) {
            Text("Signup")
        }
    }
}