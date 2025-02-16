package com.example.myfitnessapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myfitnessapp.models.ExerciseCategory
import com.example.myfitnessapp.models.ExerciseResponse
import com.example.myfitnessapp.ui.screens.ExerciseScreen
import com.example.myfitnessapp.ui.screens.HomeScreen
import com.example.myfitnessapp.ui.screens.SessionEndScreen
import com.example.myfitnessapp.ui.screens.SessionScreen
import com.example.myfitnessapp.utils.ExerciseViewModel

@Composable
fun AppNavigation(navController: NavHostController, userName: String, categories: List<ExerciseCategory>, viewModel: ExerciseViewModel, selectedExercises: MutableList<ExerciseResponse>) {

    NavHost(navController = navController, startDestination = "home_screen") {
        composable("home_screen") { HomeScreen(navController, userName) }
        composable("exercise_screen") { ExerciseScreen(navController, categories, viewModel, selectedExercises) }
        composable("session_screen") { SessionScreen(navController, selectedExercises) }
        composable("session_end_screen") { SessionEndScreen(navController, userName, selectedExercises) }
    }
}