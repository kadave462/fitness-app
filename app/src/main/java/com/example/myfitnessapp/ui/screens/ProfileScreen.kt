package com.example.myfitnessapp.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myfitnessapp.R
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.ui.components.EditableTextField
import com.example.myfitnessapp.ui.components.FloatingButtonView
import com.example.myfitnessapp.ui.components.LevelSelector
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.viewmodels.repositories.UserRepository
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(modifiers: Modifiers, navController: NavController, user: User) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var profileImageUri by remember { mutableStateOf(user.profilePictureUri?.let { Uri.parse(it) }) }

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
            modifier = Modifier.fillMaxWidth()
        ) {
            item { EditableTextField(label = "Email", value = user.email, readOnly = true) }
            item { EditableTextField(label = "Prénom", value = user.firstName, readOnly = true) }
            item { EditableTextField(label = "Nom", value = user.lastName, readOnly = true) }
            item { EditableTextField(label = "Âge", value = user.getAge().toString(), readOnly = true) }

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
    }
}