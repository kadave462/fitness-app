package com.example.myfitnessapp.models.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.myfitnessapp.models.entities.User
import java.time.Period
import java.util.Date

@Dao
interface UserDao {

    /*suspend fun getAge(userId: Int){
        val user = getUserById(userId)
        val birthdate = user.birthdate
        val today = LocalDate.now()
        val period = Period.between(birthdate, today)
        return period.years
    }*/

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): User

    @Query("SELECT level FROM users WHERE id = :userId")
    suspend fun getUserLevel(userId: Int): String

    @Query("UPDATE users SET level = :level WHERE id = :userId")
    suspend fun updateUserLevel(userId: Int, level: String)

    @Query("SELECT birthdate FROM users WHERE id = :userId")
    suspend fun getBirthDate(userId: Int): String

}