package com.example.myfitnessapp.models.datas

data class Session (
    val selectedExercises: MutableList<Exercise> = mutableListOf(),
) {
    fun addExercise(exercise: Exercise) {
        if (!selectedExercises.contains(exercise)) {
            selectedExercises.add(exercise)
        }
    }

    fun removeExercise(exercise: Exercise) {
        if (selectedExercises.contains(exercise)) {
            selectedExercises.remove(exercise)
        }
    }

    fun getExerciseOrder(exercise: Exercise): Int {
        return selectedExercises.indexOf(exercise) + 1
    }
}