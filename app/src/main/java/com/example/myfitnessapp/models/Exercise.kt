package com.example.myfitnessapp.models


data class Exercise(
    val id: String,
    val name: String,
    val bodyPart: String,
    val target: String,
    val secondaryMuscles: List<String>,
    val gifUrl: String,
)