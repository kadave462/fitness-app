package com.example.myfitnessapp.viewmodels.repositories

import android.content.Context
import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.room.Room
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.datas.Exercise

class SessionRepository(context: Context, val session: SnapshotStateList<Exercise>) {
    private val _selectedExercises = mutableListOf<Exercise>()
    val selectedExercises: List<Exercise> = _selectedExercises

    val totalSets = 3

    private val database = AppDatabase.getDatabase(context)
    val muscleDAO = database.muscleDao()


    suspend fun getNumberOfReps(exercise: Exercise): Int {
        Log.d("MonTag", "Appel de getNumberOfReps")
        val targetMuscleNameFromApi = exercise.target
        val reps = muscleDAO.getNumberOfReps(targetMuscleNameFromApi)
        return reps as Int
    }

}