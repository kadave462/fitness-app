package com.example.myfitnessapp.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.models.datas.Exercise
import com.example.myfitnessapp.models.datas.User
import com.example.myfitnessapp.ui.theme.Modifiers

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExerciseView(modifiers: Modifiers, exercise: Exercise, isSelected: Boolean, index: Int?, onSelectionChange: (Boolean) -> Unit) {
    Row(
        modifier = modifiers.containerModifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = exercise.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                style = MaterialTheme.typography.titleSmall)

            Box(modifier = modifiers.containerModifier,
                contentAlignment = Alignment.CenterStart){
                FlowRow(
                    modifier = Modifier.padding(modifiers.innerPadding),
                ) {
                    exercise.secondaryMuscles.forEach { muscle ->
                        SecondaryMuscleTagView(muscle)
                    }
                }

            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isSelected) {
                Text(text = index.toString(), style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(modifiers.innerPadding) )
            }
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onSelectionChange(it) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

    val modifiers = Modifiers()

    val exercise =  Exercise(id = "", name = "Pompes", target = "Poids du corps", bodyPart = "Pectoraux",  secondaryMuscles = listOf("test", "test"), gifUrl = "https://example.com/pompes.gif", gif = null)
    val test: (isS: Boolean) -> Unit = {}

    ExerciseView(modifiers, exercise, true, 1, test)
}