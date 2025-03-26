package com.example.myfitnessapp.models.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.myfitnessapp.models.entities.Food
import com.example.myfitnessapp.models.entities.MealEntry

@Dao
interface MealEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealEntry(mealEntry: MealEntry): Long

    @Query("DELETE FROM meal_entries WHERE id = :entryId")
    suspend fun deleteMealEntry(entryId: Int)

    @Transaction
    @Query("""
        SELECT me.*, f.* FROM meal_entries me
        JOIN foods f ON me.foodId = f.id
        WHERE me.userId = :userId AND me.date = :date
        ORDER BY me.timestamp DESC
    """)
    suspend fun getMealEntriesForDate(userId: Int, date: String): Map<MealEntry, Food>

    @Query("""
        SELECT 
            SUM(f.calories * me.servings) as totalCalories,
            SUM(f.protein * me.servings) as totalProtein,
            SUM(f.carbs * me.servings) as totalCarbs,
            SUM(f.fat * me.servings) as totalFat
        FROM meal_entries me
        JOIN foods f ON me.foodId = f.id
        WHERE me.userId = :userId AND me.date = :date
    """)
    suspend fun getNutritionTotalsForDate(userId: Int, date: String): NutritionTotals
}
data class NutritionTotals(
    val totalCalories: Int = 0,
    val totalProtein: Double = 0.0,
    val totalCarbs: Double = 0.0,
    val totalFat: Double = 0.0
)