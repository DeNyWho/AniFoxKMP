package com.example.android.composable.content_horizontal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import coil.annotation.ExperimentalCoilApi
import com.example.android.composable.item.ItemDetailScreenshot
import com.example.android.composable.item.ItemVertical
import com.example.android.composable.item.ItemVerticalModifier
import com.example.android.composable.item.showItemVerticalAnimeMoreWhenPastLimit
import com.example.android.composable.shimmer.ContentListHeaderWithButtonShimmer
import com.example.android.composable.shimmer.onUpdateShimmerBounds
import com.example.android.composable.shimmer.rememberShimmerCustomBounds
import com.example.android.composable.shimmer.showItemVerticalAnimeShimmer
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.common.ContentLight
import com.valentinilk.shimmer.Shimmer

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ScrollableHorizontalScreenshots(
    modifier: Modifier,
    itemModifier: Modifier = Modifier.width(ItemVerticalModifier.ScreenShot),
    headerModifier: Modifier = HorizontalContentHeaderConfig.NulalbleStart,
    shimmer: Shimmer = rememberShimmerCustomBounds(),
    thumbnailHeight: Dp = ItemVerticalModifier.ThumbnailHeightScreenShot,
    headerTitle: String,
    contentState: StateListWrapper<String>,
    contentPadding: PaddingValues,
    contentArrangement: Arrangement.Horizontal,
    textAlign: TextAlign = TextAlign.Start,
    onItemClick: (String, String) -> Unit,
) {

    if (contentState.isLoading) {
        ContentListHeaderWithButtonShimmer(shimmerInstance = shimmer)
    } else if (contentState.data.isNotEmpty()) {
        HorizontalContentHeader(
            modifier = headerModifier,
            title = headerTitle,
            onButtonClick = null
        )
    }

    LazyRow(
        modifier = modifier.onUpdateShimmerBounds(shimmer),
        contentPadding = contentPadding,
        horizontalArrangement = contentArrangement
    ) {
        if (contentState.isLoading) {
            showItemVerticalAnimeShimmer(
                modifier = itemModifier,
                shimmerInstance = shimmer,
                thumbnailHeight = thumbnailHeight
            )
        } else if (contentState.data.isNotEmpty()) {
            items(
                items = contentState.data, key =  {it}
            ) {data ->
                ItemDetailScreenshot(
                    modifier = itemModifier,
                    data = data,
                    thumbnailHeight = thumbnailHeight
                )
            }
        }
    }
}