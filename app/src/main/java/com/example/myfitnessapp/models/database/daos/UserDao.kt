package com.example.myfitnessapp.models.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myfitnessapp.models.entities.User
import java.time.Period
import java.util.Date

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): User

    @Query("SELECT level FROM users WHERE id = :userId")
    suspend fun getUserLevel(userId: Int): String

    @Query("UPDATE users SET level = :level WHERE id = :userId")
    suspend fun updateUserLevel(userId: Int, level: String)

    @Query("SELECT birthdate FROM users WHERE id = :userId")
    suspend fun getBirthDate(userId: Int): String

}