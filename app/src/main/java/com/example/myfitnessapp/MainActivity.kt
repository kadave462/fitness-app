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

                LaunchedEffect(Unit) {
                    val userDao = database.getUserDao()
                    userDao.deleteUserById(1)
                    user = null
                }

                if (user == null) {
                    RegistrationScreen(modifiers = Modifiers(), onUserRegistered = { registeredUser ->
                        scope.launch {
                            database.getUserDao().insertUser(registeredUser)
                            user = registeredUser
                        }
                    })
                } else {
                    val repository = remember { ExerciseRepository(this) }
                    var currentIndex by remember { mutableIntStateOf(0) }

                    LaunchedEffect(Unit) {
                        scope.launch {
                            repository.makeExercisesList()
                            repository.makeCategories()
                        }
                    }

                    AppNavigation(
                        modifiers = Modifiers(),
                        navController = navController,
                        user = user!!,
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
@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    MyFitnessAppTheme {
        val navController = rememberNavController()
        val userName = "Alex"
        val categories = listOf(
            ExerciseCategory("Cardio", listOf(
                ExerciseResponse(id = "", name = "Pompes", target = "Poids du corps", bodyPart = "Pectoraux",  secondaryMuscles = listOf(), gifUrl = "https://example.com/pompes.gif"),
                ExerciseResponse(id = "", name = "Squats", bodyPart = "Jambes", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://example.com/squats.gif"),),
            ),
            ExerciseCategory("Musculation", listOf(
                ExerciseResponse(id = "", name = "Tractions", bodyPart = "Dos", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://example.com/tractions.gif"),
                ExerciseResponse(id = "", name = "Développé couché", bodyPart = "Pectoraux", target = "Haltères", secondaryMuscles = listOf(), gifUrl = "https://example.com/developpe.gif"),),
            )
        )

        val selectedExercises = remember { mutableStateListOf<ExerciseResponse>() }

        val viewModel = remember { ExerciseViewModel(categories) }

        AppNavigation(navController, userName, categories, viewModel, selectedExercises)
    }
} */