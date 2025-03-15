package com.example.myfitnessapp.viewmodels.repositories

import android.content.Context
import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.entities.Exercise
import com.example.myfitnessapp.models.entities.Session
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.models.interfaces.SessionRepositoryInterface

class SessionRepository (context: Context) : SessionRepositoryInterface {
    private val _selectedExercises = mutableListOf<Exercise>()
    val selectedExercises: List<Exercise> = _selectedExercises

    val totalSets = 3

    private val database = AppDatabase.getDatabase(context)
    private val muscleDAO = database.getMuscleDao()
    private val sessionDAO = database.getSessionDao()


    suspend fun calculateNumberOfReps(exercise: Exercise, user: User): Int {
        var reps = muscleDAO.getNumberOfReps(exercise.target)
        val level = user.level
        if(reps != null){
            if(level == "Intermediate")
                return reps * 1.5 as Int
            if(level == "Advanced")
                return reps * 2
            return reps
        }
        return 10
    }

    suspend fun getNumberOfReps(exercise: Exercise, user: User): Int { //Devrait aller dans exercise
        return calculateNumberOfReps(exercise, user)
    }


    fun getNumberOfSet(): Int{
        return totalSets
    }

    override suspend fun getAllSavedSessions():List<List<Session>>{
        val sessions = sessionDAO.getAllSessions()
        val sessionsCount = sessionDAO.getLastSessionId()

        if(sessionsCount != null){
            return sessions.groupBy { it.id }.values.toList()
        }
        return emptyList<List<Session>>()
    }



    suspend fun saveSession(name: String) {
        var sessionId = sessionDAO.getLastSessionId() ?: 0
        if (sessionId != 0) {
            sessionId++
        }

        val sessions = selectedExercises.map { exercise ->
            Session(sessionId, exercise.id, name, totalSets)
        }
        sessionDAO.insertAll(sessions)
        Log.d("ExerciseRepository", "Session saved by SessionRepository")
    }


}