package com.example.android.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.android.composable.CenterCircularProgressIndicator
import com.example.android.navigation.Screen
import com.example.android.presentation.detail.composable.DetailScreenToolbar
import com.example.android.ui.red
import com.example.common.nav.ContentDetailsNavArgs
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.koin.androidx.compose.getViewModel

@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel = getViewModel(),
    navArgs: ContentDetailsNavArgs
) {
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
    val detailsState = viewModel.detailManga.value
    val linkedState = viewModel.linkedManga.value
    val similarState = viewModel.similarManga.value

    LaunchedEffect(viewModel){
        viewModel.getDetailManga(navArgs.contentType.name, navArgs.id)
        viewModel.getLinkedManga(navArgs.id)
        viewModel.getSimilarManga(navArgs.id)
    }

    if(detailsState.data.isEmpty()){
        CenterCircularProgressIndicator(
            size = 40.dp,
            color = red,
            modifier = Modifier.background(MaterialTheme.colors.background)
        )
    } else {
        CollapsingToolbarScaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            state = rememberCollapsingToolbarScaffoldState(),
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            toolbar = {
                DetailScreenToolbar(
                    detailState = detailsState,
                    toolbarScaffoldState = toolbarScaffoldState
                ) { navController.popBackStack() }
            }
        ) {
            DetailContentList(
                detailsState = detailsState,
                linkedState = linkedState,
                similarState = similarState,
                onContentClick = { type, id ->
                    navController.navigate("${Screen.Details.route}/$type/$id")
                },
            )
        }
    }

}

