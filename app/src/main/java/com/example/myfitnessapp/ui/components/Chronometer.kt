package com.example.myfitnessapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfitnessapp.viewmodels.utils.TimeUtils
import com.example.myfitnessapp.viewmodels.utils.ChronometerUtils

@Composable
fun Chronometer(viewModel: ChronometerUtils) {
    val isRunning by viewModel.isRunning
    val time by viewModel.time

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = TimeUtils.formatTime(time),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(
                onClick = {
                    if (isRunning) {
                        viewModel.stopChronometer()
                    } else {
                        viewModel.startChronometer()
                    }
                }
            ) {
                Icon(
                    imageVector = if (isRunning) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = if (isRunning) "Arrêter" else "Démarrer"
                )
            }

            Button(
                onClick = { viewModel.resetChronometer() },
                enabled = time > 0
            ) {
                Icon(
                    imageVector = Icons.Filled.Replay,
                    contentDescription = "Réinitialiser"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChronometerScreen() {
    Chronometer(viewModel = ChronometerUtils())
}
