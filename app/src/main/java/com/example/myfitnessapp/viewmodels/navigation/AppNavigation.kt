package com.example.myfitnessapp.viewmodels.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.models.interfaces.SessionRepositoryInterface
import com.example.myfitnessapp.ui.screens.AllSessionsScreen
import com.example.myfitnessapp.ui.screens.BreakScreen
import com.example.myfitnessapp.viewmodels.utils.NavigationUtils
import com.example.myfitnessapp.ui.screens.ExerciseScreen
import com.example.myfitnessapp.ui.screens.HomeScreen
import com.example.myfitnessapp.ui.screens.ProfileScreen
import com.example.myfitnessapp.ui.screens.SessionDetailScreen
import com.example.myfitnessapp.ui.screens.SessionEndScreen
import com.example.myfitnessapp.ui.screens.SessionScreen
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.viewmodels.repositories.ExerciseRepository

@Composable
fun AppNavigation(
    modifiers: Modifiers,
    navController: NavHostController,
    user: User,
    exerciseRepository: ExerciseRepository,
    sessionRepository: SessionRepositoryInterface,
    currentIndex: Int,
    onIndexChange: (Int) -> Unit
) {

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
            composable("home_screen") { HomeScreen(modifiers,navController, user) }
            composable("exercise_screen") { ExerciseScreen(modifiers, navController, exerciseRepository, sessionRepository) }
            composable("session_screen") { SessionScreen(modifiers, navController, user, exerciseRepository, currentIndex, onIndexChange) }
            composable("break_screen") { BreakScreen(modifiers,navController, exerciseRepository, currentIndex, onIndexChange) }
            composable("session_end_screen") { SessionEndScreen(modifiers, navController, user, exerciseRepository, currentIndex, onIndexChange) }
            composable("profile_screen") { ProfileScreen(modifiers, navController, user) }
            composable("all_sessions_screen") {AllSessionsScreen(modifiers, sessionRepository, navController)}
            composable("session_detail_screen/{sessionId}") { backStackEntry ->
                val sessionId = backStackEntry.arguments?.getString("sessionId")
                SessionDetailScreen(modifiers.bigPaddingModifier(true), navController, exerciseRepository, sessionRepository = sessionRepository, sessionId!!) }
        }
    }
}