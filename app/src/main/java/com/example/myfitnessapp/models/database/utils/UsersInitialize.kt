package com.example.myfitnessapp.models.database.utils

import android.content.Context
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun populateUserDatabase(context: Context) {
    withContext(Dispatchers.IO) {
        val database = AppDatabase.getDatabase(context)
        val userDao = database.getUserDao()

        val user = User(
            id = 1,
            email = "alex.laffite@gmail.com",
            password = "123456",
            pseudonym = "AlexL",
            firstName = "Alex",
            lastName = "Laffite",
            weight = 80.0,
            height = 180,
            birthdate = "1995-06-15",
            gender = "Homme",
            level = "Beginner"
        )

        if (userDao.getUserById(1) == null) {
            userDao.insertUser(user)
        }
    }
}