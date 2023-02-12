package com.example.android.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = getViewModel(),
    scrollState: LazyListState = rememberLazyListState(),
    onContentClick: (String, Int) -> Unit = { type, id -> },
    modifier: Modifier = Modifier,
){
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(viewModel){
        viewModel.getRandomManga()
    }

    val randomManga = viewModel.randomManga.value
    println(randomManga)


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)
    ) {
        Column(Modifier.fillMaxWidth()) {
            HomeContentList(
                navController = navController,
                randomMangaState = randomManga
            )
        }
    }

}