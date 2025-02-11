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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.models.Exercise
import com.example.myfitnessapp.models.ExerciseCategory
import com.example.myfitnessapp.ui.views.CategoryView
import com.example.myfitnessapp.ui.components.FloatingButtonView

@Composable
fun ExerciseScreen(navController: NavController, categories: List<ExerciseCategory>, selectedExercises: MutableList<Exercise>) {

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(categories) { category ->
                    CategoryView(category, selectedExercises)
                }
            }
        }

        FloatingButtonView(
            title = "Démarrer la séance",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            navController.navigate("session_screen")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ExerciseScreenPreview() {
    val navController = rememberNavController()
    val categories = listOf(
        ExerciseCategory("Cardio", listOf(Exercise("Jumping Jacks", "Full Body", "Cardio", ""), Exercise("Burpees", "Full Body", "Cardio", ""))),
        ExerciseCategory("Musculation", listOf(Exercise("Pompes", "Pectoraux", "Force", ""), Exercise("Squats", "Jambes", "Force", "")))
    )
    val selectedExercises = remember { mutableStateListOf<Exercise>() }

    ExerciseScreen(navController, categories, selectedExercises)
}