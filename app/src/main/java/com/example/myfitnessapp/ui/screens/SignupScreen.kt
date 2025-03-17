package com.example.myfitnessapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.myfitnessapp.R
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.database.daos.UserDao
import com.example.myfitnessapp.models.entities.User
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(navController: NavController, userDao: UserDao) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var pseudonym by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf(0.0) }
    var height by remember { mutableStateOf(0) }
    var birthdate by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.a),
            contentDescription = "Sign Up Banner",
            modifier = Modifier.fillMaxWidth().height(250.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Hello there!", fontSize = 24.sp)
        Text(text = "Create an account", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email address") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = pseudonym, onValueChange = { pseudonym = it }, label = { Text("Pseudonym") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = firstName, onValueChange = { firstName = it }, label = { Text("First Name") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Last Name") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = birthdate, onValueChange = { birthdate = it }, label = { Text("Birthdate (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = gender, onValueChange = { gender = it }, label = { Text("Gender") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val newUser = User(
                    id = 2, // Auto-generated ID by Room
                    email = email,
                    password = password,
                    pseudonym = pseudonym,
                    firstName = firstName,
                    lastName = lastName,
                    weight = weight,
                    height = height,
                    birthdate = birthdate,
                    gender = gender,
                    level = "Beginner",
                    profilePictureUri = null,
                    isLoggedIn = true // Set the user as logged in
                )

                // Insert the user into the database
                (context as? androidx.lifecycle.LifecycleOwner)?.lifecycleScope?.launch {
                    userDao.logoutAllUsers() // Ensure only one user is logged in
                    userDao.insertUser(newUser)
                    Toast.makeText(context, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                    navController.navigate("home")
                }
            },
            modifier = Modifier.fillMaxWidth().height(60.dp)
        ) {
            Text("Signup")
        }
    }
}