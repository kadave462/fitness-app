package com.example.myfitnessapp.models.database.utils

import android.content.Context
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.entities.Muscle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.myfitnessapp.models.entities.User


suspend fun populateMusclesDatabase(context: Context) {
    withContext(Dispatchers.IO) {
        val database = AppDatabase.getDatabase(context)
        val muscleDao = database.getMuscleDao()
        val defaultReps = 10
        val bigMuscles = listOf("calves","levator scapulae", "serratus anterior", "quads")
        val mediumMuscles = listOf("abductors","abs", "abductors", "forearms","glutes","hamstrings")
        val smallMuscles = listOf("biceps", "delts", "lats", "pectorals", "spine", "traps", "triceps", "upper back")
        val cardio = listOf("cardiovascular system")


        if (muscleDao.getAllMuscles().isEmpty()) {
            for(muscleName in bigMuscles){
                val muscle = Muscle(muscleName = muscleName, defaultSets = 3, defaultReps = defaultReps)
                muscleDao.insertMuscle(muscle)
            }
            for(muscleName in mediumMuscles){
                val muscle = Muscle(muscleName = muscleName, defaultSets = 3, defaultReps = defaultReps)
                muscleDao.insertMuscle(muscle)
            }
            for(muscleName in smallMuscles){
                val muscle = Muscle(muscleName = muscleName, defaultSets = 3, defaultReps = defaultReps)
                muscleDao.insertMuscle(muscle)
            }
            for(muscleName in cardio){
                val muscle = Muscle(muscleName = muscleName, defaultSets = 1, defaultReps = 1)
                muscleDao.insertMuscle(muscle)
            }
        }
    }
}


// In MusclesInitialize.kt, add:
suspend fun createInitialAdminIfNeeded(context: Context) {
    withContext(Dispatchers.IO) {
        val database = AppDatabase.getDatabase(context)
        val userDao = database.getUserDao()

        // Check if there's any admin user
        val admins = userDao.getAllUsers().filter { it.isAdmin }

        if (admins.isEmpty()) {
            // Create initial admin user
            val adminPassword = "Admin123!"
            val hashedPassword = org.mindrot.jbcrypt.BCrypt.hashpw(adminPassword, org.mindrot.jbcrypt.BCrypt.gensalt())

            val adminUser = User(
                id = 0,
                email = "admin@myfitnessapp.com",
                passwordHash = hashedPassword,
                pseudonym = "Administrator",
                firstName = "Admin",
                lastName = "User",
                weight = 0.0,
                height = 0,
                birthdate = "2000-01-01",
                gender = "Other",
                level = "Débutant",
                isAdmin = true
            )

            userDao.insertUser(adminUser)
        }
    }
}