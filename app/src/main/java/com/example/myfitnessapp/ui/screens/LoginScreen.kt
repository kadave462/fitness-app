package com.example.myfitnessapp.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.myfitnessapp.R
import com.example.myfitnessapp.models.database.daos.UserDao
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController, userDao: UserDao) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.a),
            contentDescription = "Login image",
            modifier = Modifier.size(200.dp)
        )

        Text(text = "Welcome Back", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Login to your account")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text(text = "Email address") })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text(text = "Password") }, visualTransformation = PasswordVisualTransformation())
        Spacer(modifier = Modifier.height(16.dp))

        if (loginError.isNotEmpty()) {
            Text(text = loginError, color = androidx.compose.ui.graphics.Color.Red)
            Spacer(modifier = Modifier.height(10.dp))
        }

        Button(onClick = {
            (context as? androidx.lifecycle.LifecycleOwner)?.lifecycleScope?.launch {
                val user = userDao.getUserByEmail(email) // Fetch user by email
                if (user != null && user.password == password) { // Correct password validation
                    userDao.logoutAllUsers() // Logout other users
                    userDao.loginUser(user.id) // Set this user as logged in
                    navController.navigate("home_screen") // Navigate to home
                    Log.i("Login", "User logged in: $email")
                } else {
                    loginError = "Invalid email or password. Try again."
                }
            }
        }) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "Forgot Password?", modifier = Modifier.clickable { })
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "Or sign in with")

        Row(
            modifier = Modifier.fillMaxWidth().padding(40.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(painter = painterResource(id = R.drawable.fb), contentDescription = "Facebook", modifier = Modifier.size(60.dp).clickable { })
            Image(painter = painterResource(id = R.drawable.google), contentDescription = "Google", modifier = Modifier.size(60.dp).clickable { })
            Image(painter = painterResource(id = R.drawable.twitter), contentDescription = "Twitter", modifier = Modifier.size(60.dp).clickable { })
        }
    }
}
