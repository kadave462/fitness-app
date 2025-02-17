package com.example.myfitnessapp.network

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.myfitnessapp.models.Exercise
import com.example.myfitnessapp.models.ExerciseCategory
import com.example.myfitnessapp.models.ExerciseResponse
import com.example.myfitnessapp.utils.ExerciseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File


class ExerciseRepository() {
        private val api = ExerciceClient.api
        private var allExercises: List<Exercise>? = null
        var allCategories = mutableStateListOf<ExerciseCategory>()
        val viewModel = ExerciseViewModel(allCategories)
        val selectedExercises = mutableStateListOf<Exercise>()



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

        suspend fun groupByBodyPart(exercises: List<Exercise>?): List<ExerciseCategory> {
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

        suspend fun getAllCategories(): List<ExerciseCategory> {
                makeCategories()
                return allCategories
        }
}









