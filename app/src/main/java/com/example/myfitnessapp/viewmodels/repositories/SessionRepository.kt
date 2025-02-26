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

    val savedSessions = mutableListOf<List<Session>>()

    val totalSets = 3

    private val database = AppDatabase.getDatabase(context)
    val muscleDAO = database.getMuscleDao()
    val sessionDAO = database.getSessionDao()
    private var name = "SansNom"


    suspend fun getNumberOfReps(exercise: Exercise, user: User): Int {
        return calculateNumberOfReps(exercise, user)
    }

    fun getNumberOfSet(): Int{
        return totalSets
    }

    suspend fun getAllSavedSessions():List<List<Session>>{
        val sessions = sessionDAO.getAllSessions()
        val sessionsCount = sessionDAO.getLastSessionId()

        if(sessionsCount != null){
            return sessions.groupBy { it.id }.values.toList()
        }
        return emptyList()
    }

    suspend fun calculateNumberOfReps(exercise: Exercise, user: User): Int {
        var reps = muscleDAO.getNumberOfReps(exercise.target)
        val level = user.level
        if(reps != null){
            if(level == "Intermediate")
                return reps * 1.5 as Int
            if(level == "Advanced")
                return reps * 2 as Int
            return reps as Int
        }
        return 10
    }

    suspend fun setName(name: String){
        this.name = name
    }

    suspend fun saveSession(){
        var sessionId = sessionDAO.getLastSessionId()?: 0
        if(sessionId != 0){
            sessionId ++
        }

        val sessions = selectedExercises.map { exercise ->
            Session(sessionId, exercise.id, name, totalSets)
        }
        sessionDAO.insertAll(sessions)
    }

}