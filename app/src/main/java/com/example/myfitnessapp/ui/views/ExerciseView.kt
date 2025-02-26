package com.example.myfitnessapp.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.myfitnessapp.models.entities.Exercise
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.ui.theme.titleXSmall

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExerciseView(
    modifiers: Modifiers,
    exercise: Exercise,
    isSelected: Boolean,
    index: Int?,
    onSelectionChange: (Boolean) -> Unit
) {
    val shapes = Shapes()

    Row(
        modifier = modifiers
            .containerModifier
            .background(MaterialTheme.colorScheme.primary, shape = shapes.medium)
            .clickable { onSelectionChange(!isSelected) }
            .padding(modifiers.innerPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            model = exercise.gifUrl,
            contentDescription = "Image de l'exercice",
            modifier = Modifier
                .weight(0.3f)
                .clip(shapes.medium),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .weight(0.7f)
                .padding(start = modifiers.bigPadding),
        ) {
            Text(
                text = exercise.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                maxLines = 2,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Box(
                modifier = modifiers.containerModifier,
                contentAlignment = Alignment.CenterStart
            ){
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                ) {
                    exercise.secondaryMuscles.forEach { muscle ->
                        SecondaryMuscleTagView(modifiers, muscle)
                    }
                }

            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .weight(0.2f)
                .fillMaxWidth()
        ) {
            if (isSelected) {
                Text(
                    text = index.toString(),
                    style = MaterialTheme.typography.titleXSmall,
                    modifier = Modifier.padding(modifiers.innerPadding),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onSelectionChange(it) },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.onPrimary,
                    uncheckedColor = MaterialTheme.colorScheme.onPrimary,
                    checkmarkColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExerciseViewPreview() {

    val modifiers = Modifiers()

    val exercise =  Exercise(id = "", name = "Pompes", target = "Poids du corps", bodyPart = "Pectoraux",  secondaryMuscles = listOf("test", "test"), gifUrl = "https://example.com/pompes.gif", gif = null)
    val test: (isS: Boolean) -> Unit = {}

    ExerciseView(modifiers, exercise, true, 1, test)
}