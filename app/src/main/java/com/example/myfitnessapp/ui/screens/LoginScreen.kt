package com.example.myfitnessapp.ui.screens

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

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.a),
            contentDescription = "Login image",
            modifier = Modifier.fillMaxWidth().height(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Welcome Back", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text(text = "Login to your account", fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email address", color = Color.Green) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password", color = Color.Gray) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("home_screen") },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color.Green)
        ) {
            Text("Login", fontSize = 18.sp, color = Color.Black)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Forgot Password?",
            color = Color.Gray,
            modifier = Modifier.clickable { }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Or sign in with", color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(painter = painterResource(id = R.drawable.fb), contentDescription = "Facebook", modifier = Modifier.size(60.dp).clickable { })
            Image(painter = painterResource(id = R.drawable.google), contentDescription = "Google", modifier = Modifier.size(60.dp).clickable { })
            Image(painter = painterResource(id = R.drawable.twitter), contentDescription = "Twitter", modifier = Modifier.size(60.dp).clickable { })
        }
    }
}