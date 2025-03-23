package com.example.myfitnessapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myfitnessapp.models.database.daos.UserDao
import com.example.myfitnessapp.models.entities.User
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

@Composable
fun LoginScreen(
    navController: NavController,
    userDao: UserDao,
    onUserAuthenticated: (User) -> Unit
) {
    var email by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.01f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Retour"
                )
            }
        }

        Spacer(modifier = Modifier.fillMaxHeight(0.3f))

        Text(text = "Connexion", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.fillMaxHeight(0.02f))

        OutlinedTextField(
            value = email.text,
            onValueChange = { email = TextFieldValue(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password.text,
            onValueChange = { password = TextFieldValue(it) },
            label = { Text("Mot de passe") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.03f))

        Button(
            onClick = {
                scope.launch {
                    val user = userDao.getUserByEmail(email.text)
                    if (user != null && BCrypt.checkpw(password.text, user.passwordHash)) {
                        onUserAuthenticated(user)
                        navController.navigate("home_screen") {
                            popUpTo("auth_screen") { inclusive = true }
                        }
                    } else {
                        Toast.makeText(context, "Email ou mot de passe incorrect", Toast.LENGTH_LONG).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Se connecter",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}