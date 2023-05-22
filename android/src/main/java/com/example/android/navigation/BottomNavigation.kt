package com.example.android.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.android.presentation.anime.AnimeScreen
import com.example.android.presentation.home.HomeScreen
//import com.example.android.presentation.manga.MangaScreen
import com.example.android.presentation.myList.MyListScreen
import com.example.android.ui.*
import com.example.android.ui.blacker

enum class BottomNavTabs(val label: String, val icon: Int) {
    Anime("Аниме", MyIcons.Outlined.anime),
//    Manga("Манга", Icons.Outlined.manga),
    Home("Главная ", MyIcons.Outlined.home),
    Favourite("Избранное", MyIcons.Outlined.favourite)
}

@Composable
fun NavScreen(
    selectedTab: MutableState<BottomNavTabs>,
    navController: NavHostController
) {
    Scaffold(
        bottomBar = {
            if (!isSystemInDarkTheme()) {
                BottomNavigation(
                    modifier = Modifier.height(64.dp),
                    backgroundColor = smokyWhite,
                ) {
                    for (tab in BottomNavTabs.values()) {
                        BottomNavigationItem(
                            modifier = Modifier.fillMaxSize().weight(5f),
                            selected = selectedTab.value == tab,
                            onClick = {
                                if (selectedTab.value == tab) return@BottomNavigationItem
                                selectedTab.value = tab
                            },
                            label = {
                                Text(
                                    text = tab.label,
                                    fontSize = 10.sp,
                                    color = reder
                                )
                            },
                            alwaysShowLabel = false,
                            icon = {
                                Image(
                                    painter = painterResource(id = tab.icon),
                                    colorFilter = ColorFilter.tint(color = if(selectedTab.value == tab) reder else Color.Black),
                                    contentDescription = tab.label,
                                    modifier = Modifier.size(25.dp),
                                    contentScale = ContentScale.Crop
                                )
                            },
                        )
                    }
                }
            } else {
                BottomNavigation(
                    modifier = Modifier.height(64.dp),
                    backgroundColor = slate700,
                    elevation = 16.dp
                ) {
                    for (tab in BottomNavTabs.values()) {
                        BottomNavigationItem(
                            modifier = Modifier.fillMaxSize(),
                            selected = selectedTab.value == tab,
                            onClick = {
                                if (selectedTab.value == tab) return@BottomNavigationItem
                                selectedTab.value = tab
                            },
                            label = {
                                Text(
                                    text = tab.label,
                                    fontSize = 9.sp,
                                    color = Color.White,
                                )
                            },
                            alwaysShowLabel = false,
                            icon = {
                                Image(
                                    painterResource(id = tab.icon),
                                    colorFilter = ColorFilter.tint(Color.White),
                                    contentDescription = tab.label,
                                    modifier = Modifier.size(25.dp),
                                    contentScale = ContentScale.Crop
                                )

                            },
                        )
                    }
                }
            }
        }
    ) {
        val modifier = Modifier.padding(it)
        when (selectedTab.value) {
            BottomNavTabs.Anime -> AnimeScreen(
                navController,
                modifier = modifier
            )
//            BottomNavTabs.Manga -> MangaScreen(
//                navController,
//                modifier = modifier
//            )
            BottomNavTabs.Home -> HomeScreen(
                navController,
                modifier = modifier
            )
            BottomNavTabs.Favourite -> MyListScreen(
                navController,
                modifier = modifier
            )
        }
    }

}