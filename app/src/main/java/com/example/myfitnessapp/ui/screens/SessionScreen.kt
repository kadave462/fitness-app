package com.example.myfitnessapp.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.ui.components.BackAndForthButtons
import com.example.myfitnessapp.ui.components.Chronometer
import com.example.myfitnessapp.ui.components.ProgressionBar
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.viewmodels.utils.provideImageLoader
import com.example.myfitnessapp.viewmodels.repositories.ExerciseRepository
import com.example.myfitnessapp.viewmodels.repositories.SessionRepository
import com.example.myfitnessapp.viewmodels.utils.ChronometerUtils


@Composable
fun SessionScreen(
    modifiers: Modifiers,
    navController: NavController,
    user: User,
    repository: ExerciseRepository,
    currentIndex: Int,
    onIndexChange: (Int) -> Unit
) {
    val selectedExercises = repository.selectedExercises
    val sessionRepository = SessionRepository(user, LocalContext.current, selectedExercises)

    val currentExercise = selectedExercises[currentIndex]
    val defaultSets = sessionRepository.getNumberOfSet()
    var currentSetIndex by remember { mutableIntStateOf(0) }
    var defaultReps by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(currentExercise.name) {
        defaultReps = sessionRepository.getNumberOfReps(currentExercise, user)
    }

    Column(
        modifier = modifiers.bigPaddingModifier(true),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = currentExercise.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
            )
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 10.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Série ${currentSetIndex + 1} / $defaultSets",
                style = MaterialTheme.typography.titleSmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = currentExercise.gifUrl,
            imageLoader = provideImageLoader(context = LocalContext.current),
            contentDescription = "GIF de ${currentExercise.name}",
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.45f)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        ProgressionBar(
            modifiers = modifiers,
            selectedExercises = selectedExercises,
            currentIndex = currentIndex,
            currentSetIndex = currentSetIndex,
            totalSets = sessionRepository.getNumberOfSet(),
            isBreak = false,
            showPauseMarkers = true
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        Chronometer(viewModel = ChronometerUtils())

        Spacer(modifier = Modifier.fillMaxHeight(0.2f))

        Text(
            text = "Répétez l'exercice affiché à l'écran ci-dessus $defaultReps fois",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.3f))

        BackAndForthButtons(
            modifiers = modifiers,
            selectedExercises = selectedExercises,
            currentIndex = currentIndex,
            currentSetIndex = currentSetIndex,
            totalSets = sessionRepository.getNumberOfSet(),
            onIndexChange = { newIndex ->
                onIndexChange(newIndex)
                currentSetIndex = 0
            },
            onSetChange = { newSetIndex -> currentSetIndex = newSetIndex },
            navController = navController,
            navigation = "break_screen"
        )
    }
}

/*
@Preview(showBackground = true)
@Composable
fun PreviewSessionScreen() {
    val navController = rememberNavController()
    val sampleExercises = listOf(
<<<<<<< HEAD
        ExerciseResponse(id = "", name = "Pompes", target = "Poids du corps", bodyPart = "Pectoraux", secondaryMuscles = listOf(), gifUrl = "https://media.tenor.com/0sKxFzX_QpIAAAAC/push-up.gif"),
        ExerciseResponse(id = "", name = "Squats", bodyPart = "Jambes", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://media.tenor.com/Bzqv5io1K1YAAAAC/squat-workout.gif"),
        ExerciseResponse(id = "", name = "Tractions", bodyPart = "Dos", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://media.tenor.com/YX9Rs7yCr6EAAAAC/pull-up.gif"),
        ExerciseResponse(id = "", name = "Développé couché", bodyPart = "Pectoraux", target = "Haltères", secondaryMuscles = listOf(), gifUrl = "https://media.tenor.com/6m8QHxdxRE4AAAAC/bench-press.gif")
=======
        ExerciseResponse(id = "", name = "Pompes", target = "Poids du corps", bodyPart = "Pectoraux",  secondaryMuscles = listOf(), gifUrl = "https://v2.exercisedb.io/image/XCcm9Nve61IfOR"),
        ExerciseResponse(id = "", name = "Squats", bodyPart = "Jambes", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://v2.exercisedb.io/image/XCcm9Nve61IfOR"),
        ExerciseResponse(id = "", name = "Tractions", bodyPart = "Dos", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://v2.exercisedb.io/image/XCcm9Nve61IfOR"),
        ExerciseResponse(id = "", name = "Développé couché", bodyPart = "Pectoraux", target = "Haltères", secondaryMuscles = listOf(), gifUrl = "https://v2.exercisedb.io/image/XCcm9Nve61IfOR")
>>>>>>> origin/david
    )

    SessionScreen(navController, sampleExercises)
} */