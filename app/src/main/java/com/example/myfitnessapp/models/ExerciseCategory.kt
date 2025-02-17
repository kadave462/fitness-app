package com.example.myfitnessapp.models

data class ExerciseCategory(
    val category: String,
    val exercises: List<Exercise>
) {
    companion object {
        fun groupByBodyPart(exercises: List<Exercise>): List<ExerciseCategory> {
            return exercises.groupBy { it.bodyPart }
                .map { (bodyPart, exercises) ->
                    ExerciseCategory(
                        category = bodyPart.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                        exercises = exercises
                    )
                }
        }
    }
}