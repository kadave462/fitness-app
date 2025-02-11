package com.example.myfitnessapp.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun Greetings(userName: String) {
    Text(
        text = "Bonjour, $userName",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}