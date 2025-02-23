package com.example.myfitnessapp.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfitnessapp.ui.theme.Modifiers


@Composable
fun SecondaryMuscleTagView(modifiers: Modifiers, muscle: String){
    Box(
        modifier = modifiers
            .onContainerModifier
            .background(MaterialTheme.colorScheme.tertiaryContainer
                , shape = MaterialTheme.shapes.medium),
    ) {
        Text(text = muscle,
            style = MaterialTheme.typography.labelMedium,
            modifier = modifiers.onContainerModifier)
    }
}

@Preview
@Composable
fun PreviewSecondaryMuscleTagView() {
        SecondaryMuscleTagView(modifiers = Modifiers(), muscle = "Biceps")

}