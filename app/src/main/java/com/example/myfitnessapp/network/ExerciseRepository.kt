package com.example.myfitnessapp.network

import android.util.Log
import com.example.myfitnessapp.models.Exercise
import com.example.myfitnessapp.models.ExerciseResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class ExerciseRepository(scope: CoroutineScope) {
        private val api = ExerciceClient.api
        val scope = scope

        fun showError(e: Exception) {
                Log.e("MonTag", "Erreur : ${e.message}")
                Log.e("MonTag", "Erreur : ${Log.getStackTraceString(e)}")
        }

        suspend fun fetchAllExercises(): List<ExerciseResponse> {
                Log.d("MonTag", "Appel de fetchAllExercises cours ..")
                var response = emptyList<ExerciseResponse>()
                try {
                        scope.launch {
                                Log.d("MonTag", "Lancement du launched effect")
                                response = api.getExercisesWithoutEquipment()
                                Log.d("MonTag", "Appel terminé")
                        }
                } catch (e: Exception) {
                        showError(e)
                }
                return response;
        }


        /*suspend fun fetchGif(id: String): File? {
                Log.d("MonTag", "Appel de fetchGif cours ..")
                var gif: File? = null

                try {
                        scope.launch {
                                Log.d("MonTag", "Lancement du gif launched effect")
                                gif = api.getGif(id)
                                Log.d("MonTag", "Appel de gif terminé")
                        }

                } catch (e: Exception) {
                        showError(e)
                }
                return gif
        } */

        /*suspend fun makeExercisesList(): List<Exercise>{
                val exerciseResponses = fetchAllExercises()
                val exercises = mutableListOf<Exercise>()

                for (exerciseResponse in exerciseResponses){
                        val gif = fetchGif(exerciseResponse.id)
                        exercises.add(Exercise(
                                exerciseResponse.id,
                                exerciseResponse.name,
                                exerciseResponse.bodyPart,
                                exerciseResponse.target,
                                exerciseResponse.secondaryMuscles,
                                exerciseResponse.urlGif,
                                gif,
                                //gif
                        ))
                }
                return exercises;
        }*/
}









