package com.example.myfitnessapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.models.Exercise
import com.example.myfitnessapp.models.ExerciseResponse

@Composable
fun BackAndForthButtons(
    selectedExercises: MutableList<Exercise>,
    currentIndex: Int,
    onIndexChange: (Int) -> Unit,
    navController: NavController,
    navigation: String,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { if (currentIndex > 0) onIndexChange(currentIndex - 1) },
            enabled = currentIndex > 0
        ) {
            Text("Précédent")
        }

        if (currentIndex < selectedExercises.size - 1) {
            Button(
                onClick = { onIndexChange(currentIndex + 1) },
            ) {
                Text("Suivant")
            }
        } else {
            Button(
                onClick = { navController.navigate(navigation) },
            ) {
                Text("Terminer")
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun PreviewBackAndForthButtons() {
    val sampleExercises = MutableListOf(
        Exercise(id = "", name = "Pompes", target = "Poids du corps", bodyPart = "Pectoraux",  secondaryMuscles = listOf(), gifUrl = "https://example.com/pompes.gif"),
        Exercise(id = "", name = "Squats", bodyPart = "Jambes", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://example.com/squats.gif"),
        Exercise(id = "", name = "Tractions", bodyPart = "Dos", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://example.com/tractions.gif"),
        Exercise(id = "", name = "Développé couché", bodyPart = "Pectoraux", target = "Haltères", secondaryMuscles = listOf(), gifUrl = "https://example.com/developpe.gif")
    )
    var currentIndex by remember { mutableIntStateOf(0) }
    val navController = rememberNavController()
    val navigation = ""

    BackAndForthButtons(sampleExercises, currentIndex, { currentIndex = it }, navController, navigation)
}
*/