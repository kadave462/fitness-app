package com.example.myfitnessapp.viewmodels.repositories

import android.content.Context
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.entities.User
import java.time.LocalDate
import java.time.Period

class UserRepository(context: Context, private val user: User) {
    private val db = AppDatabase.getDatabase(context)
    private val dao = db.getUserDao()

    fun getUser(): User {
        return user
    }

    suspend fun updateUserLevel(level: String){
        dao.updateUserLevel(user.id, level)
    }

    suspend fun getAge(): Int {
        val birthDate = LocalDate.parse(dao.getBirthDate(user.id))
        val today = LocalDate.now()
        val period = Period.between(birthDate, today)
        return period.years
    }
}