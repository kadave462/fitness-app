package com.example.myfitnessapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.myfitnessapp.models.entities.Exercise
import com.example.myfitnessapp.models.interfaces.SessionRepositoryInterface
import com.example.myfitnessapp.ui.theme.Modifiers

@Composable
fun BackAndForthButtons(
    modifiers: Modifiers,
    selectedExercises: MutableList<Exercise>,
    currentIndex: Int,
    currentSetIndex: Int,
    totalSets: Int,
    onIndexChange: (Int) -> Unit,
    onSetChange: (Int) -> Unit,
    navController: NavController,
    navigation: String,
    sessionRepository: SessionRepositoryInterface
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifiers.containerModifier
    ) {
        Button(
            onClick = {
                if (currentSetIndex > 0) {
                    onSetChange(currentSetIndex - 1)
                } else if (currentIndex > 0) {
                    onIndexChange(currentIndex - 1)
                }
            },
            enabled = currentIndex > 0 || currentSetIndex > 0
        ) {
            Text("Précédent", style = MaterialTheme.typography.bodySmall)
        }

        if (currentIndex < selectedExercises.size - 1 || currentSetIndex < totalSets - 1) {
            Button(
                onClick = {
                    if (currentSetIndex < totalSets - 1) {
                        onSetChange(currentSetIndex + 1)
                    } else {
                        navController.navigate("break_screen") {
                            launchSingleTop = true
                        }
                    }
                }
            ) {
                Text("Suivant", style = MaterialTheme.typography.bodySmall)
            }
        } else {
            Button(
                onClick = { navController.navigate(navigation) },
            ) {
                Text("Terminer", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun PreviewBackAndForthButtons() {
<<<<<<< HEAD
    val sampleExercises = mutableListOf(
        Exercise(id = "", name = "Pompes", target = "Poids du corps", bodyPart = "Pectoraux",  secondaryMuscles = listOf(), gifUrl = "https://example.com/pompes.gif"),
        Exercise(id = "", name = "Squats", bodyPart = "Jambes", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://example.com/squats.gif"),
        Exercise(id = "", name = "Tractions", bodyPart = "Dos", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://example.com/tractions.gif"),
        Exercise(id = "", name = "Développé couché", bodyPart = "Pectoraux", target = "Haltères", secondaryMuscles = listOf(), gifUrl = "https://example.com/developpe.gif")
=======
    val sampleExercises = listOf(
        ExerciseResponse(id = "", name = "Pompes", target = "Poids du corps", bodyPart = "Pectoraux",  secondaryMuscles = listOf(), gifUrl = "https://example.com/pompes.gif"),
        ExerciseResponse(id = "", name = "Squats", bodyPart = "Jambes", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://example.com/squats.gif"),
        ExerciseResponse(id = "", name = "Tractions", bodyPart = "Dos", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://example.com/tractions.gif"),
        ExerciseResponse(id = "", name = "Développé couché", bodyPart = "Pectoraux", target = "Haltères", secondaryMuscles = listOf(), gifUrl = "https://example.com/developpe.gif")
>>>>>>> origin/david
    )
    //var currentIndex by remember { mutableIntStateOf(0) }
    val navController = rememberNavController()
    val navigation = ""

    BackAndForthButtons(sampleExercises, currentIndex, { currentIndex = it }, navController, navigation)
} */
