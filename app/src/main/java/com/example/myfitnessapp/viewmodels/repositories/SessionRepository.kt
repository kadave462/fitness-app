package com.example.myfitnessapp.viewmodels.repositories

import android.content.Context
import android.util.Log
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

    override var selectedSession: List<Session> = emptyList()

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

    suspend fun getNumberOfReps(exercise: Exercise, user: User): Int {
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
        return emptyList()
    }

    override suspend fun getSessionById(id: Int): List<Session> {
        Log.d("SessionS", "getSessionById called with id = ${id}")
        val sessions = sessionDAO.getSessionsByGroupId(id)
        Log.d("SessionS", "Session: ${sessions.size}")
        return sessions
    }

    override fun getSessionName(sessions: List<Session>): String {
        if(sessions.isEmpty())
            return "Liste Vide"

        if (sessions[0].name == null) {
            return "Pas de nom"
        }
        return sessions[0].name!!
    }

    override suspend fun saveSession(name: String, selectedExercises: List<Exercise>) {
        var sessionId = sessionDAO.getLastSessionId() ?: 0
        sessionId++

        val sessions = selectedExercises.map { exercise ->
            sessionDAO.insert(Session(sessionId, exercise.name, name, totalSets))
            Log.d("SessionRepository", "Session saved by SessionRepository with ${exercise}")
        }
        Log.d("ExerciseRepository", "Saving sessions by SessionRepository ${sessionId}" +
                " with {selectedExercises: ${sessions}}")
    }

    override suspend fun deleteSessions(id: Int){
        sessionDAO.deleteSessionsById(id)
    }
}