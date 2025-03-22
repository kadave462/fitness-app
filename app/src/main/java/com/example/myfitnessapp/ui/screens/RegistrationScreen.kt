package com.example.myfitnessapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.ui.components.DateField
import com.example.myfitnessapp.ui.components.DropdownSelector
import com.example.myfitnessapp.ui.components.EditableTextField
import com.example.myfitnessapp.ui.components.OutlinedValidatedField
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.viewmodels.utils.GoogleRegistrationViewModelFactory
import com.example.myfitnessapp.viewmodels.utils.RegistrationViewModel
import kotlin.random.Random

@Composable
fun RegistrationScreen(
    modifiers: Modifiers,
    email: String,
    passwordHash: String,
    onUserRegistered: (User) -> Unit
) {
    // Check if this is a Google sign-up
    val isGoogleSignUp = passwordHash.startsWith("google_auth_")

    // Get the context for accessing SharedPreferences
    val context = LocalContext.current

    // Create the ViewModel using our factory if this is a Google sign-up,
    // otherwise use the default ViewModel
    val registrationViewModel: RegistrationViewModel = if (isGoogleSignUp) {
        viewModel(factory = GoogleRegistrationViewModelFactory(context))
    } else {
        viewModel()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Créer votre profil",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        // If this is a Google sign-up, show a helper message
        if (isGoogleSignUp) {
            Text(
                text = "Nous avons pré-rempli certains champs avec vos informations Google",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Email field (pre-filled and read-only)
        EditableTextField(label = "Email", value = email, readOnly = true)

        // Pseudonym field (may be pre-filled with Google display name)
        OutlinedValidatedField(
            label = "Pseudonyme",
            value = registrationViewModel.pseudonym,
            onValueChange = { registrationViewModel.pseudonym = it },
            showError = registrationViewModel.hasTriedSubmit && !registrationViewModel.isPseudonymValid(),
            errorText = "Ce champ est requis"
        )

        // First name field (may be pre-filled with Google given name)
        OutlinedValidatedField(
            label = "Prénom",
            value = registrationViewModel.firstName,
            onValueChange = { registrationViewModel.firstName = it },
            showError = registrationViewModel.hasTriedSubmit && !registrationViewModel.isFirstNameValid(),
            errorText = "Ce champ est requis"
        )

        // Last name field (may be pre-filled with Google family name)
        OutlinedValidatedField(
            label = "Nom",
            value = registrationViewModel.lastName,
            onValueChange = { registrationViewModel.lastName = it },
            showError = registrationViewModel.hasTriedSubmit && !registrationViewModel.isLastNameValid(),
            errorText = "Ce champ est requis"
        )

        // Weight field (needs to be filled by user)
        OutlinedValidatedField(
            label = "Poids (kg)",
            value = registrationViewModel.weight,
            onValueChange = { registrationViewModel.weight = it },
            showError = registrationViewModel.hasTriedSubmit && !registrationViewModel.isWeightValid(),
            errorText = "Entrez un poids valide"
        )

        // Height field (needs to be filled by user)
        OutlinedValidatedField(
            label = "Taille (cm)",
            value = registrationViewModel.height,
            onValueChange = { registrationViewModel.height = it },
            showError = registrationViewModel.hasTriedSubmit && !registrationViewModel.isHeightValid(),
            errorText = "Entrez une taille valide"
        )

        // Birthdate field (needs to be filled by user)
        DateField(
            label = "Date de naissance",
            date = registrationViewModel.birthdate,
            onDateSelected = { registrationViewModel.birthdate = it }
        )
        if (registrationViewModel.hasTriedSubmit && !registrationViewModel.isBirthdateValid()) {
            Text("La date de naissance est requise", color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        // Gender dropdown
        DropdownSelector("Genre", listOf("Homme", "Femme", "Autre"), registrationViewModel.gender) {
            registrationViewModel.gender = it
        }

        // Fitness level dropdown
        DropdownSelector("Niveau", listOf("Débutant", "Intermédiaire", "Avancé"), registrationViewModel.level) {
            registrationViewModel.level = it
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Generate a random ID for the new user
        val randomId = Random.nextInt(1, Int.MAX_VALUE)

        // Registration button
        Button(
            onClick = {
                registrationViewModel.onSubmit {
                    // Create user with all the gathered information
                    val user = User(
                        id = randomId,
                        email = email,
                        passwordHash = passwordHash,
                        pseudonym = registrationViewModel.pseudonym,
                        firstName = registrationViewModel.firstName,
                        lastName = registrationViewModel.lastName,
                        weight = registrationViewModel.weight.toDouble(),
                        height = registrationViewModel.height.toInt(),
                        birthdate = registrationViewModel.birthdate,
                        gender = registrationViewModel.gender,
                        level = registrationViewModel.level,
                        // If this is a Google sign-up, we might have a profile picture URL
                        profilePictureUri = if (isGoogleSignUp) {
                            com.example.myfitnessapp.ui.views.getGoogleUserInfo(context)?.pictureUrl
                        } else null
                    )

                    // If this is a Google sign-up, clear the stored Google info after use
                    if (isGoogleSignUp) {
                        com.example.myfitnessapp.ui.views.clearGoogleUserInfo(context)
                    }

                    // Call the callback to register the user
                    onUserRegistered(user)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "S'inscrire",
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