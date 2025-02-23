package com.example.myfitnessapp.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myfitnessapp.R
import com.example.myfitnessapp.models.datas.User
import com.example.myfitnessapp.ui.components.FloatingButtonView
import com.example.myfitnessapp.ui.theme.Modifiers

@Composable
fun ProfileScreen(modifiers: Modifiers, navController: NavController, user: User) {
    var profileImageUri by remember { mutableStateOf(user.profilePictureUri?.let { Uri.parse(it) }) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            profileImageUri = uri
            user.profilePictureUri = uri.toString()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profil de ${user.pseudonym}",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        Box(
            modifier = Modifier
                .size(120.dp)
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

        FloatingButtonView(title = "Changer la photo de profil") {
            imagePickerLauncher.launch("image/*")
        }

        Spacer(modifier = Modifier.fillMaxHeight(0.15f))

        Text(
            text = "Informations personnelles :",
            style = MaterialTheme.typography.headlineSmall.copy(
                textDecoration = TextDecoration.Underline
            )
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        Text(
            text = "${user.firstName} ${user.lastName}",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        Text(
            text = "Email : ${user.email}",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        Text(
            text = "Poids : ${user.weight} kg",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        Text(
            text = "Taille : ${user.height} cm",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        Text(
            text = "Âge : ${user.age} ans",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        Text(
            text = "Niveau : ${user.level}",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}