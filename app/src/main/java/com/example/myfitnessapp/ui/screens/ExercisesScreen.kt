package com.example.myfitnessapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.models.ExerciseCategory
import com.example.myfitnessapp.models.Exercise
import com.example.myfitnessapp.network.ExerciseRepository
import com.example.myfitnessapp.ui.views.CategoryView
import com.example.myfitnessapp.ui.components.FloatingButtonView
import com.example.myfitnessapp.utils.ExerciseViewModel

@Composable
fun ExerciseScreen(
    navController: NavController,

    repository: ExerciseRepository
) {
    val viewModel = repository.viewModel
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredCategories by viewModel.filteredCategories.collectAsState(initial = repository.allCategories)
    val selectedExercises = repository.selectedExercises

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
            .padding(bottom = 16.dp),
            ) {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                label = { Text("Rechercher un muscle") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )


            LazyColumn(modifier = Modifier.weight(1f)) {
                items(filteredCategories) { category ->
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
            Exercise(id = "", name = "Pompes", target = "Poids du corps", bodyPart = "Pectoraux",  secondaryMuscles = listOf(), gifUrl = "https://example.com/pompes.gif", gif = null),
            Exercise(id = "", name = "Squats", bodyPart = "Jambes", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://example.com/squats.gif", gif = null),),
        ),
        ExerciseCategory("Musculation", listOf(
            Exercise(id = "", name = "Tractions", bodyPart = "Dos", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://example.com/tractions.gif", gif = null),
            Exercise(id = "", name = "Développé couché", bodyPart = "Pectoraux", target = "Haltères", secondaryMuscles = listOf(), gifUrl = "https://example.com/developpe.gif", gif = null),),
        )
    )

    ExerciseScreen(navController, ExerciseRepository())
}