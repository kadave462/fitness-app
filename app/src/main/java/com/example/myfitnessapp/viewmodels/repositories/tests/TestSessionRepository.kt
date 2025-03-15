package com.example.myfitnessapp.viewmodels.repositories.tests

import com.example.myfitnessapp.models.entities.Session
import com.example.myfitnessapp.models.interfaces.SessionRepositoryInterface

class TestSessionRepository : SessionRepositoryInterface {
    val sessions = listOf(
        listOf(
            Session(id = 1, exerciseId = "ex1", name = "Bras", totalSets = 4),
            Session(id = 1, exerciseId = "ex2", name = "Bras", totalSets = 3),
            Session(id = 1, exerciseId = "ex3", name = "Bras", totalSets = 4),
            Session(id = 1, exerciseId = "ex4", name = "Bras", totalSets = 3)
        ),
        listOf(Session(id = 2, exerciseId = "ex1", name = "Jambes", totalSets = 4),
            Session(id = 2, exerciseId = "ex2", name = "Jambes", totalSets = 3),
            Session(id = 2, exerciseId = "ex3", name = "Jambes", totalSets = 4),
            Session(id = 2, exerciseId = "ex4", name = "Jambes", totalSets = 3)
        )
    )
        override suspend fun getAllSavedSessions(): List<List<Session>>{
            return sessions
        }
}