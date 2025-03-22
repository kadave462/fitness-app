package com.example.myfitnessapp.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myfitnessapp.R
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.ui.components.EditableTextField
import com.example.myfitnessapp.ui.components.FloatingButtonView
import com.example.myfitnessapp.ui.components.LevelSelector
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.ui.views.clearGoogleUserInfo
import com.example.myfitnessapp.viewmodels.repositories.UserRepository
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    modifiers: Modifiers,
    navController: NavController,
    user: User,
    onUserDeleted: () -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var profileImageUri by remember { mutableStateOf(user.profilePictureUri?.let { Uri.parse(it) }) }

    // State for delete confirmation dialog
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            profileImageUri = uri
            user.profilePictureUri = uri.toString()
            coroutineScope.launch {
                UserRepository(context, user).setProfilePictureUri(uri.toString())
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(modifiers.bigPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profil de ${user.pseudonym}",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.Gray, shape = CircleShape)
                .clickable { imagePickerLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (profileImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(profileImageUri),
                    contentDescription = "Photo de profil",
                    modifier = Modifier.size(120.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.default_profile),
                    contentDescription = "Photo de profil par défaut",
                    modifier = Modifier.size(120.dp)
                )
            }
        }

        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        FloatingButtonView(title = "Changer la photo de profil", modifiers) {
            imagePickerLauncher.launch("image/*")
        }

        Spacer(modifier = Modifier.fillMaxHeight(0.02f))

        Text(
            text = "Informations personnelles :",
            style = MaterialTheme.typography.headlineSmall.copy(
                textDecoration = TextDecoration.Underline
            )
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item { EditableTextField(label = "Email", value = user.email, readOnly = true) }
            item { EditableTextField(label = "Prénom", value = user.firstName, readOnly = true) }
            item { EditableTextField(label = "Nom", value = user.lastName, readOnly = true) }
            item { EditableTextField(label = "Âge", value = user.getAge().toString(), readOnly = true) }
            item { EditableTextField(label = "Genre", value = user.gender, readOnly = true) }

            item {
                EditableTextField(label = "Pseudonyme", value = user.pseudonym, onValueChange = { newPseudo ->
                    user.pseudonym = newPseudo
                    coroutineScope.launch {
                        UserRepository(context, user).setPseudonym(newPseudo)
                    }
                })
            }
            item {
                EditableTextField(label = "Poids (kg)", value = user.weight.toString(), onValueChange = { newWeight ->
                    newWeight.toDoubleOrNull()?.let { weight ->
                        user.weight = weight
                        coroutineScope.launch {
                            UserRepository(context, user).setWeight(weight)
                        }
                    }
                })
            }
            item {
                EditableTextField(label = "Taille (cm)", value = user.height.toString(), onValueChange = { newHeight ->
                    newHeight.toIntOrNull()?.let { height ->
                        user.height = height
                        coroutineScope.launch {
                            UserRepository(context, user).setHeight(height)
                        }
                    }
                })
            }
            item {
                LevelSelector(user = user) { newLevel ->
                    user.level = newLevel
                    coroutineScope.launch {
                        UserRepository(context, user).updateUserLevel(newLevel)
                    }
                }
            }
        }

        // Delete Account button - Made smaller and centered
        Button(
            onClick = { showDeleteConfirmation = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red.copy(alpha = 0.8f)
            ),
            modifier = Modifier
                .width(200.dp)  // Smaller width
                .height(40.dp)  // Smaller height
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                "Supprimer mon compte",
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }

    // Confirmation Dialog - Made smaller with more readable text
    if (showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = {
                Text(
                    "Supprimer votre compte ?",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Text(
                    "Cette action supprimera définitivement votre compte et toutes vos données. " +
                            "Cette action est irréversible.",
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteConfirmation = false
                        // Delete user account
                        coroutineScope.launch {
                            // Delete from the database
                            val userRepository = UserRepository(context, user)
                            userRepository.deleteUser(user.id)

                            // Clear Google credentials if present
                            try {
                                // Clear stored Google user info
                                clearGoogleUserInfo(context)

                                // Clear credentials from Credential Manager
                                val credentialManager = CredentialManager.create(context)
                                // Note: There's no direct method to clear credentials, but we can
                                // effectively invalidate them by clearing our stored info
                            } catch (e: Exception) {
                                // Log error but continue with deletion process
                            }

                            // Notify parent components about deletion
                            onUserDeleted()

                            // Navigate back to auth screen
                            navController.navigate("auth_screen") {
                                popUpTo("auth_screen") { inclusive = true }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    modifier = Modifier
                        .width(100.dp)
                        .height(36.dp)
                ) {
                    Text("Confirmer", fontSize = 12.sp)
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDeleteConfirmation = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    ),
                    modifier = Modifier
                        .width(100.dp)
                        .height(36.dp)
                ) {
                    Text("Annuler", fontSize = 12.sp)
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .width(300.dp)  // Set a fixed width for the dialog
        )
    }
}