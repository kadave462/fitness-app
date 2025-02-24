package com.example.myfitnessapp.viewmodels.repositories

import android.content.Context
import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.room.Room
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.datas.Exercise
import com.example.myfitnessapp.models.datas.User

class SessionRepository(context: Context, val session: SnapshotStateList<Exercise>) {
    private val _selectedExercises = mutableListOf<Exercise>()
    val selectedExercises: List<Exercise> = _selectedExercises

    val totalSets = 3

    private val database = AppDatabase.getDatabase(context)
    val muscleDAO = database.muscleDao()


    suspend fun getNumberOfReps(exercise: Exercise, user: User): Int {
        return calculateNumberOfReps(exercise, user)
    }

    fun getNumberOfSet(): Int{
        return totalSets
    }

    suspend fun calculateNumberOfReps(exercise: Exercise, user: User): Int {
        var reps = muscleDAO.getNumberOfReps(exercise.target)
        val level = user.level
        if(reps != null){
            if(level == "Intermédiaire")
                return reps * 1.5 as Int
            if(level == "Avancé")
                return reps * 2 as Int
            return reps as Int
        }
        return 10
    }

}