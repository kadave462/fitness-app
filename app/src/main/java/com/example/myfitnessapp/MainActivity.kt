package com.example.myfitnessapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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

        lifecycleScope.launch { // Launch coroutine for database population
            populateMusclesDatabase(this@MainActivity)
        }

        setContent { // Keep your existing setContent block as is
            MyFitnessAppTheme {

                val scope = rememberCoroutineScope()
                val navController = rememberNavController()
                val user = User(1, "alex.laffite@gmail.com", "AlexL", "Alex", "Laffite", 80.0, 180, "10/10/2001", "Homme", "Beginner")
                val repository = remember { ExerciseRepository(this) }
                val modifiers = Modifiers()


                LaunchedEffect(Unit) {
                    scope.launch {
                        repository.makeExercisesList()
                        repository.makeCategories()
                    }
                }

                AppNavigation(modifiers, navController, user, repository)

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