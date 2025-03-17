package com.example.myfitnessapp.viewmodels.repositories.tests

import com.example.myfitnessapp.models.entities.Exercise
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

    val exercises = listOf(
            Exercise(id = "1", name = "Exo1", bodyPart = "Chest", target = "Pectorals", secondaryMuscles = listOf("Triceps", "Shoulders"), gifUrl = "https://example.com/push-up.gif"),
            Exercise(id = "2", name = "Exo2", bodyPart = "Legs", target = "Quadriceps", secondaryMuscles = listOf("Glutes", "Hamstrings"), gifUrl = "https://example.com/squat.gif"),
            Exercise(id = "3", name = "Exo3", bodyPart = "Back", target = "Lats", secondaryMuscles = listOf("Biceps", "Forearms"), gifUrl = "https://example.com/pull-up.gif"),
            Exercise(id = "4", name = "Exo4", bodyPart = "Full Body", target = "Lower Back", secondaryMuscles = listOf("Glutes", "Hamstrings", "Traps"), gifUrl = "https://example.com/deadlift.gif"),
            Exercise(id = "5", name = "Exo5", bodyPart = "Arms", target = "Biceps", secondaryMuscles = listOf("Forearms"), gifUrl = "https://example.com/bicep-curl.gif")
        )

    override var selectedSession = sessions.first()



    override suspend fun getAllSavedSessions(): List<List<Session>>{
        return sessions
    }

    override suspend fun getSessionById(id: Int): List<Session>{
        return sessions.first { it.first().id == id }
    }

    override suspend fun getExerciseById(id: String): Exercise{
        return exercises.first { it.id == id }
    }

}