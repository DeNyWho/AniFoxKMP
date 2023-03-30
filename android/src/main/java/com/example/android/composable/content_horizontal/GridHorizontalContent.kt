package com.example.android.composable.content_horizontal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.android.composable.item.ItemVertical
import com.example.android.composable.item.ItemVerticalModifier
import com.example.android.composable.shimmer.ContentListHeaderWithButtonShimmer
import com.example.android.composable.shimmer.onUpdateShimmerBounds
import com.example.android.composable.shimmer.rememberShimmerCustomBounds
import com.example.android.composable.shimmer.showItemVerticalAnimeShimmer
import com.example.android.util.calculateGridCount
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.mangaResponse.light.MangaLight
import com.valentinilk.shimmer.Shimmer

@OptIn(ExperimentalCoilApi::class)
@Composable
fun GridHorizontalContent(
    modifier: Modifier,
    headerModifier: Modifier = HorizontalContentHeaderConfig.Default,
    itemModifier: Modifier = Modifier.width(ItemVerticalModifier.Default),
    shimmer: Shimmer = rememberShimmerCustomBounds(),
    thumbnailHeight: Dp = ItemVerticalModifier.ThumbnailHeightGrid,
    headerTitle: String,
    contentState: StateListWrapper<MangaLight>,
    contentPadding: PaddingValues,
    textAlign: TextAlign = TextAlign.Start,
    onIconClick: () -> Unit,
    onItemClick: (String, String) -> Unit,
    limit: Int = 12
) {
    if (contentState.isLoading) {
        ContentListHeaderWithButtonShimmer(shimmerInstance = shimmer)
    } else if (contentState.data.isNotEmpty()) {
        HorizontalRandomHeader(
            modifier = headerModifier,
            title = headerTitle,
            onButtonClick = if(contentState.data.size > limit) onIconClick else null
        )
    }

    LazyVerticalGrid(
        modifier = modifier.onUpdateShimmerBounds(shimmer).fillMaxSize().heightIn(max = ItemVerticalModifier.ThumbnailHeightGrid * limit),
        columns = GridCells.Fixed(calculateGridCount(columnWidth = ItemVerticalModifier.Default)),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        userScrollEnabled = false,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (contentState.isLoading) {
            showItemVerticalAnimeShimmer(
                modifier = itemModifier,
                shimmerInstance = shimmer,
                thumbnailHeight = thumbnailHeight
            )
        } else if (contentState.data.isNotEmpty()) {
            items(contentState.data.take(limit).size){ index ->
                ItemVertical(
                    modifier = itemModifier,
                    data = contentState.data[index],
                    thumbnailHeight = thumbnailHeight,
                    textAlign = textAlign,
                    onClick = onItemClick
                )
            }
        }
    }
}