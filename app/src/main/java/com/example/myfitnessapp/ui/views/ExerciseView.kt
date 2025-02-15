package com.example.myfitnessapp.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@Composable
fun ExerciseView(exercise: ExerciseResponse, isSelected: Boolean, index: Int?, onSelectionChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = exercise.name, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(text = exercise.target, fontSize = 12.sp, color = Color.Gray)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
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