package com.example.myfitnessapp.ui.views

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.myfitnessapp.ui.theme.Modifiers
import androidx.compose.runtime.setValue

@Composable
fun NewSessionView(modifier: Modifier = Modifiers().containerModifier, onDismiss: () -> Unit, onAdd: (String) -> Unit) {
    Text("Youpee")

    var sessionName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ajouter une session") },
        text = {
            TextField(value = sessionName, onValueChange = { sessionName = it })
        },
        confirmButton = {
            Button(onClick = { onAdd(sessionName); onDismiss() }) {
                Text(
                    text = "Valider",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    )
}