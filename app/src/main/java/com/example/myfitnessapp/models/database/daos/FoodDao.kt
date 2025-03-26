package com.example.myfitnessapp.models.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myfitnessapp.models.entities.Food

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: Food): Long



    @Query("SELECT * FROM foods WHERE name LIKE :query ORDER BY name ASC")
    suspend fun searchFoods(query: String): List<Food>

    @Query("SELECT * FROM foods WHERE id = :foodId")
    suspend fun getFoodById(foodId: Int): Food?

    @Query("SELECT * FROM foods WHERE isUserCreated = 1 AND name LIKE :query ORDER BY name ASC")
    suspend fun getUserCreatedFoods(query: String = "%"): List<Food>
}