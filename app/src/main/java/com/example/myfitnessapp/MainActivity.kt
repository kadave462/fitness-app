package com.example.myfitnessapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.viewmodels.navigation.AppNavigation
import com.example.myfitnessapp.viewmodels.repositories.ExerciseRepository
import kotlinx.coroutines.launch
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.database.daos.MuscleDao
import com.example.myfitnessapp.models.database.utils.populateMusclesDatabase
import androidx.lifecycle.lifecycleScope
import com.example.myfitnessapp.ui.screens.RegistrationScreen
import com.example.myfitnessapp.viewmodels.repositories.SessionRepository
import java.time.LocalDate
import java.util.Calendar
import java.util.Date


class MainActivity : ComponentActivity() {

    private lateinit var muscleDao: MuscleDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(this)
        muscleDao = database.getMuscleDao()

        lifecycleScope.launch {
            populateMusclesDatabase(this@MainActivity)
        }

        setContent {
            MyFitnessAppTheme {
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()
                val database = AppDatabase.getDatabase(LocalContext.current)
                var user by remember { mutableStateOf<User?>(null) }
                val modifiers = Modifiers()

                //LaunchedEffect(Unit) {
                //    val userDao = database.getUserDao()
                //    userDao.deleteUserById(1)
                //    user = null
                //}

                val repository = remember { ExerciseRepository(this, SessionRepository(this)) }
                var currentIndex by remember { mutableIntStateOf(0) }

                LaunchedEffect(Unit) {
                    scope.launch {
                        repository.makeExercisesList()
                        repository.makeCategories()
                    }
                }

                AppNavigation(
                    modifiers = modifiers,
                    navController = navController,
                    user = user,
                    repository = repository,
                    currentIndex = currentIndex,
                    onIndexChange = { newIndex -> currentIndex = newIndex },
                    onUserAuthenticated = { authenticatedUser ->
                        user = authenticatedUser
                    }
                )
            }
        }
    }
}

