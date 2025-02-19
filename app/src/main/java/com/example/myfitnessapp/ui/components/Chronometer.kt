package com.example.myfitnessapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.viewmodels.utils.ChronometerUtils
import com.example.myfitnessapp.viewmodels.utils.TimeUtils

@Composable
fun Chronometer(viewModel: ChronometerUtils) {
    val time by viewModel.time

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = TimeUtils.formatTime(time),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = { viewModel.startChronometer() }) {
                Text("Démarrer")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.stopChronometer() }) {
                Text("Arrêter")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.resetChronometer() }) {
                Text("Réinitialiser")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChronometerScreen() {
    Chronometer(viewModel = ChronometerUtils())
}
