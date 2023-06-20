package com.example.android.navigation

import android.view.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.android.common.OnDestinationChanged
import com.example.android.presentation.detail.DetailScreen
import com.example.android.presentation.home.HomeScreen
//import com.example.android.presentation.manga.MangaScreen
import com.example.android.presentation.morePage.MorePageScreen
import com.example.android.presentation.player.PlayerScreen
import com.example.android.presentation.read.ReadChapterInfoScreen
import com.example.android.presentation.read.ReaderChaptersScreen
import com.example.android.presentation.search.SearchScreen
import com.example.android.presentation.signIn.SignInScreen
import com.example.android.presentation.signUp.SignUpScreen
import com.example.android.presentation.splash.SplashScreen
import com.example.common.core.enum.ContentType
import com.example.common.core.enum.TypesOfMoreScreen
import com.example.common.nav.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun Navigation(window: Window) {

    val systemUiController = rememberSystemUiController()
    val navController = rememberNavController()
    val selectedTab = remember { mutableStateOf(BottomNavTabs.Home) }

    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        composable(Screen.Splash.route) {
            OnDestinationChanged(
                systemUiController = systemUiController,
                navigationBarColor = MaterialTheme.colors.background,
                statusBarColor = MaterialTheme.colors.background,
                drawOverStatusBar = true,
                window = window
            )

            SplashScreen(navController = navController)
        }

        composable(Screen.SignIn.route) {
            OnDestinationChanged(
                systemUiController = systemUiController,
                navigationBarColor = MaterialTheme.colors.background,
                statusBarColor = MaterialTheme.colors.background,
                drawOverStatusBar = false,
                window = window
            )

            SignInScreen(navController = navController)
        }

        composable(Screen.SignUp.route) {
            OnDestinationChanged(
                systemUiController = systemUiController,
                navigationBarColor = MaterialTheme.colors.background,
                statusBarColor = MaterialTheme.colors.background,
                drawOverStatusBar = false,
                window = window
            )

            SignUpScreen(navController = navController)
        }

        composable(
            "${Screen.MorePage.route}/{typeOfScreen}/{type}/{order}/{status}/{genres}",
            arguments = moreScreenArgs
        ) { backStack ->
            OnDestinationChanged(
                systemUiController = systemUiController,
                navigationBarColor = MaterialTheme.colors.background,
                statusBarColor = MaterialTheme.colors.background,
                drawOverStatusBar = true,
                window = window
            )

            MorePageScreen(
                navController = navController,
                navArgs = ContentMoreNavArgs(
                    typeOfScreen = TypesOfMoreScreen.valueOf(backStack.arguments?.getString("typeOfScreen")!!),
                    contentType = ContentType.valueOf(backStack.arguments?.getString("type")!!),
                    order = backStack.arguments?.getString("order"),
                    status = backStack.arguments?.getString("status"),
                    genres = backStack.arguments?.getString("genres"),
                )
            )
        }
        composable(
            "${Screen.Details.route}/{type}/{id}",
            arguments = detailsScreenArgs
        ) { backStack ->
            OnDestinationChanged(
                systemUiController = systemUiController,
                navigationBarColor = Color.Transparent,
                statusBarColor = Color.Transparent,
                drawOverStatusBar = true,
                window = window,
                portrait = true
            )
            DetailScreen(
                navController = navController,
                navArgs = ContentDetailsNavArgs(
                    id = backStack.arguments?.getString("id")!!,
                    contentType = ContentType.valueOf(backStack.arguments?.getString("type")!!),
                )
            )
        }

        composable(
            route = "${Screen.Player.route}/{playerUrl}",
            arguments = playerScreenArgs
        ) { backStack ->
            OnDestinationChanged(
                systemUiController = systemUiController,
                navigationBarColor = Color.Transparent,
                statusBarColor = Color.Transparent,
                drawOverStatusBar = true,
                window = window,
                hideSystemBar = true,
                landscape = true
            )
            PlayerScreen(
                navController = navController,
                navArgs = ContentPlayerNavArgs(
                    playerUrl = backStack.arguments?.getString("playerUrl")!!
                )
            )
        }

        composable(
            route = "${Screen.ReaderChapters.route}/{mangaId}",
            arguments = chaptersScreenArgs
        ) { backStack ->
            OnDestinationChanged(
                systemUiController = systemUiController,
                navigationBarColor = Color.Transparent,
                statusBarColor = Color.Transparent,
                drawOverStatusBar = true,
                window = window,
                portrait = true
            )
            ReaderChaptersScreen(
                navController = navController,
                navArgs = ContentChaptersNavArgs(
                    mangaID = backStack.arguments?.getString("mangaId")!!
                )
            )
        }

        composable(
            route = "${Screen.ReadChapter.route}/{mangaId}/{chapterCode}/{chapterId}/{chapterTitle}",
            arguments = chaptersScreenArgs
        ) { backStack ->
            OnDestinationChanged(
                systemUiController = systemUiController,
                navigationBarColor = Color.Transparent,
                statusBarColor = Color.Transparent,
                drawOverStatusBar = true,
                window = window,
                portrait = true
            )
            ReadChapterInfoScreen(
                navController = navController,
                navArgs = ContentReaderNavArgs(
                    mangaID = backStack.arguments?.getString("mangaId")!!,
                    chapterId = backStack.arguments?.getString("chapterId")!!,
                    chapterCode = backStack.arguments?.getString("chapterCode")!!,
                    chapterTitle = backStack.arguments?.getString("chapterTitle")!!
                )
            )
        }

        composable(Screen.Home.route) {
            OnDestinationChanged(
                systemUiController = systemUiController,
                navigationBarColor = MaterialTheme.colors.onSurface,
                statusBarColor = MaterialTheme.colors.background,
                drawOverStatusBar = false,
                window = window,
            )

            HomeScreen(
                navController = navController
            )
            NavScreen(selectedTab = selectedTab, navController = navController)

        }

//        composable(Screen.Manga.route) {
//            OnDestinationChanged(
//                systemUiController = systemUiController,
//                color = MaterialTheme.colors.background,
//                drawOverStatusBar = false,
//                window = window,
//            )
//
//            MangaScreen(
//                navController = navController
//            )
//
//        }

        composable(Screen.Search.route) {
            OnDestinationChanged(
                systemUiController = systemUiController,
                navigationBarColor = MaterialTheme.colors.background,
                statusBarColor = MaterialTheme.colors.background,
                drawOverStatusBar = false,
                window = window,
            )

            SearchScreen(
                navController = navController
            )
        }

    }
}

private val playerScreenArgs = listOf(
    navArgument(name = "playerUrl") {
        type = NavType.StringType
        nullable = false
    }
)

private val chaptersScreenArgs = listOf(
    navArgument(name = "mangaId") {
        type = NavType.StringType
        nullable = false
    }
)

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
    navArgument(name = "typeOfScreen") {
        type = NavType.StringType
        nullable = false
    },
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