package com.example.android.navigation

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android.common.OnDestinationChanged
import com.example.android.presentation.home.HomeScreen
import com.example.android.presentation.splash.SplashScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun Navigation(window: Window){

    val systemUiController = rememberSystemUiController()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        composable(Screen.Splash.route){
            OnDestinationChanged(
                systemUiController = systemUiController,
                color = Color.Transparent,
                drawOverStatusBar = true,
                window = window
            )

            SplashScreen(navController = navController)
        }


        composable(Screen.Home.route){
            OnDestinationChanged(
                systemUiController = systemUiController,
                color = Color.Transparent,
                drawOverStatusBar = true,
                window = window
            )

            HomeScreen(navController = navController)
        }

    }
}