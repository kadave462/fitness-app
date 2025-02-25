package com.example.myfitnessapp.models.datas

import java.time.LocalDate
import java.time.Period

data class User(
    val id: Int,
    val pseudonym: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val weight: Double,
    val height: Int,
    val birthdate: Int,
    val gender: String,
    val level: String,
    var profilePictureUri: String? = null
){

    fun getAge(): Int? {
        if(birthdate != null){
            val today = LocalDate.now()
            val period = Period.between(birthdate, today)
            return period.years
        }
        return null
    }

}