package com.example.myfitnessapp.models.datas

import androidx.room.Entity

@Entity(tableName = "user_sessions", primaryKeys = ["user_id", "session_id"])
data class UserSession(
    val user_id: String,
    val session_id: String,
) {
}