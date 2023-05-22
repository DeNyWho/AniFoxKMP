package com.example.android.navigation

sealed class Screen(val route: String) {
    object Splash: Screen("splash_screen" )
    object SignIn: Screen("signIn_screen")
    object SignUp: Screen("signUp_screen")
    object Home: Screen("home_screen")
    object Player: Screen("player_screen")
    object Anime: Screen("anime_screen")
    object Manga: Screen("manga_screen")
    object MorePage: Screen("more_page_screen")
    object Details: Screen("details_screen")
    object Search: Screen("search_screen")
    object Favourite: Screen("favourite_screen")
}
