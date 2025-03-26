package com.example.myfitnessapp.models.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.myfitnessapp.models.entities.User

@Entity(
    tableName = "nutrition_goals",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class NutritionGoal(
    @PrimaryKey val userId: Int,
    val calorieGoal: Int,
    val proteinGoal: Double,
    val carbsGoal: Double,
    val fatGoal: Double
)