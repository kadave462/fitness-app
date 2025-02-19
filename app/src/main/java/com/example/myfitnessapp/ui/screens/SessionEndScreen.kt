package com.example.myfitnessapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.models.datas.Exercise
import com.example.myfitnessapp.models.datas.User
import com.example.myfitnessapp.viewmodels.repositories.ExerciseRepository
import com.example.myfitnessapp.ui.components.FloatingButtonView
import com.example.myfitnessapp.ui.theme.Modifiers

@Composable
fun SessionEndScreen(
    modifiers: Modifiers,
    navController: NavController,
    user: User,
    repository: ExerciseRepository
) {
    val userName = user.pseudonym
    var selectedExercises = repository.selectedExercises

    Box(
        modifier = modifiers.bigPaddingModifier(false),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = modifiers.getScreenHeight()/3)
        ) {
            Text(
                text = "Félicitations, $userName !",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

            modifiers.getBigSpacer()

            Text(
                text = "Vous avez terminé la séance avec succès.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = modifiers.containerModifier
                    .wrapContentWidth(Alignment.Start)
            )

            Text(
                text = "Vous avez réalisé les exercices suivants :",
                style = MaterialTheme.typography.bodyLarge,
                modifier = modifiers.containerModifier
                    .wrapContentWidth(Alignment.Start)
            )

            selectedExercises.forEach { exercise ->
                Text(
                    text = "- ${exercise.name}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = modifiers.containerModifier
                        .wrapContentWidth(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            FloatingButtonView(
                title = "Revenir à l'écran d'accueil",
            ) {
                navController.navigate("home_screen")
                selectedExercises.clear()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSessionEndScreen(){
    val navController = rememberNavController()
    val user = User(10, "AlexL", "Alex", "Laffite", "alex.laffite@gmail.com", 80.0, 180, 25, "Homme", "Débutant")
    val modifiers = Modifiers()
    val sampleExercises = mutableListOf(
        Exercise(id = "", name = "Pompes", target = "Poids du corps", bodyPart = "Pectoraux",  secondaryMuscles = listOf(), gifUrl = "https://example.com/pompes.gif", gif = null),
        Exercise(id = "", name = "Squats", bodyPart = "Jambes", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://example.com/squats.gif", gif = null),
        Exercise(id = "", name = "Tractions", bodyPart = "Dos", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://example.com/tractions.gif", gif = null),
        Exercise(id = "", name = "Développé couché", bodyPart = "Pectoraux", target = "Haltères", secondaryMuscles = listOf(), gifUrl = "https://example.com/developpe.gif", gif = null)
    )

    SessionEndScreen(modifiers, navController, user, ExerciseRepository())
}