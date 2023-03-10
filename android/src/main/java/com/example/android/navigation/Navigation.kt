package com.example.android.navigation

import android.view.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.android.common.OnDestinationChanged
import com.example.android.presentation.detail.DetailScreen
import com.example.android.presentation.home.HomeScreen
import com.example.android.presentation.morePage.MorePageScreen
import com.example.android.presentation.search.SearchScreen
import com.example.android.presentation.signIn.SignInScreen
import com.example.android.presentation.signUp.SignUpScreen
import com.example.android.presentation.splash.SplashScreen
import com.example.common.core.enum.ContentType
import com.example.common.nav.ContentDetailsNavArgs
import com.example.common.nav.ContentMoreNavArgs
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun Navigation(window: Window) {

    val systemUiController = rememberSystemUiController()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        composable(Screen.Splash.route) {
            OnDestinationChanged(
                systemUiController = systemUiController,
                color = Color.Transparent,
                drawOverStatusBar = true,
                window = window
            )

            SplashScreen(navController = navController)
        }

        composable(Screen.SignIn.route) {
            OnDestinationChanged(
                systemUiController = systemUiController,
                color = Color.Transparent,
                drawOverStatusBar = false,
                window = window
            )

            SignInScreen(navController = navController)
        }

        composable(Screen.SignUp.route) {
            OnDestinationChanged(
                systemUiController = systemUiController,
                color = Color.Transparent,
                drawOverStatusBar = false,
                window = window
            )

            SignUpScreen(navController = navController)
        }
        composable(
            "${Screen.MorePage.route}/{type}/{order}/{status}/{genres}",
            arguments = moreScreenArgs
        ) { backStack ->
            OnDestinationChanged(
                systemUiController = systemUiController,
                color = MaterialTheme.colors.background,
                drawOverStatusBar = true,
                window = window
            )

            MorePageScreen(
                navController = navController,
                navArgs = ContentMoreNavArgs(
                    contentType = ContentType.valueOf(backStack.arguments?.getString("type")!!),
                    order = backStack.arguments?.getString("order"),
                    status = backStack.arguments?.getString("status"),
                    genres = backStack.arguments?.getString("genres")
                )
            )
        }
        composable(
            "${Screen.Details.route}/{type}/{id}",
            arguments = detailsScreenArgs
        ) { backStack ->
            OnDestinationChanged(
                systemUiController = systemUiController,
                color = Color.Transparent,
                drawOverStatusBar = true,
                window = window
            )
            DetailScreen(
                navController = navController,
                navArgs = ContentDetailsNavArgs(
                    id = backStack.arguments?.getString("id")!!,
                    contentType = ContentType.valueOf(backStack.arguments?.getString("type")!!),
                )
            )

        }

        composable(Screen.Home.route) {
            OnDestinationChanged(
                systemUiController = systemUiController,
                color = MaterialTheme.colors.background,
                drawOverStatusBar = false,
                window = window,
            )

            HomeScreen(
                navController = navController
            )
        }

        composable(Screen.Search.route) {
            OnDestinationChanged(
                systemUiController = systemUiController,
                color = MaterialTheme.colors.background,
                drawOverStatusBar = false,
                window = window,
            )

            SearchScreen(
                navController = navController
            )
        }

    }
}

private val detailsScreenArgs = listOf(
    navArgument(name = "type") {
        type = NavType.StringType
        nullable = false
    },
    navArgument(name = "id") {
        type = NavType.StringType
        nullable = false
    }
)

private val moreScreenArgs = listOf(
    navArgument(name = "type") {
        type = NavType.StringType
        nullable = false
    },
    navArgument(name = "order") {
        type = NavType.StringType
        nullable = true
    },
    navArgument(name = "status") {
        type = NavType.StringType
        nullable = true
    },
    navArgument(name = "genres") {
        type = NavType.StringType
        nullable = true
    }
)