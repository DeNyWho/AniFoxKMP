package com.example.android.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.example.android.composable.item.ItemVertical
import com.example.android.composable.item.ItemVerticalModifier
import com.example.android.composable.shimmer.onUpdateShimmerBounds
import com.example.android.util.calculateGridCount
import com.example.common.models.common.ContentLight
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SearchContentList(
    searchResults: LazyPagingItems<ContentLight>,
    onItemClick: (String, String) -> Unit,
    listState: LazyGridState
) {
    var shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)
    val loadState = searchResults.loadState
    val isListEmpty = loadState.prepend is LoadState.NotLoading && searchResults.itemCount == 0

    LazyVerticalGrid(
        modifier = Modifier
            .onUpdateShimmerBounds(shimmerInstance).padding(bottom = 40.dp),
        columns = GridCells.Fixed(calculateGridCount(columnWidth = ItemVerticalModifier.Default)),
        contentPadding = PaddingValues(4.dp),
        userScrollEnabled = true,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if(!isListEmpty) {
            items(searchResults.itemCount) { index ->
                ItemVertical(
                    data = searchResults[index]!!,
                    onClick = onItemClick,
                )
            }
        }
    }


}