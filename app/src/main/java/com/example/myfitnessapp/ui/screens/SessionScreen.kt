package com.example.myfitnessapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myfitnessapp.ViewModel.ExerciseRepository
import com.example.myfitnessapp.ui.components.BackAndForthButtons
import com.example.myfitnessapp.ui.components.Chronometer
import com.example.myfitnessapp.ui.components.ProgressionBar
import com.example.myfitnessapp.ViewModel.utils.ChronometerUtils
import com.example.myfitnessapp.ViewModel.utils.provideImageLoader

@Composable
fun SessionScreen(navController: NavController, repository: ExerciseRepository) {
    var selectedExercises = repository.selectedExercises
    var currentIndex by remember { mutableIntStateOf(0) }
    val currentExercise = selectedExercises[currentIndex]


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = currentExercise.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = currentExercise.gifUrl,
            imageLoader = provideImageLoader(context = LocalContext.current),
            contentDescription = "GIF de ${currentExercise.name}",
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(64.dp))

        ProgressionBar(selectedExercises, currentIndex)

        Spacer(modifier = Modifier.height(32.dp))

        Chronometer(viewModel = ChronometerUtils())

        Spacer(modifier = Modifier.height(192.dp))

        BackAndForthButtons(
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
        ExerciseResponse(id = "", name = "Pompes", target = "Poids du corps", bodyPart = "Pectoraux", secondaryMuscles = listOf(), gifUrl = "https://media.tenor.com/0sKxFzX_QpIAAAAC/push-up.gif"),
        ExerciseResponse(id = "", name = "Squats", bodyPart = "Jambes", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://media.tenor.com/Bzqv5io1K1YAAAAC/squat-workout.gif"),
        ExerciseResponse(id = "", name = "Tractions", bodyPart = "Dos", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://media.tenor.com/YX9Rs7yCr6EAAAAC/pull-up.gif"),
        ExerciseResponse(id = "", name = "Développé couché", bodyPart = "Pectoraux", target = "Haltères", secondaryMuscles = listOf(), gifUrl = "https://media.tenor.com/6m8QHxdxRE4AAAAC/bench-press.gif")
    )

    SessionScreen(navController, sampleExercises)
} */