package com.example.myfitnessapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.R
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.ui.components.FloatingButtonView
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.viewmodels.utils.TimeUtils

@Composable
fun HomeScreen(modifiers: Modifiers, navController: NavController, user: User) {
    Column(modifier = modifiers.bigPaddingModifier(false)) {

        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        Box(Modifier.weight(1f)
        ){
            Column(
                modifier = modifiers.containerModifier,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fitness_illu),
                    contentDescription = "Illustration de l'image",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    modifier = Modifier.size((modifiers.getScreenWidth()) - modifiers.bigPadding)
                )

                Spacer(modifier = Modifier.width(modifiers.innerPadding))
                
                Text(
                    text = "Salut ${user.pseudonym}, prêt(e) à sortir de ton canapé ?",
                    style = MaterialTheme.typography.headlineLarge.copy(textAlign = TextAlign.Center)
                )
                Text(
                    text = "C'est le moment, il est ${TimeUtils().getCurrentTime()} !",
                    style = MaterialTheme.typography.headlineSmall.copy(textAlign = TextAlign.Center)
                )
            }
        }

        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            FloatingButtonView(title = "Aller aux exercices", modifiers) {
                navController.navigate("exercise_screen")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val user = User(10, "AlexL", "", "Alex", "Laffite", "alex.laffite@gmail.com", 80.0, 180, "1995-06-15", "Homme", "Débutant")
    val modifiers = Modifiers()
    HomeScreen(modifiers, navController = rememberNavController(), user)
}
