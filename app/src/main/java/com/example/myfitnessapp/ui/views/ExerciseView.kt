package com.example.myfitnessapp.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.models.Exercise
import com.example.myfitnessapp.models.ExerciseResponse

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExerciseView(exercise: ExerciseResponse, isSelected: Boolean, index: Int?, onSelectionChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = exercise.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium)

            FlowRow(
                modifier = Modifier.padding(top = 4.dp),
            ) {
                exercise.secondaryMuscles.forEach { muscle ->
                    Box(
                        modifier = Modifier
                            .padding(end = 4.dp, bottom = 4.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(text = muscle, fontSize = 12.sp, color = Color.Black)
                    }
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            if (isSelected) {
                Text(text = index.toString(), fontSize = 14.sp, modifier = Modifier.padding(end = 8.dp))
            }
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onSelectionChange(it) }
            )
        }
    }
}