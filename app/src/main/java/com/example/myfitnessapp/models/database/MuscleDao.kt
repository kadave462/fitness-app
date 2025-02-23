package com.example.myfitnessapp.models.database // Make sure this is your correct package name

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MuscleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // 3. @Insert annotation
    suspend fun insertMuscle(muscle: Muscle) // 4. insertMuscle function

    @Insert(onConflict = OnConflictStrategy.REPLACE) // 5. @Insert for insertMuscles
    suspend fun insertMuscles(muscles: List<Muscle>) // 6. insertMuscles function

    @Query("SELECT * FROM muscles WHERE muscle_name = :muscleName") // 7. @Query for getMuscleByName
    suspend fun getMuscleByName(muscleName: String): Muscle? // 8. getMuscleByName function

    @Query("SELECT * FROM muscles") // 9. @Query for getAllMuscles
    suspend fun getAllMuscles(): List<Muscle> // 10. getAllMuscles function

    // You can add more DAO functions here as needed for updating, deleting, etc.
}