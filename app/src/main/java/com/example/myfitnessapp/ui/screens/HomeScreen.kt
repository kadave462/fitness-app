package com.example.myfitnessapp.ui.screens

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import com.example.myfitnessapp.models.Exercise
import com.example.myfitnessapp.models.ExerciseResponse
import com.example.myfitnessapp.network.ExerciceClient
import kotlinx.coroutines.launch
import java.io.File


@Composable
fun HomeScreen(modifier: Modifier){
    val scope = rememberCoroutineScope()
    var allExercices by remember { mutableStateOf<List<ExerciseResponse>>(emptyList()) }
    //var test by remember { mutableStateOf<List<Exercise>>(emptyList()) }

    LaunchedEffect(Unit) {
        try{
            scope.launch {
                Log.d("MonTag", "Lancement du launched effect")
                allExercices = fetchAllExercisesWithRetrofit() //N'arrive pas ici
                //test = makeExercisesList(allExercices)
                Log.d("MonTag", "Appel terminé")
            }
        } catch(e: Exception){
            Log.d("MonTag", "Erreur : ${e.message}")
        }
    }

    Text("Ceci est un test")

    LazyColumn(modifier = modifier) {
        items(allExercices){
            Text(it.name)
            Text(it.id,
                fontStyle = FontStyle.Italic)
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

/*suspend fun fetchGif(id: String): File? {
    Log.d("MonTag", "Appel de fetchGif cours ..")
    var gif: File? = null
    try {
            Log.d("MonTag", "Lancement du gif launched effect")
            gif = ExerciceClient.api.getGif(id)
            Log.d("MonTag", "Appel de gif terminé")

    } catch (e: Exception) {
        showError(e)
    }
    return gif
} */


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
