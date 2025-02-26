package com.example.myfitnessapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.models.entities.Exercise
import com.example.myfitnessapp.ui.theme.Modifiers

@Composable
fun ProgressionBar(
    modifiers: Modifiers,
    selectedExercises: MutableList<Exercise>,
    currentIndex: Int,
    currentSetIndex: Int,
    totalSets: Int,
    isBreak: Boolean,
    showPauseMarkers: Boolean
) {
    val totalExercises = selectedExercises.size
    val totalSteps = totalExercises * totalSets
    val currentStep = (currentIndex * totalSets) + currentSetIndex + 1

    val progress = remember { Animatable(1f / totalSteps) }

    LaunchedEffect(currentStep) {
        val targetProgress = if (totalSteps > 0) currentStep.toFloat() / totalSteps.toFloat() else 0f
        progress.animateTo(targetProgress, animationSpec = tween(durationMillis = 500))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isBreak) {
            Text(
                text = "Progression : $currentStep / $totalSteps",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray)
        ) {
            LinearProgressIndicator(
                progress = { if (isBreak) 0f else progress.value },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
                color = if (isBreak) Color.Transparent else Color.Black,
                trackColor = Color.Gray,
            )

            if (isBreak) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val dashWidth = 20.dp.toPx()
                    val dashGap = 10.dp.toPx()
                    val strokeWidth = 8.dp.toPx()
                    val totalWidth = size.width

                    var currentX = 0f
                    while (currentX < totalWidth) {
                        drawLine(
                            color = Color.White,
                            start = Offset(currentX, size.height / 2),
                            end = Offset(currentX + dashWidth, size.height / 2),
                            strokeWidth = strokeWidth
                        )
                        currentX += dashWidth + dashGap
                    }
                }
            }

            if (showPauseMarkers) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val stepWidth = size.width / totalSteps.toFloat()
                    for (i in 1..totalExercises) {
                        val xPosition = (i * totalSets * stepWidth).coerceAtMost(size.width)
                        drawLine(
                            color = Color.Red,
                            start = Offset(xPosition - 1f, 0f),
                            end = Offset(xPosition - 1f, size.height),
                            strokeWidth = 3.dp.toPx()
                        )
                    }
                }
            }
        }
    }
}


/*
@Preview(showBackground = true)
@Composable
fun PreviewProgressionBar() {
    val modifiers = Modifiers()

    val sampleExercises = mutableListOf(
        Exercise(
            id = "",
            name = "Pompes",
            target = "Poids du corps",
            bodyPart = "Pectoraux",
            secondaryMuscles = listOf(),
            gifUrl = "https://example.com/pompes.gif"
        ),
        Exercise(
            id = "",
            name = "Squats",
            bodyPart = "Jambes",
            target = "Poids du corps",
            secondaryMuscles = listOf(),
            gifUrl = "https://example.com/squats.gif"
        ),
        Exercise(
            id = "",
            name = "Tractions",
            bodyPart = "Dos",
            target = "Poids du corps",
            secondaryMuscles = listOf(),
            gifUrl = "https://example.com/tractions.gif"
        ),
        Exercise(
            id = "",
            name = "Développé couché",
            bodyPart = "Pectoraux",
            target = "Haltères",
            secondaryMuscles = listOf(),
            gifUrl = "https://example.com/developpe.gif"
        )
    )

    var currentIndex by remember { mutableIntStateOf(0) }
    val navController = rememberNavController()
    val navigation = ""

    ProgressionBar(modifiers, sampleExercises, currentIndex)

    Spacer(modifier = Modifier.height(16.dp))

    BackAndForthButtons(
        modifiers = modifiers,
        sampleExercises,
        currentIndex,
        { currentIndex = it },
        navController,
        navigation
    )
}
*/