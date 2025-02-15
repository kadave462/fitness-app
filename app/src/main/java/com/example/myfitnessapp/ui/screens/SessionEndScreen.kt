package com.example.myfitnessapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.models.Exercise
import com.example.myfitnessapp.models.ExerciseResponse
import com.example.myfitnessapp.ui.components.FloatingButtonView

@Composable
fun SessionEndScreen(navController: NavController, userName: String, selectedExercises: MutableList<ExerciseResponse>) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 128.dp)
        ) {
            Text(
                text = "Félicitations, $userName !",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Vous avez terminé la séance avec succès.",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Vous avez réalisé les exercices suivants :",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))

            selectedExercises.forEach { exercise ->
                Text(
                    text = "- ${exercise.name}",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .wrapContentWidth(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            FloatingButtonView(
                title = "Revenir à l'écran d'accueil",
            ) {
                navController.navigate("home_screen")
                selectedExercises.clear()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSessionEndScreen(){
    val navController = rememberNavController()
    val userName = "Alex"
    val sampleExercises = mutableListOf(
        ExerciseResponse(id = "", name = "Pompes", target = "Poids du corps", bodyPart = "Pectoraux",  secondaryMuscles = listOf(), gifUrl = "https://example.com/pompes.gif"),
        ExerciseResponse(id = "", name = "Squats", bodyPart = "Jambes", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://example.com/squats.gif"),
        ExerciseResponse(id = "", name = "Tractions", bodyPart = "Dos", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://example.com/tractions.gif"),
        ExerciseResponse(id = "", name = "Développé couché", bodyPart = "Pectoraux", target = "Haltères", secondaryMuscles = listOf(), gifUrl = "https://example.com/developpe.gif")
    )

    SessionEndScreen(navController, userName, sampleExercises)
}