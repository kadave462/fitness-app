package com.example.myfitnessapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource
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

        Spacer(modifier = Modifier.height(modifiers.getScreenHeight() / 5f))

        Box(Modifier.weight(1f)
        ){
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top

            ) {
                Image(
                    painter = painterResource(id = R.drawable.fitness_illustration),
                    contentDescription = "Fitness Image",
                    modifier = Modifier.size((modifiers.getScreenWidth()/2) - modifiers.bigPadding)
                )

                Spacer(modifier = Modifier.width(modifiers.innerPadding))

                Column(modifier = modifiers.containerModifier,
                    verticalArrangement = Arrangement.Top) {

                    Text(
                        text = "Bonjour, ${user.pseudonym}",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Il est ${TimeUtils().getCurrentTime()}",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
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
