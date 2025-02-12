package com.example.myfitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.database.AppDatabase // Import AppDatabase
import com.example.myfitnessapp.database.MuscleDao // Import MuscleDao
import com.example.myfitnessapp.database.populateDatabase // Import populateDatabase
import com.example.myfitnessapp.models.Exercise
import com.example.myfitnessapp.models.ExerciseCategory
import com.example.myfitnessapp.navigation.AppNavigation
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import androidx.lifecycle.lifecycleScope // Import lifecycleScope
import kotlinx.coroutines.launch // Import launch

class MainActivity : ComponentActivity() {

    private lateinit var muscleDao: MuscleDao // Declare MuscleDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(this) // Initialize database
        muscleDao = database.muscleDao() // Get MuscleDao

        lifecycleScope.launch { // Launch coroutine for database population
            populateDatabase(this@MainActivity) // Call populateDatabase
        }

        setContent { // Keep your existing setContent block as is
            MyFitnessAppTheme {
                val navController = rememberNavController()
                val userName = "Alex"
                val categories = remember { mutableStateListOf<ExerciseCategory>() }
                val selectedExercises = remember { mutableStateListOf<Exercise>() }

                AppNavigation(navController, userName, categories, selectedExercises)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    MyFitnessAppTheme {
        val navController = rememberNavController()
        val userName = "Alex"
        val categories = listOf(
            ExerciseCategory("Cardio", listOf(Exercise("Jumping Jacks", "Full Body", "Cardio", ""), Exercise("Burpees", "Full Body", "Cardio", ""))),
            ExerciseCategory("Musculation", listOf(Exercise("Pompes", "Pectoraux", "Force", ""), Exercise("Squats", "Jambes", "Force", "")))
        )
        val selectedExercises = remember { mutableStateListOf<Exercise>() }

        AppNavigation(navController, userName, categories, selectedExercises)
    }
}