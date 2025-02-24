package com.example.myfitnessapp.viewmodels.repositories

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.myfitnessapp.models.ExerciseCategory
import com.example.myfitnessapp.models.datas.Exercise
import com.example.myfitnessapp.models.datas.ExerciseResponse
import com.example.myfitnessapp.models.network.ExerciceClient
import java.io.File



class ExerciseRepository(context: Context) {
        private val api = ExerciceClient.api
        private var allExercises: List<Exercise>? = null //Metrre en mutable ?


        var allCategories = mutableStateListOf<ExerciseCategory>()
        val selectedExercises = mutableStateListOf<Exercise>()



        private fun showError(e: Exception) {
                Log.e("MonTag", "Erreur : ${e.message}")
                Log.e("MonTag", "Erreur : ${Log.getStackTraceString(e)}")
        }

        private suspend fun fetchAllExercises(): List<ExerciseResponse> {
                Log.d("MonTag", "Appel de fetchAllExercises cours ..")
                var response = emptyList<ExerciseResponse>()
                try {
                        Log.d("MonTag", "Lancement du launched effect")
                        response = api.getExercises()
                        Log.d("MonTag", "Appel terminé")
                        return response

                } catch (e: Exception) {
                        showError(e)
                        return response
                }
        }

        private suspend fun fetchGif(gifUrl: String): File? {
                val id: String = gifUrl.substringAfterLast("/")

                Log.d("MonTag", "Appel de fetchGif cours avec id :  $id")
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

        suspend fun makeExercisesList(): Unit{
                if (allExercises == null) {
                        val exerciseResponses = fetchAllExercises()
                        val exercises = mutableListOf<Exercise>()

                        for (exerciseResponse in exerciseResponses) {
                                val gif = fetchGif(exerciseResponse.id)
                                exercises.add(
                                        Exercise(
                                                exerciseResponse.id,
                                                exerciseResponse.name,
                                                exerciseResponse.bodyPart,
                                                exerciseResponse.target,
                                                exerciseResponse.secondaryMuscles,
                                                exerciseResponse.gifUrl,
                                                gif,
                                                )
                                )
                        }
                        allExercises = exercises
                }
        }

        private suspend fun groupByBodyPart(exercises: List<Exercise>?): List<ExerciseCategory> {
                if (exercises == null) {
                        makeExercisesList()
                        return groupByBodyPart(exercises)
                }
                return exercises.groupBy { it.bodyPart }
                        .map { (bodyPart, exercises) ->
                                ExerciseCategory(
                                        category = bodyPart.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                                        exercises = exercises
                                )
                        }
        }

        suspend fun makeCategories(): Unit{
                val categories = groupByBodyPart(allExercises)
                allCategories.clear()
                allCategories.addAll(categories)
        }

}









