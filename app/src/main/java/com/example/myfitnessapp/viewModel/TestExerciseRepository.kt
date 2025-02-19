package com.example.myfitnessapp.viewModel

import androidx.compose.runtime.mutableStateListOf
import com.example.myfitnessapp.models.datas.Exercise
import com.example.myfitnessapp.models.datas.ExerciseCategory
import com.example.myfitnessapp.models.network.ExerciceClient

class TestExerciseRepository : Repository {

    override val api = ExerciceClient
    override var allExercises: List<Exercise>? = null //Metrre en mutable ?

    override val selectedExercises = mutableStateListOf<Exercise>()

    override var allCategories: MutableList<ExerciseCategory> = mutableListOf(
        ExerciseCategory("Cardio", listOf(
            Exercise(id = "", name = "Pompes", target = "Poids du corps", bodyPart = "Pectoraux",  secondaryMuscles = listOf(), gifUrl = "https://example.com/pompes.gif", gif = null),
            Exercise(id = "", name = "Squats", bodyPart = "Jambes", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://example.com/squats.gif", gif = null),),
        ),
        ExerciseCategory("Musculation", listOf(
            Exercise(id = "", name = "Tractions", bodyPart = "Dos", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://example.com/tractions.gif", gif = null),
            Exercise(id = "", name = "Développé couché", bodyPart = "Pectoraux", target = "Haltères", secondaryMuscles = listOf(), gifUrl = "https://example.com/developpe.gif", gif = null),),
        )

    )
    override val exerciseViewModel = ExerciseViewModel(allCategories)



}