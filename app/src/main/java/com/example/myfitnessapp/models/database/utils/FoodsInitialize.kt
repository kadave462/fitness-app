package com.example.myfitnessapp.models.database.utils

import android.content.Context
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.entities.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun populateFoodsDatabase(context: Context) {
    withContext(Dispatchers.IO) {
        val database = AppDatabase.getDatabase(context)
        val foodDao = database.getFoodDao()

        // Check if foods already exist
        val existingFoods = foodDao.searchFoods("%")
        if (existingFoods.isNotEmpty()) {
            return@withContext
        }

        // Sample food list
        val sampleFoods = listOf(
            Food(
                name = "Apple",
                calories = 95,
                protein = 0.5,
                carbs = 25.0,
                fat = 0.3,
                servingSize = 1.0,
                servingUnit = "medium"
            ),
            Food(
                name = "Banana",
                calories = 105,
                protein = 1.3,
                carbs = 27.0,
                fat = 0.4,
                servingSize = 1.0,
                servingUnit = "medium"
            ),
            Food(
                name = "Chicken Breast",
                calories = 165,
                protein = 31.0,
                carbs = 0.0,
                fat = 3.6,
                servingSize = 100.0,
                servingUnit = "g"
            ),
            Food(
                name = "Egg",
                calories = 68,
                protein = 5.5,
                carbs = 0.6,
                fat = 4.8,
                servingSize = 1.0,
                servingUnit = "large"
            ),
            Food(
                name = "Salmon",
                calories = 206,
                protein = 22.0,
                carbs = 0.0,
                fat = 13.0,
                servingSize = 100.0,
                servingUnit = "g"
            ),
            Food(
                name = "Brown Rice",
                calories = 216,
                protein = 5.0,
                carbs = 45.0,
                fat = 1.8,
                servingSize = 1.0,
                servingUnit = "cup"
            ),
            Food(
                name = "Oatmeal",
                calories = 158,
                protein = 6.0,
                carbs = 27.0,
                fat = 3.0,
                servingSize = 1.0,
                servingUnit = "cup"
            ),
            Food(
                name = "Greek Yogurt",
                calories = 100,
                protein = 17.0,
                carbs = 6.0,
                fat = 0.7,
                servingSize = 100.0,
                servingUnit = "g"
            ),
            Food(
                name = "Whole Milk",
                calories = 149,
                protein = 7.7,
                carbs = 12.0,
                fat = 8.0,
                servingSize = 1.0,
                servingUnit = "cup"
            ),
            Food(
                name = "Protein Powder",
                calories = 120,
                protein = 24.0,
                carbs = 3.0,
                fat = 1.5,
                servingSize = 30.0,
                servingUnit = "g"
            )
        )

        // Insert sample foods
        sampleFoods.forEach { food ->
            foodDao.insertFood(food)
        }
    }
}