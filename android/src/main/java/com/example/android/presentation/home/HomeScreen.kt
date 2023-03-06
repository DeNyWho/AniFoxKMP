package com.example.android.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.android.navigation.Screen
import com.example.android.presentation.search.composable.SearchBoxField
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = getViewModel(),
    lazyColumnState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier,
){
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarChannel = remember { Channel<String?>(Channel.CONFLATED) }

    LaunchedEffect(viewModel){
        viewModel.getOngoingManga()
        viewModel.getFinishManga()
        viewModel.getRomanceManga()
        viewModel.getRandomManga()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)
    ) {
        Column(Modifier.fillMaxWidth()) {
            SearchBoxField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(
                        onClick = { navController.navigate(Screen.Search.route)}
                    ),
                isEnabled = false
            )
            HomeContentList(
                navController = navController,
                lazyColumnState = lazyColumnState,
                onGoingMangaState = viewModel.ongoingManga.value,
                onFinalMangaState = viewModel.finishManga.value,
                randomMangaState = viewModel.randomManga.value,
                romanceMangaState = viewModel.romanceManga.value,
                onContentClick = { type, id ->
                    navController.navigate("${Screen.Details.route}/$type/$id")
                },
                onIconClick = { navController.navigate(Screen.MorePage.route) }
            )
        }
    }

    LaunchedEffect(snackbarChannel) {
        snackbarChannel.receiveAsFlow().collect { error ->

            val result = if (error != null) {
                snackbarHostState.showSnackbar(
                    message = error,
                    actionLabel = "Dismiss",
                    duration = SnackbarDuration.Long
                )
            } else {
                null
            }

            when (result) {
                SnackbarResult.ActionPerformed -> {
                    /* action has been performed */
                }
                SnackbarResult.Dismissed -> {
                    /* dismissed, no action needed */
                }

                else -> {}
            }
        }
    }

}