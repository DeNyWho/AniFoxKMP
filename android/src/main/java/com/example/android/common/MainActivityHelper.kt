package com.example.android.common

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun OnDestinationChanged(
    systemUiController: SystemUiController = rememberSystemUiController(),
    color: Color = Color.Transparent,
    drawOverStatusBar: Boolean = false,
    window: Window
) {

    SideEffect {
        systemUiController.setSystemBarsColor(color = color)
    }

    WindowCompat.setDecorFitsSystemWindows(window, !drawOverStatusBar)
}