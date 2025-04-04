package com.example.myfitnessapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.example.myfitnessapp.ui.theme.Modifiers

@Composable
fun FloatingButtonView(title: String, modifiers: Modifiers, enabled: Boolean = true, onClick: () -> Unit) {
    Box(
        modifier = modifiers.onContainerModifier,
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            enabled = enabled,
            shape = MaterialTheme.shapes.medium,
            modifier = modifiers.containerModifier
        ) {
            Text(text = title,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2 )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFloatingButtonView() {
    val modifiers = Modifiers()
    Column(
        modifier = modifiers.bigPaddingModifier(true),
        verticalArrangement = Arrangement.Center
    ) {
        FloatingButtonView(title = "Ajouter", modifiers) {}
    }
}