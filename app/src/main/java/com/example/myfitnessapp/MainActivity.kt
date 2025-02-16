package com.example.myfitnessapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.models.Exercise
import com.example.myfitnessapp.models.ExerciseCategory
import com.example.myfitnessapp.models.ExerciseResponse
import com.example.myfitnessapp.navigation.AppNavigation
import com.example.myfitnessapp.network.ExerciceClient
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.utils.ExerciseViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFitnessAppTheme {

                val scope = rememberCoroutineScope()
                var allExercices by remember { mutableStateOf<List<ExerciseResponse>>(emptyList()) }
                val navController = rememberNavController()
                val userName = "Alex"
                val categories = remember { mutableStateListOf<ExerciseCategory>() }
                val selectedExercises = remember { mutableStateListOf<ExerciseResponse>() }
                val viewModel = remember { ExerciseViewModel(categories) }

                LaunchedEffect(Unit) {
                    try{
                        scope.launch {
                            Log.d("MonTag", "Lancement du launched effect")
                            allExercices = fetchAllExercisesWithRetrofit()
                            categories.clear()
                            categories.addAll(ExerciseCategory.groupByBodyPart(allExercices))
                            //test = makeExercisesList(allExercices)
                            Log.d("MonTag", "Appel terminé")
                        }
                    } catch(e: Exception){
                        Log.d("MonTag", "Erreur : ${e.message}")
                    }
                }

                AppNavigation(navController, userName, categories, viewModel, selectedExercises)

            }
        }
    }
}

suspend fun fetchAllExercisesWithRetrofit(): List<ExerciseResponse> {
    Log.d("MonTag", "Appel en cours ..")
    try {
        val response = ExerciceClient.api.getExercises()
        return response ;
    } catch (e: Exception) {
        Log.e("MonTag", "Erreur : ${e.message}")
        Log.e("MonTag", "Erreur : ${Log.getStackTraceString(e)}")
        return emptyList<ExerciseResponse>();
    }
}

fun showError(e: Exception) {
    Log.e("MonTag", "Erreur : ${e.message}")
    Log.e("MonTag", "Erreur : ${Log.getStackTraceString(e)}")
}

suspend fun makeExercisesList(exerciseResponses: List<ExerciseResponse>): List<Exercise>{
    val exercises = mutableListOf<Exercise>()

    for (exerciseResponse in exerciseResponses){
        val id = exerciseResponse.urlGif.substringAfterLast("/")
        //val gif = fetchGif(id)
        exercises.add(Exercise(
            exerciseResponse.id,
            exerciseResponse.name,
            exerciseResponse.bodyPart,
            exerciseResponse.target,
            exerciseResponse.secondaryMuscles,
            exerciseResponse.urlGif
            //gif
        ))
    }
    return exercises;
}

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
}