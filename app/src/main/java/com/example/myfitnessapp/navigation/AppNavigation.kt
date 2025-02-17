package com.example.myfitnessapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myfitnessapp.models.Exercise
import com.example.myfitnessapp.models.ExerciseCategory
import com.example.myfitnessapp.models.User
import com.example.myfitnessapp.network.ExerciseRepository
import com.example.myfitnessapp.ui.screens.ExerciseScreen
import com.example.myfitnessapp.ui.screens.HomeScreen
import com.example.myfitnessapp.ui.screens.SessionEndScreen
import com.example.myfitnessapp.ui.screens.SessionScreen
import com.example.myfitnessapp.utils.ExerciseViewModel
import java.lang.reflect.Modifier

@Composable
fun AppNavigation(navController: NavHostController, user: User, repository: ExerciseRepository) {

    NavHost(navController = navController, startDestination = "home_screen") {
        composable("home_screen") { HomeScreen(navController, user) }
        composable("exercise_screen") { ExerciseScreen(navController, repository) }
        composable("session_screen") { SessionScreen(navController, repository) }
        composable("session_end_screen") { SessionEndScreen(navController, user, repository) }
    }
}