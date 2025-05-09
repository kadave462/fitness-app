package com.example.myfitnessapp.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myfitnessapp.ui.theme.Modifiers

@Composable
fun TagView(
    modifiers: Modifiers,
    text: String
){
    Box(
        modifier = modifiers
            .onContainerModifier
            .background(
                MaterialTheme.colorScheme.tertiaryContainer,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = modifiers.onContainerModifier
        )
    }
}

@Preview
@Composable
fun PreviewTagView() {
    TagView(modifiers = Modifiers(), text = "Biceps")

}