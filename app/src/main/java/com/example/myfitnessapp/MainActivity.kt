package com.example.myfitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.database.daos.UserDao
import com.example.myfitnessapp.models.database.utils.populateUserDatabase
import com.example.myfitnessapp.models.database.utils.populateMusclesDatabase
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.viewmodels.navigation.AppNavigation
import com.example.myfitnessapp.viewmodels.repositories.ExerciseRepository
import kotlinx.coroutines.launch
import android.util.Log

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(this)
        val userDao = database.getUserDao()
        val muscleDao = database.getMuscleDao()

        setContent {
            MyFitnessAppTheme {
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()
                val repository = remember { ExerciseRepository(this) }
                val modifiers = remember { Modifiers() }
                var currentIndex by remember { mutableIntStateOf(0) }

                // User state managed within Compose
                var user by remember { mutableStateOf<User?>(null) }

                // Populate databases and fetch user asynchronously
                LaunchedEffect(Unit) {
                    scope.launch {
                        populateUserDatabase(this@MainActivity)
                        populateMusclesDatabase(this@MainActivity)

                        val loggedInUser = userDao.getLoggedInUser()
                        user = loggedInUser ?: userDao.getUserById(1)
                    }
                }

                // Initialize repository operations once
                LaunchedEffect(Unit) {
                    scope.launch {
                        repository.makeExercisesList()
                        repository.makeCategories()
                    }
                }

                if (user == null) {
                    Log.e("MainActivity", "User is null, waiting for database fetch...")
                } else {
                    AppNavigation(
                        modifiers = modifiers,
                        navController = navController,
                        userDao = userDao,
                        user = user!!, // Safe after checking null
                        repository = repository,
                        currentIndex = currentIndex,
                        onIndexChange = { newIndex -> currentIndex = newIndex }
                    )
                }

            }
        }
    }
}

/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(this)
        muscleDao = database.getMuscleDao()
        userDao = database.getUserDao()


        lifecycleScope.launch {
            populateMusclesDatabase(this@MainActivity)
            val currentUser = userDao.getUserById(1) // Get the user with ID 1
            if (currentUser != null) {
               var user = currentUser
            }
        }

        setContent {
            MyFitnessAppTheme {

                val scope = rememberCoroutineScope()
                val navController = rememberNavController()

                val user = User(1, "alex.laffite@gmail.com", "AlexL", "Alex", "Laffite", "Yvan", 180, "1995-06-15", "Homme", "Beginner")
                val repository = remember { ExerciseRepository(this) }
                val modifiers = Modifiers()

                var currentIndex by remember { mutableIntStateOf(0) }
                // Use remember to store the current user
                val userState = remember { mutableStateOf(user) }



                LaunchedEffect(Unit) {
                    scope.launch {
                        repository.makeExercisesList()
                        repository.makeCategories()
                    }
                }

                AppNavigation(
                    modifiers, navController, user, repository, currentIndex,
                    onIndexChange = { newIndex -> currentIndex = newIndex }
                )

            }
        }
    }
}

*/