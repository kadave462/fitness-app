package com.example.myfitnessapp.models.interfaces

import com.example.myfitnessapp.models.entities.Session

interface SessionRepositoryInterface {
    suspend fun getAllSavedSessions(): List<List<Session>>
}