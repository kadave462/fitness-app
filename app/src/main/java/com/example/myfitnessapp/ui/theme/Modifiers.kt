package com.example.myfitnessapp.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class Modifiers {
    val innerPadding: PaddingValues = PaddingValues(5.dp)
    val outerPadding: PaddingValues = PaddingValues(10.dp)

    val screenModifier = Modifier
        .fillMaxSize()
        .padding(outerPadding)

    val containerModifier = Modifier
        .fillMaxWidth()
        .padding(innerPadding)
}