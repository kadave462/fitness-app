package com.example.myfitnessapp.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import com.example.myfitnessapp.ui.components.BackAndForthButtons
import com.example.myfitnessapp.ui.components.Chronometer
import com.example.myfitnessapp.ui.components.ProgressionBar
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.viewmodels.utils.provideImageLoader
import com.example.myfitnessapp.viewmodels.repositories.ExerciseRepository
import com.example.myfitnessapp.viewmodels.utils.ChronometerUtils


@Composable
fun SessionScreen(modifiers: Modifiers, navController: NavController, repository: ExerciseRepository) {
    var selectedExercises = repository.selectedExercises
    var currentIndex by remember { mutableIntStateOf(0) }
    val currentExercise = selectedExercises[currentIndex]


    //added Database State Variables:
    val context = LocalContext.current
    var defaultSets by remember { mutableStateOf<Int?>(null) }
    var defaultReps by remember { mutableStateOf<Int?>(null) }
    val database = AppDatabase.getDatabase(context)
    val muscleDao = database.muscleDao()

    //end of  Database State Variables:

    // Block to Fetch default sets and reps from database
    LaunchedEffect(currentExercise.name) { // Keyed by exercise name
        val targetMuscleNameFromApi = currentExercise.target // Use target from ExerciseResponse

        if (targetMuscleNameFromApi != null) {
            val muscle = muscleDao.getMuscleByName(targetMuscleNameFromApi)
            if (muscle != null) {
                defaultSets = muscle.defaultSets
                defaultReps = muscle.defaultReps
            } else {
//                Log.e("SessionScreen", "Muscle '$targetMuscleNameFromApi' not found in database for exercise '${currentExercise.name}'!")
                defaultSets = 0
                defaultReps = 0
            }
        } else {
//            Log.e("SessionScreen", "API Exercise data does not provide target muscle for '${currentExercise.name}'!")
            defaultSets = 0
            defaultReps = 0
        }
    }
    // end of Block to Fetch default sets and reps from database



    Column(
        modifier = modifiers.bigPaddingModifier(true),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 60.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = currentExercise.name,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 2,
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

        ProgressionBar(modifiers, selectedExercises, currentIndex)

        Spacer(modifier = Modifier.fillMaxHeight(0.1f))


        Text(text = "Repetitions: ${defaultReps ?: "-"}")

        Spacer(modifier = Modifier.height(8.dp))


        Text(text = "Series: ${defaultSets ?: "-"}")

        Chronometer(viewModel = ChronometerUtils())

        Spacer(modifier = Modifier.fillMaxHeight(0.6f))

        BackAndForthButtons(
            modifiers = modifiers,
            selectedExercises = selectedExercises,
            currentIndex = currentIndex,
            onIndexChange = { newIndex -> currentIndex = newIndex },
            navController = navController,
            navigation = "session_end_screen"
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