package com.example.android.presentation.splash

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.android.R
import com.example.android.navigation.Screen
import com.example.android.ui.blueLikeSky
import com.example.android.ui.bluest
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = getViewModel()
) {
    splash()
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(route = Screen.Home.route)
    }
}

@Composable
fun splash(){
    val modifier = if (isSystemInDarkTheme()){
        Modifier.background(Color.Black)
    } else {
        Modifier.background(
            Brush.radialGradient (listOf(blueLikeSky, bluest), radius =  1800f)
        )
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.anifox_logo___text ),
            contentDescription = "stringResource(R.string.logo)"
        )
    }
}

@Preview
@Composable
fun splashPrevDay(){
    splash()
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun splashPrevNight(){
    splash()
}