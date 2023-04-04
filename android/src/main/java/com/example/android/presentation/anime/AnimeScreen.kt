package com.example.android.presentation.anime

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
import androidx.navigation.NavHostController
import com.example.android.navigation.Screen
import com.example.android.presentation.search.composable.SearchBoxField
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate

@Composable
fun AnimeScreen(
    navController: NavHostController,
    viewModel: AnimeViewModel = getViewModel(),
    lazyColumnState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarChannel = remember { Channel<String?>(Channel.CONFLATED) }

    var currentYear = 0
    var winterYear = 0
    var springYear = 0
    var summerYear = 0
    var fallYear = 0
    val currentSeason = when (LocalDate.now().month.value) {
        12, 1, 2 -> {
            winterYear = LocalDate.now().year
            springYear = LocalDate.now().year - 1
            summerYear = LocalDate.now().year - 1
            fallYear = LocalDate.now().year - 1
            "Winter"
        }
        3, 4, 5 -> {
            winterYear = LocalDate.now().year
            springYear = LocalDate.now().year
            summerYear = LocalDate.now().year - 1
            fallYear = LocalDate.now().year - 1
            "Spring"
        }
        6, 7, 8 -> {
            winterYear = LocalDate.now().year
            springYear = LocalDate.now().year
            summerYear = LocalDate.now().year
            fallYear = LocalDate.now().year - 1
            "Summer"
        }
        else -> {
            winterYear = LocalDate.now().year
            springYear = LocalDate.now().year
            summerYear = LocalDate.now().year
            fallYear = LocalDate.now().year
            "Fall"
        }
    }

    LaunchedEffect(viewModel){
        viewModel.getAnimeSequentially(winterYear, springYear, summerYear, fallYear)
    }

    Scaffold(
        modifier = modifier
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
            AnimeContentList(
                lazyColumnState = lazyColumnState,
                onContentClick = { type, id ->
                    navController.navigate("${Screen.Details.route}/$type/$id")
                },
                onWinterAnimeState = viewModel.onWinterAnime.value,
                onSpringAnimeState = viewModel.onSpringAnime.value,
                onSummerAnimeState = viewModel.onSummerAnime.value,
                onFallAnimeState = viewModel.onFallAnime.value,
                onGoingAnimeState = viewModel.onGoingAnime.value,
                onHeaderClick = { typeOfScreen: String, type: String, order: String?, status: String?, genres: List<String>? ->
                    navController.navigate(
                        "${Screen.MorePage.route}/$typeOfScreen/$type/$order/$status/${
                            genres?.joinToString(
                                ","
                            )
                        }"
                    )
                },
                currentSeason = currentSeason
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