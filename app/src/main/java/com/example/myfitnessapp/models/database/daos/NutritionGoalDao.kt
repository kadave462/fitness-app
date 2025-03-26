package com.example.myfitnessapp.models.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myfitnessapp.models.entities.NutritionGoal

@Dao
interface NutritionGoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setNutritionGoal(goal: NutritionGoal)

    @Query("SELECT * FROM nutrition_goals WHERE userId = :userId")
    suspend fun getNutritionGoal(userId: Int): NutritionGoal?
}