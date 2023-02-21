package com.example.android.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.android.composable.CenterCircularProgressIndicator
import com.example.android.presentation.detail.composable.DetailScreenToolbar
import com.example.android.ui.red
import com.example.common.nav.ContentDetailsNavArgs
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.koin.androidx.compose.getViewModel

private object ContentDetailsScreenSection {
    const val ContentDescription = "content_description_composable"
    const val ContentGenre = "content_genre_chips"
    const val ContentLinked = "content_linked"
    const val ContentSimilar = "content_similar"
}

@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel = getViewModel(),
    navArgs: ContentDetailsNavArgs
) {
    LaunchedEffect(viewModel){
        viewModel.getDetailManga(navArgs.contentType.name, navArgs.id)
    }
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
    val detailsState by viewModel.detailManga

    if(detailsState.isLoading){
        CenterCircularProgressIndicator(
            size = 40.dp,
            color = red
        )
    } else {
        CollapsingToolbarScaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            state = toolbarScaffoldState,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            toolbar = {
                DetailScreenToolbar(
                    detailState = detailsState,
                    toolbarScaffoldState = toolbarScaffoldState
                ) { navController.popBackStack() }
            }
        ) {

        }
    }

}

