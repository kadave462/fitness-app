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
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.models.Exercise
import com.example.myfitnessapp.models.ExerciseResponse
import com.example.myfitnessapp.ui.theme.Purple80

@Composable
fun ProgressionBar(selectedExercises: List<ExerciseResponse>, currentIndex: Int) {
    val totalExercises = selectedExercises.size
    val progress = remember { Animatable(0f) }

    LaunchedEffect(currentIndex) {
        val targetProgress =
            if (totalExercises > 1) currentIndex.toFloat() / (totalExercises - 1) else 0f
        progress.animateTo(targetProgress, animationSpec = tween(durationMillis = 500))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Progression : ${currentIndex + 1} / $totalExercises",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { progress.value },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(10.dp)),
            color = Purple80,
            trackColor = Color.Gray,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProgressionBar() {
    val sampleExercises = listOf(
        ExerciseResponse(id = "", name = "Pompes", target = "Poids du corps", bodyPart = "Pectoraux",  secondaryMuscles = listOf(), urlGif = "https://example.com/pompes.gif"),
        ExerciseResponse(id = "", name = "Squats", bodyPart = "Jambes", target = "Poids du corps", secondaryMuscles = listOf(), urlGif = "https://example.com/squats.gif"),
        ExerciseResponse(id = "", name = "Tractions", bodyPart = "Dos", target = "Poids du corps", secondaryMuscles = listOf(), urlGif = "https://example.com/tractions.gif"),
        ExerciseResponse(id = "", name = "Développé couché", bodyPart = "Pectoraux", target = "Haltères", secondaryMuscles = listOf(), urlGif = "https://example.com/developpe.gif")
    )

    var currentIndex by remember { mutableIntStateOf(0) }
    val navController = rememberNavController()
    val navigation = ""

    ProgressionBar(sampleExercises, currentIndex)

    Spacer(modifier = Modifier.height(16.dp))

    BackAndForthButtons(sampleExercises, currentIndex, { currentIndex = it }, navController, navigation)
}