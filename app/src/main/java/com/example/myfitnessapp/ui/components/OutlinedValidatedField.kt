package com.example.myfitnessapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun OutlinedValidatedField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    showError: Boolean,
    errorText: String = "Ce champ est requis"
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = showError,
        modifier = Modifier.fillMaxWidth()
    )
    if (showError) {
        Text(
            text = errorText,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall
        )
    }
}