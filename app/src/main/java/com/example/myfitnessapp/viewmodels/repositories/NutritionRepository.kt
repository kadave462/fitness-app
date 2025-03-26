package com.example.myfitnessapp.viewmodels.repositories

import android.content.Context
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.database.daos.NutritionTotals
import com.example.myfitnessapp.models.entities.Food
import com.example.myfitnessapp.models.entities.MealEntry
import com.example.myfitnessapp.models.entities.NutritionGoal
import com.example.myfitnessapp.models.entities.User

class NutritionRepository(private val context: Context, private val userId: Int) {
    private val database = AppDatabase.getDatabase(context)
    private val foodDao = database.getFoodDao()
    private val mealEntryDao = database.getMealEntryDao()
    private val nutritionGoalDao = database.getNutritionGoalDao()

    suspend fun searchFoods(query: String): List<Food> {
        return foodDao.searchFoods("%$query%")
    }

    suspend fun getFoodById(foodId: Int): Food? {
        return foodDao.getFoodById(foodId)
    }

    suspend fun addCustomFood(food: Food): Long {
        return foodDao.insertFood(food.copy(isUserCreated = true))
    }

    // Meal entry operations
    suspend fun addMealEntry(foodId: Int, servings: Double, mealType: String, date: String): Long {
        val entry = MealEntry(
            userId = userId,
            foodId = foodId,
            servings = servings,
            mealType = mealType,
            date = date
        )
        return mealEntryDao.insertMealEntry(entry)
    }

    suspend fun deleteMealEntry(entryId: Int) {
        mealEntryDao.deleteMealEntry(entryId)
    }

    suspend fun getMealEntriesForDate(date: String): Map<MealEntry, Food> {
        return mealEntryDao.getMealEntriesForDate(userId, date)
    }

    suspend fun getNutritionTotalsForDate(date: String): NutritionTotals {
        return mealEntryDao.getNutritionTotalsForDate(userId, date)
    }

    // Nutrition goal operations
    suspend fun getNutritionGoal(): NutritionGoal? {
        return nutritionGoalDao.getNutritionGoal(userId)
    }

    suspend fun setNutritionGoal(calorieGoal: Int, proteinGoal: Double, carbsGoal: Double, fatGoal: Double) {
        val goal = NutritionGoal(
            userId = userId,
            calorieGoal = calorieGoal,
            proteinGoal = proteinGoal,
            carbsGoal = carbsGoal,
            fatGoal = fatGoal
        )
        nutritionGoalDao.setNutritionGoal(goal)
    }

    // Helper method to generate nutrition goals based on user information
    suspend fun generateDefaultNutritionGoals(user: User): NutritionGoal {
        // Basic calculation based on gender, weight, height, age, and activity level
        // This is a simplified example - you'd want more sophisticated calculations in a real app

        val bmr = if (user.gender == "Homme") {
            // Harris-Benedict formula for men
            88.362 + (13.397 * user.weight) + (4.799 * user.height) - (5.677 * user.getAge())
        } else {
            // Harris-Benedict formula for women
            447.593 + (9.247 * user.weight) + (3.098 * user.height) - (4.330 * user.getAge())
        }

        // Multiply by activity factor based on user.level
        val activityFactor = when (user.level) {
            "Débutant" -> 1.2 // Sedentary
            "Intermédiaire" -> 1.55 // Moderately active
            "Avancé" -> 1.725 // Very active
            else -> 1.375 // Default to lightly active
        }

        val calorieGoal = (bmr * activityFactor).toInt()

        // Macronutrient distribution (example: 30% protein, 40% carbs, 30% fat)
        val proteinGoal = (calorieGoal * 0.3 / 4).toDouble() // 4 calories per gram of protein
        val carbsGoal = (calorieGoal * 0.4 / 4).toDouble() // 4 calories per gram of carbs
        val fatGoal = (calorieGoal * 0.3 / 9).toDouble() // 9 calories per gram of fat

        return NutritionGoal(
            userId = userId,
            calorieGoal = calorieGoal,
            proteinGoal = proteinGoal,
            carbsGoal = carbsGoal,
            fatGoal = fatGoal
        )
    }
}