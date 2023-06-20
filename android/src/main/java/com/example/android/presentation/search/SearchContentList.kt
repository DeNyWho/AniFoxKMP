package com.example.android.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.android.composable.item.ItemVertical
import com.example.android.composable.item.ItemVerticalModifier
import com.example.android.composable.shimmer.onUpdateShimmerBounds
import com.example.android.composable.shimmer.rememberShimmerCustomBounds
import com.example.android.composable.shimmer.showItemVerticalAnimeShimmer
import com.example.common.core.enum.ContentType
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.common.ContentLight
import com.valentinilk.shimmer.Shimmer

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SearchContentList(
    modifier: Modifier = Modifier,
    itemModifier: Modifier = ItemVerticalModifier.fillParentWidth,
    shimmerInstance: Shimmer = rememberShimmerCustomBounds(),
    contentState: StateListWrapper<ContentLight>,
    contentPadding: PaddingValues = PaddingValues(start = 12.dp, end = 12.dp, top = 0.dp, bottom = 12.dp),
    thumbnailHeight: Dp = ItemVerticalModifier.ThumbnailHeightGrid,
    gridCells: GridCells = GridCells.Fixed(3),
    lazyGridState: LazyGridState = rememberLazyGridState(),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
    textAlign: TextAlign = TextAlign.Start,
    onItemClick: (String, String) -> Unit,
) {

    LazyVerticalGrid(
        modifier = Modifier
            .onUpdateShimmerBounds(shimmerInstance),
        columns = gridCells,
        state = lazyGridState,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement,
        horizontalArrangement = horizontalArrangement
    ) {
        if(contentState.data != null) {
            items(contentState.data.size) { index ->
                ItemVertical(
                    modifier = itemModifier,
                    data = contentState.data[index],
                    thumbnailHeight = thumbnailHeight,
                    textAlign = textAlign,
                    onClick = onItemClick,
                    contentType = ContentType.Anime.name,
                )
            }

            if (contentState.isLoading) {
                showItemVerticalAnimeShimmer(
                    modifier = itemModifier,
                    shimmerInstance = shimmerInstance,
                    thumbnailHeight = thumbnailHeight
                )
            }
        }
    }


}