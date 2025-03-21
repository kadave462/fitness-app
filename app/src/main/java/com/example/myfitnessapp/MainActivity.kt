package com.example.myfitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.database.daos.MuscleDao
import com.example.myfitnessapp.models.database.utils.populateMusclesDatabase
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.ui.screens.AuthScreen
import com.example.myfitnessapp.ui.screens.LoginScreen
import com.example.myfitnessapp.ui.screens.RegistrationScreen
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.viewmodels.navigation.AppNavigation
import com.example.myfitnessapp.viewmodels.repositories.ExerciseRepository
import com.example.myfitnessapp.viewmodels.repositories.SessionRepository
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var muscleDao: MuscleDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(this)
        muscleDao = database.getMuscleDao()

        // Initialize database with muscle data
        lifecycleScope.launch {
            populateMusclesDatabase(this@MainActivity)
        }

        setContent {
            MyFitnessAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val scope = rememberCoroutineScope()
                    val navController = rememberNavController()
                    val credentialManager = CredentialManager.create(this@MainActivity)
                    val database = AppDatabase.getDatabase(LocalContext.current)
                    val modifiers = Modifiers()

                    // State to track authentication status
                    var user by remember { mutableStateOf<User?>(null) }

                    // Reset user state when app starts
                    LaunchedEffect(Unit) {
                        val userDao = database.getUserDao()
                        userDao.deleteUserById(1)
                        user = null
                    }

                    // Composable state to show either Auth flow or Main app
                    var showAuthFlow by remember { mutableStateOf(true) }

                    if (showAuthFlow) {
                        // Authentication NavHost
                        NavHost(
                            navController = navController,
                            startDestination = "AuthScreen"
                        ) {
                            composable("AuthScreen") {
                                AuthScreen(navController)
                            }
                            composable("LoginScreen") {
                                LoginScreen(
                                    navController = navController,
                                    onLoginComplete = { loggedInUser ->
                                        scope.launch {
                                            database.getUserDao().insertUser(loggedInUser)
                                            user = loggedInUser
                                            showAuthFlow = false  // Switch to main app
                                        }
                                    }
                                )
                            }
                            composable("registration_screen") {
                                RegistrationScreen(
                                    modifiers = Modifier,
                                    credentialManager = credentialManager,
                                    onUserRegistered = { registeredUser ->
                                        scope.launch {
                                            database.getUserDao().insertUser(registeredUser)
                                            user = registeredUser
                                            showAuthFlow = false
                                        }
                                    }
                                )
                            }
                        }
                    } else {
                        // Main app navigation
                        val sessionRepository = remember { SessionRepository(this) }
                        val exerciseRepository = remember {
                            ExerciseRepository(this, sessionRepository)
                        }
                        var currentIndex by remember { mutableIntStateOf(0) }

                        LaunchedEffect(Unit) {
                            scope.launch {
                                exerciseRepository.makeExercisesList()
                                exerciseRepository.makeCategories()
                            }
                        }

                        // Use the AppNavigation when user is authenticated
                        user?.let { authenticatedUser ->
                            AppNavigation(
                                modifiers = modifiers,
                                navController = navController,
                                user = authenticatedUser,
                                repository = exerciseRepository,
                                currentIndex = currentIndex,
                                onIndexChange = { newIndex -> currentIndex = newIndex }
                            )
                        }
                    }
                }
            }
        }
    }
}