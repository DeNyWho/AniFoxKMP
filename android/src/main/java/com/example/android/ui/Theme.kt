package com.example.android.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


private val DarkColorPallete = darkColors(
    background = slate800,
    primary = slate200,
    secondaryVariant = slate700,
    onSurface = slate700,
    onPrimary = onDarkSurface,
    surface = darkGreyBackground,
)

private val LightColorPallete = lightColors(
    background = LightBackground,
    primary = slate800,
    secondaryVariant = slate300,
    onSurface = smokyWhite,
    onPrimary = blackP,
    surface = lighterGray
)

@Composable
fun AniFoxTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {

    val colors = if(darkTheme) {
        DarkColorPallete
    } else LightColorPallete

    MaterialTheme(
        typography = MyTypography(colors),
        shapes = Shapes.ThemeShapes,
        colors = colors,
        content = content
    )
}