package com.example.myfitnessapp.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Modifiers {
    val innerPadding: Dp = 8.dp
    val outerPadding: Dp = 16.dp


    val screenModifier = Modifier
        .fillMaxSize()
        .padding(outerPadding)

    val containerModifier = Modifier
        .fillMaxWidth()
        .padding(innerPadding)

    @Composable
    fun getScreenWidth(): Dp {
        return LocalConfiguration.current.screenWidthDp.dp
    }

    @Composable
    fun getScreenHeight(): Dp {
        return LocalConfiguration.current.screenHeightDp.dp
    }



}