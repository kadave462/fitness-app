package com.example.myfitnessapp.viewmodels.repositories

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.myfitnessapp.models.ExerciseCategory
import com.example.myfitnessapp.models.entities.Exercise
import com.example.myfitnessapp.models.entities.ExerciseResponse
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.models.network.ExerciceClient
import java.io.File

class ExerciseRepository(context: Context, val sessionRepository: SessionRepository
) {
        private val api = ExerciceClient.api
        private var allExercises: List<Exercise>? = null

        var allCategories = mutableStateListOf<ExerciseCategory>()
        val filter = ExerciseFilter(allCategories)
        val selectedExercises = mutableStateListOf<Exercise>()

        var newSession = mutableStateOf(false)

        private fun showError(e: Exception) {
                Log.e("ExerciseRepository", "Erreur : ${e.message}")
                Log.e("ExerciseRepository", Log.getStackTraceString(e))
        }

        private suspend fun fetchAllExercises(): List<ExerciseResponse> {
                return try {
                        Log.d("ExerciseRepository", "Fetching all exercises...")
                        api.getExercises().also {
                                Log.d("ExerciseRepository", "Fetch successful")
                        }
                } catch (e: Exception) {
                        showError(e)
                        emptyList()
                }
        }

        private suspend fun fetchGif(gifUrl: String): File? {
                val id = gifUrl.substringAfterLast("/")
                return try {
                        Log.d("ExerciseRepository", "Fetching GIF with ID: $id")
                        api.getGif(id)
                } catch (e: Exception) {
                        showError(e)
                        null
                }
        }

        suspend fun makeExercisesList() {
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
                                                gif
                                        )
                                )
                        }
                        allExercises = exercises
                }
        }

        private suspend fun groupByBodyPart(exercises: List<Exercise>?): List<ExerciseCategory> {
                val validExercises = exercises ?: run {
                        makeExercisesList()
                        allExercises ?: emptyList()
                }

                return validExercises.groupBy { it.bodyPart }
                        .map { (bodyPart, exercises) ->
                                ExerciseCategory(
                                        category = bodyPart.replaceFirstChar { it.uppercaseChar() },
                                        exercises = exercises
                                )
                        }
        }

        suspend fun makeCategories() {
                val categories = groupByBodyPart(allExercises)
                allCategories.clear()
                allCategories.addAll(categories)
        }

        suspend fun getExerciseSetsAndReps(exercise: Exercise, user: User): Pair<Int, Int> {
                val reps = when (user.level) {
                        "Intermediate" -> (10 * 1.5).toInt()
                        "Advanced" -> (10 * 2).toInt()
                        else -> 10
                }
                val sets = 3
                return sets to reps
        }

        fun showAddSessionView(){
                newSession.value = true
        }


        suspend fun addSession(sessionName: String, exercises: List<Exercise>){
                //sessionRepository.saveSession(sessionName, exercises)
        }
}









