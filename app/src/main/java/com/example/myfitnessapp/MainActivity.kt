package com.example.myfitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.models.User
import com.example.myfitnessapp.ViewModel.navigation.AppNavigation
import com.example.myfitnessapp.ViewModel.ExerciseRepository
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFitnessAppTheme {

                val scope = rememberCoroutineScope()
                val navController = rememberNavController()
                val user = User()
                val repository = remember { ExerciseRepository() }
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
                ExerciseResponse(id = "", name = "Pompes", target = "Poids du corps", bodyPart = "Pectoraux",  secondaryMuscles = listOf(), urlGif = "https://example.com/pompes.gif"),
                ExerciseResponse(id = "", name = "Squats", bodyPart = "Jambes", target = "Poids du corps", secondaryMuscles = listOf(), urlGif = "https://example.com/squats.gif"),),
            ),
            ExerciseCategory("Musculation", listOf(
                ExerciseResponse(id = "", name = "Tractions", bodyPart = "Dos", target = "Poids du corps", secondaryMuscles = listOf(), urlGif = "https://example.com/tractions.gif"),
                ExerciseResponse(id = "", name = "Développé couché", bodyPart = "Pectoraux", target = "Haltères", secondaryMuscles = listOf(), urlGif = "https://example.com/developpe.gif"),),
            )
        )

        val selectedExercises = remember { mutableStateListOf<ExerciseResponse>() }

        val viewModel = remember { ExerciseViewModel(categories) }

        AppNavigation(navController, userName, categories, viewModel, selectedExercises)
    }
} */