package com.example.android.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.example.android.composable.item.ItemVertical
import com.example.android.composable.shimmer.onUpdateShimmerBounds
import com.example.common.models.mangaResponse.light.MangaLight
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SearchContentList(
    searchResults: LazyPagingItems<MangaLight>,
    onItemClick: (String, String) -> Unit,
    listState: LazyGridState
) {
    var shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)
    LazyVerticalGrid(
        modifier = Modifier
            .onUpdateShimmerBounds(shimmerInstance),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(4.dp),
        state = listState,
        userScrollEnabled = true,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(searchResults.itemCount) {index ->
            ItemVertical(
                data = searchResults[index]!!,
                onClick = onItemClick,
            )
        }
    }


}