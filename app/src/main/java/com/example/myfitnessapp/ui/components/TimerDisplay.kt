package com.example.myfitnessapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.viewmodels.utils.TimeUtils.Companion.formatTime

@Composable
fun TimerDisplay(
    modifiers: Modifiers,
    shapes: Shapes,
    remainingTime: Long,
    isRunning: Boolean,
    onToggle: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .background(MaterialTheme.colorScheme.primaryContainer, shape = shapes.medium)
            .padding(modifiers.bigPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Minuteur",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = formatTime(remainingTime),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onToggle,
        ) {
            Icon(
                imageVector = if (isRunning) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                contentDescription = if (isRunning) "Arrêter" else "Démarrer",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimerDisplayPreview() {
    val shapes= Shapes()
    val modifiers = Modifiers()

    TimerDisplay(
        modifiers = modifiers,
        remainingTime = 60000,
        isRunning = false,
        onToggle = {},
        shapes = shapes
    )
}

