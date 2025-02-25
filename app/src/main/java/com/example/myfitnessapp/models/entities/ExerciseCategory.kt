package com.example.myfitnessapp.models

import com.example.myfitnessapp.models.entities.Exercise

data class ExerciseCategory(
    val category: String,
    val exercises: List<Exercise>)
