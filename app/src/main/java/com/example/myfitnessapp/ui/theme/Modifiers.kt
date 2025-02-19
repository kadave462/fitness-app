package com.example.myfitnessapp.ui.theme

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Modifiers {
    val innerPadding: Dp = 8.dp
    val bigPadding: Dp = 16.dp

    val bigSeparation: Dp = 32.dp
    val mediumSeparation: Dp = 16.dp


    fun bigPaddingModifier(isFullHeight: Boolean = false): Modifier{
        if(isFullHeight){
            return Modifier
                .fillMaxSize()
                .padding(bigPadding)
        }
        return return Modifier
            .fillMaxWidth()
            .padding(bigPadding)

    }


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

    @Composable
    fun getBigSpacer(){
        return Spacer(modifier = Modifier.height(bigSeparation))
    }

    @Composable
    fun getMediumSpacer(){
        return Spacer(modifier = Modifier.height(mediumSeparation))
    }



}


