package com.example.myfitnessapp.models.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muscles")
data class Muscle(
    @PrimaryKey(autoGenerate = true)
    val muscleId: Int = 0,
    @ColumnInfo(name = "muscle_name")
    val muscleName: String,
    @ColumnInfo(name = "default_sets")
    val defaultSets: Int,
    @ColumnInfo(name = "default_reps")
    val defaultReps: Int
)