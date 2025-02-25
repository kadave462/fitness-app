package com.example.myfitnessapp.models.entities

import androidx.room.Entity

@Entity(tableName = "sessions", primaryKeys = ["id", "exerciseId"])
data class Session(
    val id: Int,
    val exerciseId: String,
    var name: String?,
    val totalSets: Int
)