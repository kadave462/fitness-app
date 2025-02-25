package com.example.myfitnessapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.R
import com.example.myfitnessapp.ui.components.ProgressionBar
import com.example.myfitnessapp.ui.components.TimerDisplay
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.viewmodels.repositories.ExerciseRepository
import com.example.myfitnessapp.viewmodels.utils.TimerViewModel

@Composable
fun BreakScreen(
    modifiers: Modifiers,
    navController: NavController,
    repository: ExerciseRepository,
    currentIndex: Int,
    onIndexChange: (Int) -> Unit
) {
    val timerViewModel: TimerViewModel = viewModel()
    val shapes = Shapes()

    Column(
        modifier = modifiers.bigPaddingModifier(true),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth().heightIn(min = 80.dp), horizontalArrangement = Arrangement.Start) {
            Text(
                text = "Temps de pause",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.pause_image),
            contentDescription = "Image de pause",
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.45f)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        ProgressionBar(
            modifiers = modifiers,
            selectedExercises = repository.selectedExercises,
            currentIndex = currentIndex,
            currentSetIndex = 0,
            totalSets = 1,
            isBreak = true,
            showPauseMarkers = false
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.02f))

        Text("Veuillez prendre une pause avant de lancer le prochain exercice",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifiers.containerModifier
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        TimerDisplay(
            modifiers = modifiers,
            remainingTime = timerViewModel.remainingTime.value,
            isRunning = timerViewModel.isRunning.value,
            onToggle = { timerViewModel.toggleTimer() },
            shapes = shapes
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.3f))

        if (currentIndex + 1 == repository.selectedExercises.size) {
            Button(
                onClick = { navController.navigate("session_end_screen") }
            ) {
                Text("Fin de la séance",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        } else {
            Button(
                onClick = {
                    val nextIndex = currentIndex + 1
                    onIndexChange(nextIndex)
                    navController.navigate("session_screen")
                }
            ) {
                Text("Fin de la pause",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BreakScreenPreview() {
    val navController = rememberNavController()
    val repository = ExerciseRepository(LocalContext.current)

    BreakScreen(
        modifiers = Modifiers(),
        navController = navController,
        repository = repository,
        currentIndex = 0,
        onIndexChange = {}
    )
}