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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import com.example.myfitnessapp.models.entities.Exercise
import com.example.myfitnessapp.ui.theme.Modifiers


@Composable
fun ProgressionBar(
    modifiers: Modifiers,
    selectedExercises: MutableList<Exercise>,
    currentIndex: Int,
    currentSetIndex: Int,
    totalSets: Int
) {
    val totalExercises = selectedExercises.size
    val totalSteps = totalExercises * totalSets
    val currentStep = (currentIndex * totalSets) + currentSetIndex

    val progress = remember { Animatable(0f) }

    LaunchedEffect(currentStep) {
        val targetProgress = if (totalSteps > 1) currentStep.toFloat() / (totalSteps - 1) else 0f
        progress.animateTo(targetProgress, animationSpec = tween(durationMillis = 500))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Progression : ${currentStep + 1} / $totalSteps",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { progress.value },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(10.dp)),
            color = Color.Black,
            trackColor = Color.Gray,
        )
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