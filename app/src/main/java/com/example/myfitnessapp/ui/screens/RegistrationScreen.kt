package com.example.myfitnessapp.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.navigation.NavController
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.ui.components.DateField
import com.example.myfitnessapp.ui.components.DropdownSelector
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.ui.views.signUpWithGoogle
import com.example.myfitnessapp.viewmodels.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegistrationScreen(
    modifiers: Modifier = Modifier,
    credentialManager: CredentialManager,
    onUserRegistered: (User) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()


    var email by remember { mutableStateOf(TextFieldValue("")) }
    var pseudonym by remember { mutableStateOf(TextFieldValue("")) }
    var firstName by remember { mutableStateOf(TextFieldValue("")) }
    var lastName by remember { mutableStateOf(TextFieldValue("")) }
    var weight by remember { mutableStateOf(TextFieldValue("")) }
    var height by remember { mutableStateOf(TextFieldValue("")) }
    var birthdate by remember { mutableStateOf(TextFieldValue("")) }
    var gender by remember { mutableStateOf("Femme") }
    var level by remember { mutableStateOf("Débutant") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Créer votre profil", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email.text,
            onValueChange = { email = TextFieldValue(it) },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = pseudonym.text,
            onValueChange = { pseudonym = TextFieldValue(it) },
            label = { Text("Pseudonyme") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = firstName.text,
            onValueChange = { firstName = TextFieldValue(it) },
            label = { Text("Prénom") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = lastName.text, onValueChange = { lastName = TextFieldValue(it) },
            label = { Text("Nom") }, keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = weight.text,
            onValueChange = { weight = TextFieldValue(it) },
            label = { Text("Poids (kg)") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = height.text,
            onValueChange = { height = TextFieldValue(it) },
            label = { Text("Taille (cm)") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        DateField(
            label = "Date de naissance",
            date = birthdate.text,
            onDateSelected = { birthdate = TextFieldValue(it) }
        )

        DropdownSelector(
            label = "Genre", options = listOf("Homme", "Femme", "Autre"),
            selectedOption = gender, onOptionSelected = { gender = it }
        )

        DropdownSelector(
            label = "Niveau", options = listOf("Débutant", "Intermédiaire", "Avancé"),
            selectedOption = level, onOptionSelected = { level = it }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val user = User(
                    id = 1,
                    email = email.text,
                    pseudonym = pseudonym.text,
                    firstName = firstName.text,
                    lastName = lastName.text,
                    weight = weight.text.toDoubleOrNull() ?: 0.0,
                    height = height.text.toIntOrNull() ?: 0,
                    birthdate = birthdate.text,
                    gender = gender,
                    level = level
                )

                // Launching coroutine inside the button click
                coroutineScope.launch {  //please dont delete this one yet
                    if (email.text.endsWith("@please dont delete this one yet .com")) {
                        try {
                            signUpWithGoogle(
                                context = context,
                                credentialManager = credentialManager,
                                onSuccess = {
                                    // If Google Sign-Up is successful, register user locally
                                    onUserRegistered(user)

                                    // Switch to the main thread before showing Toast
                                    launch(Dispatchers.Main) {
                                        Toast.makeText(context, "✅ Compte enregistré avec Google", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                onFailure = { errorMessage ->
                                    launch(Dispatchers.Main) {
                                        Toast.makeText(context, "❌ Échec de l'inscription Google: $errorMessage", Toast.LENGTH_SHORT).show()
                                    }
                                    Log.e("Auth", "⚠️ Authentication failed")
                                }
                            )
                        } catch (e: Exception) {
                            launch(Dispatchers.Main) {
                                Toast.makeText(context, "❌ Erreur inattendue: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                            Log.e("Auth", "Exception: ${e.message}")
                        }
                    } else {
                        // Register locally
                        onUserRegistered(user)
                        launch(Dispatchers.Main) {
                            Toast.makeText(context, "✅ Compte enregistré localement", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
//                navController.navigate("home_screen")


            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("S'inscrire")
        }

    }
}


