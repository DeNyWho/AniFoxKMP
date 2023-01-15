package com.example.android.presentation.splash

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
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = getViewModel()
) {

    splash()
    LaunchedEffect(Unit) {
        navController.navigate(route = Screen.Home.route)
    }
}


@Preview
@Composable
fun splash(){

    val modifier = if (isSystemInDarkTheme()){
        Modifier.background(Color.Black)
    } else {
        Modifier.background(
            Brush.verticalGradient(listOf(bluest, blueLikeSky))
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