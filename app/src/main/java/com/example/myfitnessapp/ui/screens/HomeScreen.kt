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
import com.example.myfitnessapp.models.ExerciceResponse
import com.example.myfitnessapp.network.ExerciceClient
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(modifier: Modifier){
    val scope = rememberCoroutineScope()
    var allExercices by remember { mutableStateOf<List<ExerciceResponse>>(emptyList()) }
    LaunchedEffect(Unit) {
        try{
            scope.launch {
                Log.d("MonTag", "Lancement du launched effect")
                allExercices = fetchAllExercisesWithRetrofit() //N'arrive pas ici
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
        }
    }

}

suspend fun fetchAllExercisesWithRetrofit(): List<ExerciceResponse> {
    Log.d("MonTag", "Appel en cours ..")
    try {
        val response = ExerciceClient.api.getExercises()
        return response ;
    } catch (e: Exception) {
        Log.e("MonTag", "Erreur : ${e.message}")
        Log.e("MonTag", "Erreur : ${Log.getStackTraceString(e)}")
        return emptyList<ExerciceResponse>();
    }

}
