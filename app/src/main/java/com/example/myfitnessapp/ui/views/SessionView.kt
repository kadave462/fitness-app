package com.example.myfitnessapp.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myfitnessapp.models.entities.Exercise
import com.example.myfitnessapp.models.entities.Session
import com.example.myfitnessapp.ui.theme.Modifiers

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SessionView(modifiers: Modifiers, session: List<Session>) {
    var name = session[0].name.toString()
    Row(
        modifier = modifiers.bigPaddingModifier(false).background(color = MaterialTheme.colorScheme.tertiary, shape = MaterialTheme.shapes.medium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = modifiers.containerModifier.weight(0.7f)
        ) {
            Text(
                text = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                maxLines = 2,
                style = MaterialTheme.typography.titleSmall
            )

            Box(
                modifier = modifiers.containerModifier,
                contentAlignment = Alignment.CenterStart
            ) {
                FlowRow(
                    modifier = modifiers.onContainerModifier,
                ) {
                    session.forEach { exercise ->
                        exercise.exerciseId?.let { SecondaryMuscleTagView(modifiers, it) }
                    }
                }

            }
        }
    }
}

@Composable
@Preview
fun PreviewSessionView() {
    val session: List<Session> = listOf(
        Session(
            id = 1,
            exerciseId = "ex1",
            name = "Jambes",
            totalSets = 4
        ),
        Session(
            id = 2,
            exerciseId = "ex2",
            name = "Deadlift",
            totalSets = 3
        ),
        Session(
            id = 3,
            exerciseId = "ex3",
            name = "Bench Press",
            totalSets = 4
        ),
        Session(
            id = 4,
            exerciseId = "ex4",
            name = "Pull-up",
            totalSets = 3
        )
    )
    

    SessionView(
        modifiers = Modifiers(),
        session = session
    )
}