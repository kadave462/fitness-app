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

    suspend fun setPseudonym(pseudo: String){
        user.pseudonym = pseudo
        dao.updateUser(user)
    }

    suspend fun setWeight(weight: Double){
        user.weight = weight
        dao.updateUser(user)
    }

    suspend fun setEmail(email: String){
        user.email = email
        dao.updateUser(user)
    }

    suspend fun setHeight(height: Int){
        user.height = height
        dao.updateUser(user)
    }

    suspend fun setGender(gender: String){
        user.gender = gender
        dao.updateUser(user)
    }

    suspend fun setBirthDate(birthDate: String){
        user.birthdate = birthDate
        dao.updateUser(user)
    }

    suspend fun setProfilePictureUri(uri: String){
        user.profilePictureUri = uri
        dao.updateUser(user)
    }

}