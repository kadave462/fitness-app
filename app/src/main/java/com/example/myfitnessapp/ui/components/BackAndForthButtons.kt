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

@Composable
fun BackAndForthButtons(
    selectedExercises: List<Exercise>,
    currentIndex: Int,
    onIndexChange: (Int) -> Unit,
    navController: NavController
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
                onClick = { navController.navigate("endSessionScreen") },
            ) {
                Text("Terminer")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBackAndForthButtons() {
    val sampleExercises = listOf(
        Exercise(name = "Pompes", muscularGroup = "Pectoraux", type = "Poids du corps", urlgif = "https://example.com/pompes.gif"),
        Exercise(name = "Squats", muscularGroup = "Jambes", type = "Poids du corps", urlgif = "https://example.com/squats.gif"),
        Exercise(name = "Tractions", muscularGroup = "Dos", type = "Poids du corps", urlgif = "https://example.com/tractions.gif"),
        Exercise(name = "Développé couché", muscularGroup = "Pectoraux", type = "Haltères", urlgif = "https://example.com/developpe.gif")
    )
    var currentIndex by remember { mutableIntStateOf(0) }
    val navController = rememberNavController()

    BackAndForthButtons(sampleExercises, currentIndex, { currentIndex = it }, navController)
}
