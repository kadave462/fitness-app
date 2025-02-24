package com.example.myfitnessapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


private val DarkColorScheme = darkColorScheme(
    primary = acidGreen,
    onPrimary = darkBlue,
    primaryContainer = lightAcidGreen,
    onPrimaryContainer = darkBlue,

    secondary = mediumBlue,
    onSecondary = darkBlue,
    secondaryContainer = lightBlue,
    onSecondaryContainer = darkBlue,

    tertiary = mediumDarkBlue,
    onTertiary = lightGrey,
    tertiaryContainer = lightGrey,
    onTertiaryContainer = darkBlue,

    background = darkBlue,
    onBackground = lightGrey,
    surface = mediumDarkBlue,
    onSurface = lightGrey,

    error = darkRed,
    onError = lightGrey,
    errorContainer = lightRed,
    onErrorContainer = darkRed

)

private val LightColorScheme = lightColorScheme(
    primary = acidGreen,
    onPrimary = darkBlue,
    primaryContainer = lightAcidGreen,
    onPrimaryContainer = darkBlue,

    secondary = mediumBlue,
    onSecondary = darkBlue,
    secondaryContainer = lightBlue,
    onSecondaryContainer = darkBlue,

    tertiary = mediumDarkBlue,
    onTertiary = lightGrey,
    tertiaryContainer = mediumBlue,
    onTertiaryContainer = darkBlue,

    background = darkBlue,
    onBackground = lightGrey,
    surface = mediumDarkBlue,
    onSurface = lightGrey,

    error = darkRed,
    onError = lightGrey,
    errorContainer = lightRed,
    onErrorContainer = darkRed

)

@Composable
fun lightOrDarkColorScheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true): ColorScheme {

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    return colorScheme
}

@Composable
fun MyFitnessAppTheme(
    content: @Composable () -> Unit
) {
    var colorScheme = lightOrDarkColorScheme()

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        shapes = shapes,

    )
}