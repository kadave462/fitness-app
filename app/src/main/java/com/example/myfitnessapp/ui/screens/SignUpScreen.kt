package com.example.myfitnessapp.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun SignupScreen(navController: NavController, userDao: UserDao) {
    var email by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue()) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(8.dp))

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

        Spacer(modifier = Modifier.fillMaxHeight(0.25f))

        Text(text = "Créer un compte", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = email.text, onValueChange = { email = TextFieldValue(it) }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = password.text, onValueChange = { password = TextFieldValue(it) }, label = { Text("Mot de passe") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = confirmPassword.text, onValueChange = { confirmPassword = TextFieldValue(it) }, label = { Text("Confirmer le mot de passe") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (password.text != confirmPassword.text) {
                    Toast.makeText(context, "Les mots de passe ne correspondent pas", Toast.LENGTH_LONG).show()
                    return@Button
                }

                scope.launch {
                    val existingUser = userDao.getUserByEmail(email.text)
                    if (existingUser != null) {
                        Toast.makeText(context, "Cet email est déjà utilisé", Toast.LENGTH_LONG).show()
                    } else {
                        val hashedPassword = BCrypt.hashpw(password.text, BCrypt.gensalt())
                        val encodedEmail = Uri.encode(email.text)
                        val encodedPasswordHash = Uri.encode(hashedPassword)
                        navController.navigate("registration_screen/$encodedEmail/$encodedPasswordHash")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "S'inscrire",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}