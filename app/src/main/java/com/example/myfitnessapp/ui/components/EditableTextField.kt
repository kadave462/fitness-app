package com.example.myfitnessapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun EditableTextField(
    label: String,
    value: String,
    onValueChange: ((String) -> Unit)? = null,
    readOnly: Boolean = false
) {
    var text by remember { mutableStateOf(value) }

    LaunchedEffect(value) {
        text = value
    }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange?.invoke(it)
        },
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyLarge,
        readOnly = readOnly
    )
}