package com.example.myfitnessapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.ui.components.DateField
import com.example.myfitnessapp.ui.components.DropdownSelector
import com.example.myfitnessapp.ui.components.LevelSelector
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.viewmodels.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegistrationScreen(
    navController: NavController,
    email: String
) {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val userDao = remember { database.getUserDao() }

    var pseudonym by remember { mutableStateOf(TextFieldValue()) }
    var firstName by remember { mutableStateOf(TextFieldValue()) }
    var lastName by remember { mutableStateOf(TextFieldValue()) }
    var weight by remember { mutableStateOf(TextFieldValue()) }
    var height by remember { mutableStateOf(TextFieldValue()) }
    var birthdate by remember { mutableStateOf(TextFieldValue()) }
    var gender by remember { mutableStateOf("Femme") }
    var level by remember { mutableStateOf("Débutant") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Complétez votre profil", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {},
            label = { Text("Email") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(value = pseudonym.text, onValueChange = { pseudonym = TextFieldValue(it) }, label = { Text("Pseudonyme") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = firstName.text, onValueChange = { firstName = TextFieldValue(it) }, label = { Text("Prénom") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = lastName.text, onValueChange = { lastName = TextFieldValue(it) }, label = { Text("Nom") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = weight.text, onValueChange = { weight = TextFieldValue(it) }, label = { Text("Poids (kg)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = height.text, onValueChange = { height = TextFieldValue(it) }, label = { Text("Taille (cm)") }, modifier = Modifier.fillMaxWidth())

        DateField(label = "Date de naissance", date = birthdate.text, onDateSelected = { birthdate = TextFieldValue(it) })
        DropdownSelector(label = "Genre", options = listOf("Homme", "Femme", "Autre"), selectedOption = gender, onOptionSelected = { gender = it })
        DropdownSelector(label = "Niveau", options = listOf("Débutant", "Intermédiaire", "Avancé"), selectedOption = level, onOptionSelected = { level = it })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                val user = userDao.getUserByEmail(email)
                if (user != null) {
                    userDao.updateUser(
                        user.copy(
                            pseudonym = pseudonym.text,
                            firstName = firstName.text,
                            lastName = lastName.text,
                            weight = weight.text.toDoubleOrNull() ?: 0.0,
                            height = height.text.toIntOrNull() ?: 0,
                            birthdate = birthdate.text,
                            gender = gender,
                            level = level
                        )
                    )
                    withContext(Dispatchers.Main) {
                        navController.navigate("home_screen") {
                            popUpTo("RegistrationScreen") { inclusive = true }
                        }
                    }
                }
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Terminer l'inscription")
        }
    }
}
