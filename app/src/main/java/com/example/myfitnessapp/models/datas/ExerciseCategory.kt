package com.example.myfitnessapp.models

import com.example.myfitnessapp.models.datas.Exercise

data class ExerciseCategory(
    val category: String,
    val exercises: List<Exercise>
)
