package com.example.myfitnessapp.models.datas

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sessions", primaryKeys = ["id", "exerciseId"])
data class Session (
    val id: Int,
    val exerciseId: Int,
    var name: String?,
    val totalSets: Int
)