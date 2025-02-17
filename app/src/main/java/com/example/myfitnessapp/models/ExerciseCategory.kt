package com.example.myfitnessapp.models

data class ExerciseCategory(
    val category: String,
    val exercises: List<Exercise>
) {
    companion object {

    }
}