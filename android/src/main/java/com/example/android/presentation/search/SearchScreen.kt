package com.example.android.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.android.navigation.Screen
import com.example.android.presentation.search.composable.SearchBoxField
import com.example.common.core.enum.ContentType
import kotlinx.coroutines.channels.Channel
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.koin.androidx.compose.getViewModel

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = getViewModel()
) {
    val selectedType = rememberSaveable { mutableStateOf(ContentType.Manga) }
    val searchQuery = rememberSaveable { mutableStateOf("") }

    val listState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { (FocusRequester()) }
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarChannel = remember { Channel<String?>(Channel.CONFLATED) }

    LaunchedEffect(key1 = viewModel.hashCode()) {
        focusRequester.requestFocus()
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
                    viewModel.search(it)
                }
            )
        },
        state = collapsingState,
        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed
    ) {
        SearchContentList(
            listState = listState,
            searchResults = viewModel.searchedManga.collectAsLazyPagingItems(),
            onItemClick = { type, id ->
                navController.navigate("${Screen.Details.route}/$type/$id")
            },
        )
    }


}