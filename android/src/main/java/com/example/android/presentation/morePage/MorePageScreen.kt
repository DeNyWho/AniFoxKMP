package com.example.android.presentation.morePage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.android.navigation.Screen
import com.example.android.presentation.morePage.item.MorePageToolbar
import com.example.android.presentation.search.SearchContentList
import com.example.common.core.enum.ContentType
import com.example.common.nav.ContentMoreNavArgs
import kotlinx.coroutines.channels.Channel
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.koin.androidx.compose.getViewModel

@Composable
fun MorePageScreen(
    navController: NavController,
    viewModel: MorePageViewModel = getViewModel(),
    navArgs: ContentMoreNavArgs,
) {
    val selectedType = rememberSaveable { mutableStateOf(ContentType.Manga) }
    val searchQuery = rememberSaveable { mutableStateOf("") }

    val listState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarChannel = remember { Channel<String?>(Channel.CONFLATED) }

    LaunchedEffect(viewModel) {
        viewModel.search(
            order = navArgs.order,
            genres = if (navArgs.genres != null && navArgs.genres != "null") navArgs.genres!!.split(",") else null,
            status = navArgs.status
        )
    }

    val collapsingState = rememberCollapsingToolbarScaffoldState()

//    CollapsingToolbarScaffold(
//        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background) ,
//        toolbar = {
//            MorePageToolbar { navController.navigateUp() }
//        },
//        state = collapsingState,
//        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed
//    ) {
//        SearchContentList(
//            listState = listState,
//            searchResults = viewModel.searchedManga.collectAsLazyPagingItems(),
//            onItemClick = { type, id ->
//                navController.navigate("${Screen.Details.route}/$type/$id")
//            },
//        )
//    }

}