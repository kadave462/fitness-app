package com.example.myfitnessapp.viewmodels.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegistrationViewModel : ViewModel() {
    var pseudonym by mutableStateOf("")
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var weight by mutableStateOf("")
    var height by mutableStateOf("")
    var birthdate by mutableStateOf("")
    var gender by mutableStateOf("Femme")
    var level by mutableStateOf("Débutant")

    var hasTriedSubmit by mutableStateOf(false)

    fun isPseudonymValid() = pseudonym.isNotBlank()
    fun isFirstNameValid() = firstName.isNotBlank()
    fun isLastNameValid() = lastName.isNotBlank()
    fun isWeightValid() = weight.toDoubleOrNull()?.let { it > 0 } == true
    fun isHeightValid() = height.toIntOrNull()?.let { it > 0 } == true
    fun isBirthdateValid() = birthdate.isNotBlank()

    fun isFormValid(): Boolean {
        return isPseudonymValid() &&
                isFirstNameValid() &&
                isLastNameValid() &&
                isWeightValid() &&
                isHeightValid() &&
                isBirthdateValid()
    }

    fun onSubmit(onValid: () -> Unit) {
        hasTriedSubmit = true
        if (isFormValid()) {
            onValid()
        }
    }
}