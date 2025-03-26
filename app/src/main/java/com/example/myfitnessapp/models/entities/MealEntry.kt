package com.example.myfitnessapp.models.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.myfitnessapp.models.entities.Food
import com.example.myfitnessapp.models.entities.User

@Entity(
    tableName = "meal_entries",
    foreignKeys = [
        ForeignKey(
            entity = Food::class,
            parentColumns = ["id"],
            childColumns = ["foodId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MealEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val foodId: Int,
    val servings: Double,
    val mealType: String, // "Breakfast", "Lunch", "Dinner", "Snack"
    val date: String, // ISO date format "yyyy-MM-dd"
    val timestamp: Long = System.currentTimeMillis()
)
