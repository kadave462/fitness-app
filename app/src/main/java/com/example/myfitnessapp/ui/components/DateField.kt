package com.example.myfitnessapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateField(
    label: String,
    date: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    val month = remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    val day = remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = "$selectedYear-${
                (selectedMonth + 1).toString().padStart(2, '0')
            }-${selectedDay.toString().padStart(2, '0')}"
            onDateSelected(formattedDate)
        },
        year.value,
        month.value,
        day.value
    )

    OutlinedTextField(
        value = date,
        onValueChange = {},
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { datePickerDialog.show() },
        readOnly = true,
        trailingIcon = {
            Icon(
                Icons.Default.CalendarToday,
                contentDescription = "Select Date",
                modifier = Modifier.clickable { datePickerDialog.show() }
            )
        }
    )
}