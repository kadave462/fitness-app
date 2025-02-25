package com.example.myfitnessapp.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val email: String,
    val pseudonym: String,
    val firstName: String,
    val lastName: String,
    val weight: Double,
    val height: Int,
    val birthdate: String,
    val gender: String,
    val level: String = "Beginner",
    var profilePictureUri: String? = null
)/*{

    fun getAge(): Int {
        val today = LocalDate.now()
        val period = Period.between(birthdate, today)
        return period.years
    }

} */