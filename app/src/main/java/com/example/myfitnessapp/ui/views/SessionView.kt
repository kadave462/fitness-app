package com.example.myfitnessapp.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myfitnessapp.models.entities.Session
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.viewmodels.repositories.tests.TestSessionRepository

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SessionView(modifier: Modifier = Modifiers().containerModifier,
                session: List<Session>, isSelected: Boolean = false, onDelete: (Int) -> Unit = {}) {

    var name = session[0].name.toString()

    Row(
        modifier = modifier.background(color = MaterialTheme.colorScheme.tertiary, shape = MaterialTheme.shapes.medium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
            Column(
                modifier = modifier.weight(0.7f)
            ) {
                Text(
                    text = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                    maxLines = 2,
                    style = MaterialTheme.typography.titleSmall
                )

            Box(
                modifier = modifier,
                contentAlignment = Alignment.CenterStart
            ) {
                FlowRow(
                    modifier = Modifiers().onContainerModifier,
                ) {
                    session.forEach { exercise ->
                        exercise.exerciseId?.let { TagView(Modifiers(), it) }
                    }
                }

                }

            }
        Box(modifier = modifier.weight(0.2f)) {
         if(isSelected){
             Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifiers().onContainerModifier.clickable {
                 onDelete(session[0].id)
             })
         }
        }
    }
}

@Composable
@Preview
fun PreviewSessionView() {
    val sessionRepository = TestSessionRepository()
    val session = sessionRepository.sessions[0]
    val test: (isS: Boolean) -> Unit = {}

    SessionView(
        session = session,
        isSelected = true,
    )
}