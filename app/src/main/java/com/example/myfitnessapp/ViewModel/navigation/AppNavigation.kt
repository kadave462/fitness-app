package com.example.myfitnessapp.ViewModel.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myfitnessapp.models.User
import com.example.myfitnessapp.ViewModel.ExerciseRepository
import com.example.myfitnessapp.ViewModel.utils.NavigationUtils
import com.example.myfitnessapp.ui.screens.ExerciseScreen
import com.example.myfitnessapp.ui.screens.HomeScreen
import com.example.myfitnessapp.ui.screens.ProfileScreen
import com.example.myfitnessapp.ui.screens.SessionEndScreen
import com.example.myfitnessapp.ui.screens.SessionScreen

@Composable
fun AppNavigation(navController: NavHostController, user: User, repository: ExerciseRepository) {

    val currentRoute = NavigationUtils().currentRoute(navController)

    Scaffold(
        bottomBar = {
            if (currentRoute in listOf("home_screen", "exercise_screen", "profile_screen")) {
                NavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home_screen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home_screen") { HomeScreen(navController, user) }
            composable("exercise_screen") { ExerciseScreen(navController, repository) }
            composable("session_screen") { SessionScreen(navController, repository) }
            composable("session_end_screen") { SessionEndScreen(navController, user, repository) }
            composable("profile_screen") { ProfileScreen(navController, user) }
        }
    }
}