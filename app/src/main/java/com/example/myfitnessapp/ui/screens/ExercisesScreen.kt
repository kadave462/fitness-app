package com.example.myfitnessapp.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.example.myfitnessapp.ui.views.CategoryView
import com.example.myfitnessapp.ui.components.FloatingButtonView
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.ui.views.NewSessionView
import com.example.myfitnessapp.viewmodels.repositories.ExerciseFilter
import com.example.myfitnessapp.viewmodels.repositories.ExerciseRepository
import com.example.myfitnessapp.viewmodels.repositories.SessionRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Composable
fun ExerciseScreen(
    modifiers: Modifiers,
    navController: NavController,
    repository: ExerciseRepository
) {
    val filter = remember { ExerciseFilter(repository.allCategories) }
    val searchQuery by filter.searchQuery.collectAsState()
    val filteredCategories by filter.filteredCategories.collectAsState(initial = repository.allCategories)

    val selectedExercises = repository.selectedExercises
    val rememberCoroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifiers.bigPaddingModifier()
    ) {
        Column() {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { filter.updateSearchQuery(it) },
                label = { Text("Rechercher un muscle",
                    style = MaterialTheme.typography.bodyMedium,) },
                modifier = modifiers.containerModifier,
                singleLine = true
            )

            modifiers.getMediumSpacer()

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(filteredCategories) { category ->
                    CategoryView(modifiers, category, selectedExercises)
                }
            }

            if(!repository.newSession.value){
                Row(modifiers.onContainerModifier, horizontalArrangement = Arrangement.SpaceBetween) {
                    Box(modifier = modifiers.onContainerModifier.fillMaxWidth(0.5f)){
                        FloatingButtonView(title = "Enregistrer", modifiers, enabled = selectedExercises.isNotEmpty()) {
                            repository.showAddSessionView()
                    }}
                    Box(modifier = modifiers.onContainerModifier){
                        FloatingButtonView(title = "Démarrer", modifiers,  enabled = selectedExercises.isNotEmpty()) {
                            navController.navigate("session_screen")
                        }}
                }

            } else {
                NewSessionView(onDismiss = { repository.newSession.value = false }, onAdd = {
                    rememberCoroutineScope.launch {
                        repository.addSession(it, selectedExercises)
                        //navController.navigate("all_sessions_screen")
                    }
                })
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun ExerciseScreenPreview() {
    val navController = rememberNavController()
    val modifiers = Modifiers()
    val repository = ExerciseRepository(LocalContext.current, SessionRepository(LocalContext.current))
    ExerciseScreen(modifiers, navController, repository)

}