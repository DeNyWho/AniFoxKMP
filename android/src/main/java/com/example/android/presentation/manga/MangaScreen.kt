package com.example.android.presentation.manga

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.android.common.isScrolledToTheEnd
import com.example.android.navigation.Screen
import com.example.android.presentation.search.composable.SearchBoxField
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.koin.androidx.compose.getViewModel

@Composable
fun MangaScreen(
    navController: NavController,
    viewModel: MangaViewModel = getViewModel(),
) {
    val listState = rememberLazyGridState()

    val searchQuery = rememberSaveable { mutableStateOf("") }

    val focusRequester = remember { (FocusRequester()) }

    val contentState by viewModel.contentState

    LaunchedEffect(viewModel) {
        viewModel.getNextContentPart()
        snapshotFlow {
            listState.isScrolledToTheEnd() && listState.layoutInfo.totalItemsCount != listState.layoutInfo.visibleItemsInfo.size && listState.isScrollInProgress && searchQuery.value.isNotEmpty()
        }.distinctUntilChanged().onEach {
            if (it) {
                viewModel.getNextContentPart()
                viewModel.getNextContentPart()
            }
        }.launchIn(this)
    }

    val collapsingState = rememberCollapsingToolbarScaffoldState()

    CollapsingToolbarScaffold(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background) ,
        toolbar = {
            SearchBoxField(
                modifier = Modifier.padding(12.dp),
                searchQuery = searchQuery.value,
                focusRequest = focusRequester,
                onSearchQueryCleared = { searchQuery.value = "" },
                onSearchQueryChanged = {
                    searchQuery.value = it
                    viewModel.getNewSearch(searchQuery.value)
                    viewModel.getNextContentPart()
                }
            )
        },
        state = collapsingState,
        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed
    ) {
        MangaContentList(
            lazyGridState = listState,
            contentState = contentState,
            onItemClick = { type, id ->
                navController.navigate("${Screen.Details.route}/$type/$id")
            },
        )
    }

}
