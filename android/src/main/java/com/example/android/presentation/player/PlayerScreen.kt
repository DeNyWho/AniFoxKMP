package com.example.android.presentation.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.android.composable.WebViewComponent
import com.example.common.nav.ContentPlayerNavArgs

@Composable
fun PlayerScreen(
    navController: NavController,
    navArgs: ContentPlayerNavArgs
){
    Box(modifier = Modifier.fillMaxSize()) {
        WebViewComponent("https:${navArgs.playerUrl.replace("-", "/")}")
    }
}