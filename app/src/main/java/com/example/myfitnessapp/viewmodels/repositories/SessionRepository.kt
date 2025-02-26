package com.example.myfitnessapp.viewmodels.repositories

import android.content.Context
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.entities.Exercise
import com.example.myfitnessapp.models.entities.Session
import com.example.myfitnessapp.models.entities.User

class SessionRepository(user: User, context: Context, val session: SnapshotStateList<Exercise>) {
    private val _selectedExercises = mutableListOf<Exercise>()
    val selectedExercises: List<Exercise> = _selectedExercises

    private val totalSets = 3
    private val database = AppDatabase.getDatabase(context)
    private val muscleDAO = database.getMuscleDao()
    private val sessionDAO = database.getSessionDao()
    private var name = "SansNom"

    fun getNumberOfSet(): Int = totalSets

    suspend fun getNumberOfReps(exercise: Exercise, user: User): Int {
        val reps = muscleDAO.getNumberOfReps(exercise.target) ?: 10
        return when (user.level) {
            "Intermediate" -> (reps * 1.5).toInt()
            "Advanced" -> (reps * 2).toInt()
            else -> reps
        }
    }

    suspend fun setName(name: String) {
        this.name = name
    }

    suspend fun saveSession() {
        var sessionId = sessionDAO.getLastSessionId() ?: 0
        if (sessionId != 0) {
            sessionId++
        }

        val sessions = selectedExercises.map { exercise ->
            Session(sessionId, exercise.id, name, totalSets)
        }
        sessionDAO.insertAll(sessions)
    }
}