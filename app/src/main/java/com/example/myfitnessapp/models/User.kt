package com.example.myfitnessapp.models

data class User(
    val id: Int,
    val pseudonym: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val weight: Double,
    val height: Int,
    val age: Int,
    val gender: String,
    val level: String,
    var profilePictureUri: String? = null
)