package com.example.myfitnessapp.network

import android.util.Log
import com.example.myfitnessapp.models.Exercise
import com.example.myfitnessapp.models.ExerciseResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File


class ExerciseRepository() {
        private val api = ExerciceClient.api

        fun showError(e: Exception) {
                Log.e("MonTag", "Erreur : ${e.message}")
                Log.e("MonTag", "Erreur : ${Log.getStackTraceString(e)}")
        }

        suspend fun fetchAllExercises(): List<ExerciseResponse> {
                Log.d("MonTag", "Appel de fetchAllExercises cours ..")
                var response = emptyList<ExerciseResponse>()
                try {
                        Log.d("MonTag", "Lancement du launched effect")
                        response = api.getExercises()
                        Log.d("MonTag", "Appel terminé")
                        return response;

                } catch (e: Exception) {
                        showError(e)
                        return emptyList<ExerciseResponse>();
                }

        }


        suspend fun fetchGif(gifUrl: String): File? {
                val id: String = gifUrl.substringAfterLast("/")

                Log.d("MonTag", "Appel de fetchGif cours avec id :  ${id}")
                var gif: File? = null
                try {
                        Log.d("MonTag", "Lancement du gif launched effect")
                        gif = api.getGif(id)
                        Log.d("MonTag", "Appel de gif terminé")

                } catch (e: Exception) {
                        showError(e)
                }
                return gif
        }

        suspend fun makeExercisesList(exerciseResponses: List<ExerciseResponse>): List<Exercise>{
                //val exerciseResponses = fetchAllExercises()
                val exercises = mutableListOf<Exercise>()

                for (exerciseResponse in exerciseResponses){
                        val gif = fetchGif(exerciseResponse.id)
                        exercises.add(Exercise(
                                exerciseResponse.id,
                                exerciseResponse.name,
                                exerciseResponse.bodyPart,
                                exerciseResponse.target,
                                exerciseResponse.secondaryMuscles,
                                exerciseResponse.gifUrl,
                                gif,

                        ))
                }
                return exercises;
        }
}









