package com.example.myfitnessapp.models.interfaces

import com.example.myfitnessapp.models.entities.Exercise
import com.example.myfitnessapp.models.entities.Session
import kotlinx.coroutines.flow.Flow

interface SessionRepositoryInterface {
    var selectedSession: List<Session>
    suspend fun getAllSavedSessions(): List<List<Session>>
    suspend fun getSessionById(id: Int): List<Session>
    suspend fun getExerciseById(id: String): Exercise
    fun getSessionName(sessions: List<Session>): String
}