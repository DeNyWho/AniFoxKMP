package com.example.android.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


private val DarkColorPallete = darkColors(
    background = blackP,
    primary = white
)

private val LightColorPallete = lightColors(
    background = white,
    primary = blackP
)

@Composable
fun AniFoxTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {

    val colors = if(darkTheme) {
        DarkColorPallete
    } else LightColorPallete

    MaterialTheme(
        typography = Typography,
        shapes = Shapes,
        colors = colors,
        content = content
    )
}