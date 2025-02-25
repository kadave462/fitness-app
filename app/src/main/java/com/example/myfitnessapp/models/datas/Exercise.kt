package com.example.myfitnessapp.models.datas

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey val id: String,
    val name: String,
    val bodyPart: String,
    val target: String,
    val secondaryMuscles: List<String>,
    val gifUrl: String,
    var gif: File? = null)

