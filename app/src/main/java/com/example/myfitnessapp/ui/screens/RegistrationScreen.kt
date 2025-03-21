package com.example.myfitnessapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.ui.components.DateField
import com.example.myfitnessapp.ui.components.DropdownSelector
import com.example.myfitnessapp.ui.components.EditableTextField
import com.example.myfitnessapp.ui.components.LevelSelector
import com.example.myfitnessapp.ui.components.OutlinedValidatedField
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.viewmodels.repositories.UserRepository
import com.example.myfitnessapp.viewmodels.utils.RegistrationViewModel
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(
    modifiers: Modifiers,
    email: String,
    passwordHash: String,
    onUserRegistered: (User) -> Unit,
    registrationViewModel: RegistrationViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(text = "Créer votre profil",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        EditableTextField(label = "Email", value = email, readOnly = true)

        OutlinedValidatedField(
            label = "Pseudonyme",
            value = registrationViewModel.pseudonym,
            onValueChange = { registrationViewModel.pseudonym = it },
            showError = registrationViewModel.hasTriedSubmit && !registrationViewModel.isPseudonymValid()
        )

        OutlinedValidatedField(
            label = "Prénom",
            value = registrationViewModel.firstName,
            onValueChange = { registrationViewModel.firstName = it },
            showError = registrationViewModel.hasTriedSubmit && !registrationViewModel.isFirstNameValid()
        )

        OutlinedValidatedField(
            label = "Nom",
            value = registrationViewModel.lastName,
            onValueChange = { registrationViewModel.lastName = it },
            showError = registrationViewModel.hasTriedSubmit && !registrationViewModel.isLastNameValid()
        )

        OutlinedValidatedField(
            label = "Poids (kg)",
            value = registrationViewModel.weight,
            onValueChange = { registrationViewModel.weight = it },
            showError = registrationViewModel.hasTriedSubmit && !registrationViewModel.isWeightValid(),
            errorText = "Entrez un poids valide"
        )

        OutlinedValidatedField(
            label = "Taille (cm)",
            value = registrationViewModel.height,
            onValueChange = { registrationViewModel.height = it },
            showError = registrationViewModel.hasTriedSubmit && !registrationViewModel.isHeightValid(),
            errorText = "Entrez une taille valide"
        )

        DateField(
            label = "Date de naissance",
            date = registrationViewModel.birthdate,
            onDateSelected = { registrationViewModel.birthdate = it }
        )
        if (registrationViewModel.hasTriedSubmit && !registrationViewModel.isBirthdateValid()) {
            Text("La date de naissance est requise", color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        DropdownSelector("Genre", listOf("Homme", "Femme", "Autre"), registrationViewModel.gender) {
            registrationViewModel.gender = it
        }

        DropdownSelector("Niveau", listOf("Débutant", "Intermédiaire", "Avancé"), registrationViewModel.level) {
            registrationViewModel.level = it
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                registrationViewModel.onSubmit {
                    val user = User(
                        id = 0,
                        email = email,
                        passwordHash = passwordHash,
                        pseudonym = registrationViewModel.pseudonym,
                        firstName = registrationViewModel.firstName,
                        lastName = registrationViewModel.lastName,
                        weight = registrationViewModel.weight.toDouble(),
                        height = registrationViewModel.height.toInt(),
                        birthdate = registrationViewModel.birthdate,
                        gender = registrationViewModel.gender,
                        level = registrationViewModel.level
                    )
                    onUserRegistered(user)
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

@Preview
@Composable
fun PreviewUserRegistrationScreen() {
    MyFitnessAppTheme {
        RegistrationScreen(modifiers = Modifiers(), email = "john.c.breckinridge@altostrat.com", passwordHash = "password", onUserRegistered = {})
    }
}