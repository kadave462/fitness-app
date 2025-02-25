package com.example.myfitnessapp.models.database.utils // Make sure this is your correct package name

import android.content.Context
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.entities.Muscle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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