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
import com.example.myfitnessapp.models.Exercise
import com.example.myfitnessapp.models.ExerciseCategory
import com.example.myfitnessapp.navigation.AppNavigation
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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