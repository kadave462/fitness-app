package com.example.myfitnessapp.viewmodels.repositories

import com.example.myfitnessapp.models.ExerciseCategory
import com.example.myfitnessapp.models.datas.Exercise

import com.example.myfitnessapp.models.network.ExerciceClient

interface Repository {
    val api: ExerciceClient
    var allExercises: List<Exercise>?
    val selectedExercises: MutableList<Exercise>
    var allCategories: MutableList<ExerciseCategory>
    val exerciseViewModel: ExerciseViewModel
}