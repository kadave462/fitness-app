package com.example.myfitnessapp.viewmodels.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import com.example.myfitnessapp.models.ExerciseCategory


class ExerciseFilter(categories: List<ExerciseCategory>) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _filteredCategories = MutableStateFlow(categories)

    val filteredCategories: StateFlow<List<ExerciseCategory>> = _searchQuery
        .map { query ->
            if (query.isBlank()) {
                categories
            } else {
                categories.map { category ->
                    category.copy(
                        exercises = category.exercises.filter { exercise ->
                            exercise.secondaryMuscles.any { it.contains(query, ignoreCase = true) }
                                    || exercise.secondaryMuscles.any { it.contains(query, ignoreCase = true) }
                        }
                    )
                }.filter { it.exercises.isNotEmpty() }
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, categories)

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}