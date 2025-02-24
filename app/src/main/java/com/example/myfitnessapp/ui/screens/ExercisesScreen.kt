package com.example.myfitnessapp.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.example.myfitnessapp.ui.views.CategoryView
import com.example.myfitnessapp.ui.components.FloatingButtonView
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.viewmodels.repositories.ExerciseFilter
import com.example.myfitnessapp.viewmodels.repositories.ExerciseRepository


@Composable
fun ExerciseScreen(
    modifiers: Modifiers,
    navController: NavController,
    repository: ExerciseRepository
) {
    val filter = ExerciseFilter(repository.allCategories)
    val searchQuery by filter.searchQuery.collectAsState()
    val filteredCategories by filter.filteredCategories.collectAsState(initial = repository.allCategories)

    val selectedExercises = repository.selectedExercises

    Box(
        modifier = modifiers.bigPaddingModifier()
    ) {
        Column() {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { filter.updateSearchQuery(it) },
                label = { Text("Rechercher un muscle",
                    style = MaterialTheme.typography.bodyMedium) },
                modifier = modifiers.containerModifier,
                singleLine = true
            )

            modifiers.getMediumSpacer()

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(filteredCategories) { category ->
                    CategoryView(modifiers, category, selectedExercises)
                }
            }

            FloatingButtonView(title = "Démarrer la séance", enabled = selectedExercises.isNotEmpty()) {
                navController.navigate("session_screen")
            }
        }

    }
}

/*
@Preview(showBackground = true)
@Composable
fun ExerciseScreenPreview() {
    val navController = rememberNavController()
    val modifiers = Modifiers()
    val repository = ExerciseRepository(Context())
    ExerciseScreen(modifiers, navController, repository)

}