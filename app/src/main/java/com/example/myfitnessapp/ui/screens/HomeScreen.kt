package com.example.myfitnessapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.R
import com.example.myfitnessapp.models.User
import com.example.myfitnessapp.network.ExerciseRepository
import com.example.myfitnessapp.ui.components.FloatingButtonView
import com.example.myfitnessapp.utils.TimeUtils

@Composable
fun HomeScreen(navController: NavController, user: User) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.fitness_illustration),
                contentDescription = "Fitness Image",
                modifier = Modifier.size(140.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "Bonjour, $user.name",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Il est ${TimeUtils().getCurrentTime()}",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        FloatingButtonView(title = "Aller aux exercices") {
            navController.navigate("exercise_screen")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val user = User()
    HomeScreen(navController = rememberNavController(), user)
}
