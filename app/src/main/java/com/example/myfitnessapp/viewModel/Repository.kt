package com.example.myfitnessapp.viewModel

import androidx.compose.runtime.mutableStateListOf
import com.example.myfitnessapp.models.datas.Exercise
import com.example.myfitnessapp.models.datas.ExerciseCategory
import com.example.myfitnessapp.models.network.ExerciceClient

interface Repository {
    val api: ExerciceClient
    var allExercises: List<Exercise>?
    val selectedExercises: MutableList<Exercise>
    var allCategories: MutableList<ExerciseCategory>
    val exerciseViewModel: ExerciseViewModel
}