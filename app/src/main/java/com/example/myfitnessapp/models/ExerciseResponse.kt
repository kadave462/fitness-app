package com.example.myfitnessapp.models

data class ExerciseResponse(
    val id: String,
    val name: String,
    val bodyPart: String,
    val target: String,
    val secondaryMuscles: List<String>,
    val gifUrl: String
)
