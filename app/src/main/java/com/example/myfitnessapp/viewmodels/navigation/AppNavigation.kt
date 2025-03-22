package com.example.myfitnessapp.viewmodels.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.ui.screens.AllSessionsScreen
import com.example.myfitnessapp.ui.screens.AuthScreen
import com.example.myfitnessapp.ui.screens.BreakScreen
import com.example.myfitnessapp.viewmodels.utils.NavigationUtils
import com.example.myfitnessapp.ui.screens.ExerciseScreen
import com.example.myfitnessapp.ui.screens.HomeScreen
import com.example.myfitnessapp.ui.screens.LoginScreen
import com.example.myfitnessapp.ui.screens.ProfileScreen
import com.example.myfitnessapp.ui.screens.RegistrationScreen
import com.example.myfitnessapp.ui.screens.SessionDetailScreen
import com.example.myfitnessapp.ui.screens.SessionEndScreen
import com.example.myfitnessapp.ui.screens.SessionScreen
import com.example.myfitnessapp.ui.screens.SignupScreen
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.viewmodels.repositories.ExerciseRepository
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(
    modifiers: Modifiers,
    navController: NavHostController,
    user: User?,
    repository: ExerciseRepository,
    currentIndex: Int,
    onIndexChange: (Int) -> Unit,
    onUserAuthenticated: (User) -> Unit
) {
    val database = AppDatabase.getDatabase(LocalContext.current)
    val userDao = database.getUserDao()

    val scope = rememberCoroutineScope()
    val currentRoute = NavigationUtils().currentRoute(navController)

    Scaffold(
        bottomBar = {
            if (currentRoute in listOf("home_screen", "exercise_screen", "profile_screen", "all_sessions_screen")) {
                NavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (user == null) "auth_screen" else "home_screen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("auth_screen") { AuthScreen(navController,onUserAuthenticated) }
            composable("login_screen") { LoginScreen(navController, userDao, onUserAuthenticated) }
            composable("signup_screen") { SignupScreen(navController, userDao) }

            composable(
                route = "registration_screen/{email}/{passwordHash}",
                arguments = listOf(
                    navArgument("email") { type = NavType.StringType },
                    navArgument("passwordHash") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val email = backStackEntry.arguments?.getString("email") ?: ""
                val passwordHash = backStackEntry.arguments?.getString("passwordHash") ?: ""
                RegistrationScreen(
                    modifiers = modifiers,
                    email = email,
                    passwordHash = passwordHash,
                    onUserRegistered = { registeredUser ->
                        scope.launch {
                            userDao.insertUser(registeredUser)
                            onUserAuthenticated(registeredUser)
                            navController.navigate("home_screen") {
                                popUpTo("auth_screen") { inclusive = true }
                            }
                        }
                    }
                )
            }

            composable("home_screen") { user?.let {
                HomeScreen(modifiers, navController, it)
                }
            }

            composable("exercise_screen") { ExerciseScreen(modifiers, navController, repository) }

            composable("session_screen") {
                user?.let {
                    SessionScreen(modifiers, navController, it, repository, currentIndex, onIndexChange)
                }
            }

            composable("break_screen") { BreakScreen(modifiers,navController, repository, currentIndex, onIndexChange) }

            composable("session_end_screen") {
                user?.let {
                    SessionEndScreen(modifiers, navController, it, repository, currentIndex, onIndexChange)
                }
            }

            composable("profile_screen") {
                user?.let {
                    ProfileScreen(modifiers, navController, it)
                }
            }

            composable("all_sessions_screen") {AllSessionsScreen(modifiers, repository.sessionRepository, navController)}
            composable("session_detail_screen") { SessionDetailScreen(modifiers.bigPaddingModifier(true), navController, repository.sessionRepository) }
        }
    }
}