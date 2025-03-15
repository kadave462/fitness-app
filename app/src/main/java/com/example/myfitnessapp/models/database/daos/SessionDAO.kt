package com.example.myfitnessapp.models.database.daos

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myfitnessapp.models.entities.Exercise
import com.example.myfitnessapp.models.entities.Session

@Dao
interface SessionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sessions: List<Session>){
        Log.d("SessionS", "Inserting sessions: $sessions")
    }


    @Query("SELECT * FROM sessions WHERE id = :sessionId")
    suspend fun getSessionById(sessionId: Int): List<Session>


    @Query("SELECT MAX(id) FROM sessions")
    suspend fun getLastSessionId(): Int?

    @Query("SELECT * FROM sessions")
    suspend fun getAllSessions(): List<Session>





}