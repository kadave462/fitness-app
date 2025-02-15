package com.example.myfitnessapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.models.Exercise
import com.example.myfitnessapp.models.ExerciseCategory
import com.example.myfitnessapp.models.ExerciseResponse
import com.example.myfitnessapp.ui.views.CategoryView
import com.example.myfitnessapp.ui.components.FloatingButtonView

@Composable
fun ExerciseScreen(navController: NavController, categories: List<ExerciseCategory>, selectedExercises: MutableList<ExerciseResponse>) {

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Column(modifier = Modifier.fillMaxSize()
            .padding(bottom = 16.dp),) {

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(categories) { category ->
                    CategoryView(category, selectedExercises)
                }
            }

            FloatingButtonView(title = "Démarrer la séance", enabled = selectedExercises.isNotEmpty()) {
                navController.navigate("session_screen")
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun ExerciseScreenPreview() {
    val navController = rememberNavController()
    val categories = listOf(
        ExerciseCategory("Cardio", listOf(
            ExerciseResponse(id = "", name = "Pompes", target = "Poids du corps", bodyPart = "Pectoraux",  secondaryMuscles = listOf(), gifUrl = "https://example.com/pompes.gif"),
            ExerciseResponse(id = "", name = "Squats", bodyPart = "Jambes", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://example.com/squats.gif"),),
        ),
        ExerciseCategory("Musculation", listOf(
            ExerciseResponse(id = "", name = "Tractions", bodyPart = "Dos", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://example.com/tractions.gif"),
            ExerciseResponse(id = "", name = "Développé couché", bodyPart = "Pectoraux", target = "Haltères", secondaryMuscles = listOf(), gifUrl = "https://example.com/developpe.gif"),),
        )
    )

    val selectedExercises = remember { mutableStateListOf<ExerciseResponse>() }

    ExerciseScreen(navController, categories, selectedExercises)
}