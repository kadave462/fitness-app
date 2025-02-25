package com.example.myfitnessapp.models.datas

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.Period

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val email: String,
    val pseudonym: String,
    val firstName: String,
    val lastName: String,
    val weight: Double,
    val height: Int,
    val birthdate: Int,
    val gender: String,
    val level: String = "Debutant",
    var profilePictureUri: String? = null
)/*{

    fun getAge(): Int {
        val today = LocalDate.now()
        val period = Period.between(birthdate, today)
        return period.years
    }

} */