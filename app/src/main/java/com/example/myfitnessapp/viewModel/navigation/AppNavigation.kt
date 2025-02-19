package com.example.myfitnessapp.viewModel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myfitnessapp.models.datas.User
import com.example.myfitnessapp.viewModel.ExerciseRepository
import com.example.myfitnessapp.ui.screens.ExerciseScreen
import com.example.myfitnessapp.ui.screens.HomeScreen
import com.example.myfitnessapp.ui.screens.SessionEndScreen
import com.example.myfitnessapp.ui.screens.SessionScreen
import com.example.myfitnessapp.ui.theme.Modifiers

@Composable
fun AppNavigation(modifiers: Modifiers, navController: NavHostController, user: User, repository: ExerciseRepository) {

    NavHost(navController = navController, startDestination = "home_screen") {
        composable("home_screen") { HomeScreen(modifiers, navController, user) }
        composable("exercise_screen") { ExerciseScreen(modifiers, navController, repository) }
        composable("session_screen") { SessionScreen(modifiers, navController, repository) }
        composable("session_end_screen") { SessionEndScreen(modifiers, navController, user, repository) }
    }
}