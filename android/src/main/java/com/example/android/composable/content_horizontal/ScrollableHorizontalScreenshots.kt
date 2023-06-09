package com.example.android.composable.content_horizontal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.android.composable.item.ItemDetailScreenshot
import com.example.android.composable.item.ItemVerticalModifier
import com.example.android.composable.shimmer.ContentListHeaderWithButtonShimmer
import com.example.android.composable.shimmer.onUpdateShimmerBounds
import com.example.android.composable.shimmer.rememberShimmerCustomBounds
import com.example.android.composable.shimmer.showItemVerticalAnimeShimmer
import com.example.common.domain.common.StateListWrapper
import com.google.accompanist.insets.LocalWindowInsets
import com.origeek.imageViewer.previewer.rememberPreviewerState
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
    val scope = rememberCoroutineScope()
    val imageViewerState = rememberPreviewerState()

    if (contentState.isLoading) {
        ContentListHeaderWithButtonShimmer(shimmerInstance = shimmer)
    } else if (contentState.data.isNotEmpty()) {
        HorizontalContentHeader(
            modifier = headerModifier,
            title = headerTitle,
            onButtonClick = null
        )
    }
    val density = LocalDensity.current
    val statusBarHeight = with (density) { LocalWindowInsets.current.statusBars.top.toDp() }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val contentHeight = with(density) {
        (190 * screenWidth.roundToPx() / 140).toDp()
    }
    var selectedImageIndex by remember { mutableStateOf(0) }
    var selectedImageUrl by remember { mutableStateOf("") }
    val galleryListState = rememberLazyListState()
    val extraContentPadding = 24.dp - 140.dp
    var yAxisOffset by remember { mutableStateOf(0.dp) }
    var xAxisOffset by remember { mutableStateOf(0.dp) }
    var xAxisStart by remember { mutableStateOf(0.dp) }
    var xAxisTarget by remember { mutableStateOf(0.dp) }
    var xAxisOffsetBlocker by remember { mutableStateOf(0.dp) }
    var xAxisStartBlocker by remember { mutableStateOf(0.dp) }
    var xAxisTargetBlocker by remember { mutableStateOf(0.dp) }
    var animatedXDp by remember { mutableStateOf(0.dp) }
    var animatedXDpBlocker by remember { mutableStateOf(0.dp) }
    var isGalleryPageOpen by remember { mutableStateOf(false) }
    var isOverlayVisible by remember { mutableStateOf(false) }

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
            itemsIndexed(
                items = contentState.data
            ) { index, data ->
                ItemDetailScreenshot(
                    modifier = itemModifier,
                    data = data,
                    thumbnailHeight = thumbnailHeight
                ) {
                    selectedImageIndex = index
                    selectedImageUrl = data

                    val xOffset = galleryListState.layoutInfo.visibleItemsInfo.find {
                        it.index == index
                    }?.offset ?: 0
                    xAxisOffset = with(density) {
                        (xOffset.toFloat()).toDp() + extraContentPadding
                    }
                    xAxisStart = xAxisOffset
                    xAxisTarget = 0.dp
                    animatedXDp = xAxisOffset

                    xAxisOffsetBlocker = xAxisOffset + 140.dp - 12.dp
                    xAxisStartBlocker = xAxisOffsetBlocker
                    xAxisTargetBlocker = 0.dp
                    animatedXDpBlocker = xAxisOffsetBlocker

                    isOverlayVisible = true
                    isGalleryPageOpen = true
                }
            }
        }
    }
}