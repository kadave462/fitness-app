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
import com.example.myfitnessapp.models.datas.User
import com.example.myfitnessapp.ui.components.FloatingButtonView
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.viewModel.utils.TimeUtils

@Composable
fun HomeScreen(modifiers: Modifiers, navController: NavController, user: User) {
    Column(modifier = modifiers.bigPaddingModifier(true)) {
        Box(Modifier.weight(1f)){
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.align(Alignment.Center)

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
                        text = "Bonjour, ${user.name}",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Il est ${TimeUtils().getCurrentTime()}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            FloatingButtonView(title = "Aller aux exercices") {
                navController.navigate("exercise_screen")
            }
        }


    }


}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val user = User()
    val modifiers = Modifiers()
    HomeScreen(modifiers, navController = rememberNavController(), user)
}
