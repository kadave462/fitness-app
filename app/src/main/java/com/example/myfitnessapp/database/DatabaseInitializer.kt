package com.example.myfitnessapp.database // Make sure this is your correct package name

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun populateDatabase(context: Context) { // 1. suspend fun populateDatabase
    withContext(Dispatchers.IO) { // 2. withContext(Dispatchers.IO)
        val database = AppDatabase.getDatabase(context) // 3. Get AppDatabase instance
        val muscleDao = database.muscleDao() // 4. Get MuscleDao instance

        if (muscleDao.getAllMuscles().isEmpty()) { // 5. Check if database is empty
            val initialMuscles = listOf( // 6. Define initial muscles
                Muscle(muscleName = "abductors", defaultSets = 3, defaultReps = 12),
                Muscle(muscleName = "abs", defaultSets = 3, defaultReps = 15),
                Muscle(muscleName = "adductors", defaultSets = 3, defaultReps = 12),
                Muscle(muscleName = "biceps", defaultSets = 3, defaultReps = 10),
                Muscle(muscleName = "calves", defaultSets = 3, defaultReps = 15),
                Muscle(muscleName = "cardiovascular system", defaultSets = 1, defaultReps = 1), // Adjust as needed
                Muscle(muscleName = "delts", defaultSets = 3, defaultReps = 10),
                Muscle(muscleName = "forearms", defaultSets = 3, defaultReps = 12),
                Muscle(muscleName = "glutes", defaultSets = 3, defaultReps = 12),
                Muscle(muscleName = "hamstrings", defaultSets = 3, defaultReps = 12),
                Muscle(muscleName = "lats", defaultSets = 3, defaultReps = 10),
                Muscle(muscleName = "levator scapulae", defaultSets = 2, defaultReps = 15),
                Muscle(muscleName = "pectorals", defaultSets = 3, defaultReps = 10),
                Muscle(muscleName = "quads", defaultSets = 3, defaultReps = 12),
                Muscle(muscleName = "serratus anterior", defaultSets = 2, defaultReps = 15),
                Muscle(muscleName = "spine", defaultSets = 1, defaultReps = 1), // Adjust as needed
                Muscle(muscleName = "traps", defaultSets = 3, defaultReps = 10),
                Muscle(muscleName = "triceps", defaultSets = 3, defaultReps = 10),
                Muscle(muscleName = "upper back", defaultSets = 3, defaultReps = 10)
            )
            muscleDao.insertMuscles(initialMuscles) // 7. Insert initial muscles
        }
    }
}