package com.example.android.presentation.player

import android.app.Activity
import android.view.Window
import android.view.WindowManager
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import com.example.android.composable.WebViewComponent
import com.example.common.nav.ContentPlayerNavArgs
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun PlayerScreen(
    navController: NavController,
    navArgs: ContentPlayerNavArgs
){
    Box(modifier = Modifier.fillMaxSize()) {
        WebViewComponent("https:${navArgs.playerUrl.replace("-", "/")}")
        BackHandler {
            navController.popBackStack()
        }
    }
}
