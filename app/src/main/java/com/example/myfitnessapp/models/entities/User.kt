package com.example.myfitnessapp.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date


@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var email: String,
    var pseudonym: String,
    val firstName: String,
    val lastName: String,
    var weight: Double,
    var height: Int,
    var birthdate: String,
    var gender: String,
    var level: String,
    var profilePictureUri: String? = null
) {
    fun getAge(): Int {
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val birthLocalDate = LocalDate.parse(birthdate, formatter)
            val today = LocalDate.now()
            val age = Period.between(birthLocalDate, today)
            age.years
        } catch (e: Exception) {
            -1
        }
    }
}