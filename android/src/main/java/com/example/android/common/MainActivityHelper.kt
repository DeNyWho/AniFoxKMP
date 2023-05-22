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
    statusBarColor: Color = Color.Transparent,
    navigationBarColor: Color = Color.Transparent,
    drawOverStatusBar: Boolean = false,
    window: Window
) {

    SideEffect {
        systemUiController.setStatusBarColor(statusBarColor)
        systemUiController.setNavigationBarColor(navigationBarColor)
    }

    WindowCompat.setDecorFitsSystemWindows(window, !drawOverStatusBar)
}