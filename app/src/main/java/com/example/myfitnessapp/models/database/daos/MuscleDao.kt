package com.example.myfitnessapp.models.database.daos // Make sure this is your correct package name

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myfitnessapp.models.entities.Muscle

@Dao
interface MuscleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMuscle(muscle: Muscle)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMuscles(muscles: List<Muscle>)

    @Query("SELECT * FROM muscles WHERE muscle_name = :muscleName")
    suspend fun getMuscleByName(muscleName: String): Muscle?

    @Query("SELECT default_reps FROM muscles WHERE muscle_name = :muscleName")
    suspend fun getNumberOfReps(muscleName: String): Int?

    @Query("SELECT default_sets FROM muscles WHERE muscle_name = :muscleName")
    suspend fun getNumberOfSets(muscleName: String): Int?

    @Query("SELECT * FROM muscles")
    suspend fun getAllMuscles(): List<Muscle>
}