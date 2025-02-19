package com.example.myfitnessapp.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfitnessapp.models.datas.Exercise
import com.example.myfitnessapp.models.datas.ExerciseCategory
import com.example.myfitnessapp.ui.theme.Modifiers

@Composable
fun CategoryView(modifiers: Modifiers, category: ExerciseCategory, selectedExercises: MutableList<Exercise>) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = category.category,
                style = MaterialTheme.typography.titleMedium
            )
        }

        HorizontalDivider(thickness = 1.dp, color = Color.Gray)

        if (expanded) {
            category.exercises.forEach { exercise ->
                val index = selectedExercises.indexOf(exercise) + 1
                val isSelected = selectedExercises.contains(exercise)

                ExerciseView(modifiers, exercise, isSelected, index) { isChecked ->
                    if (isChecked) {
                        if (!selectedExercises.contains(exercise)) {
                            selectedExercises.add(exercise)
                        }
                    } else {
                        selectedExercises.remove(exercise)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCategoryView() {
    val modifiers = Modifiers()
    val sampleCategory = ExerciseCategory(
        "Pectoraux", listOf(
            Exercise(id = "", name = "Pompes", target = "Poids du corps", bodyPart = "Pectoraux",  secondaryMuscles = listOf(), gifUrl = "https://example.com/pompes.gif", gif = null),
            Exercise(id = "", name = "Squats", bodyPart = "Jambes", target = "Poids du corps", secondaryMuscles = listOf(), gifUrl = "https://example.com/squats.gif", gif = null),
        )
    )
    val exercises = remember { mutableStateListOf<Exercise>() }

    CategoryView(modifiers, category = sampleCategory, exercises)
}

