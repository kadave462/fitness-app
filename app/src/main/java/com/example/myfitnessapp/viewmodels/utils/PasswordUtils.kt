package com.example.myfitnessapp.viewmodels.utils

fun isPasswordSecure(password: String): Boolean {
    val minLength = 8
    val uppercasePattern = Regex("[A-Z]")
    val lowercasePattern = Regex("[a-z]")
    val digitPattern = Regex("[0-9]")
    val specialCharPattern = Regex("[^A-Za-z0-9]")

    return password.length >= minLength &&
            uppercasePattern.containsMatchIn(password) &&
            lowercasePattern.containsMatchIn(password) &&
            digitPattern.containsMatchIn(password) &&
            specialCharPattern.containsMatchIn(password)
}