package com.example.android.presentation.detail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.android.composable.chip.GenreChip
import com.example.android.composable.content_horizontal.HorizontalContentHeaderConfig
import com.example.android.composable.content_horizontal.ScrollableHorizontalContent
import com.example.android.composable.content_horizontal.ScrollableHorizontalMedia
import com.example.android.composable.content_horizontal.ScrollableHorizontalScreenshots
import com.example.android.composable.item.ItemVerticalModifier
import com.example.android.composable.shimmer.rememberShimmerCustomBounds
import com.example.android.presentation.detail.composable.ContentDetailsThreeColumnSection
import com.example.android.presentation.detail.composable.DetailDescription
import com.example.common.core.enum.ContentType
import com.example.common.core.enum.TypesOfMoreScreen
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.common.ContentDetail
import com.example.common.models.common.ContentLight
import com.example.common.models.common.ContentMedia
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

private object ContentDetailsScreenSection {
    const val ContentDescription = "content_description"
    const val ContentScreenshots = "content_screenshots"
    const val ContentMedia = "content_media"
    const val ContentSimilar = "content_similar"
    const val ContentPhotos = "content_photos"
    const val ContentData = "content_data"
    const val ContentRelated = "content_related"
    const val ContentGenre = "content_genre_chips"
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailContentList(
    lazyColumnState: LazyListState = rememberLazyListState(),
    onContentClick: (String, String) -> Unit,
    detailsState: StateListWrapper<ContentDetail>,
    mediaState: StateListWrapper<ContentMedia>,
    screenshotsState: StateListWrapper<String>,
    similarState: StateListWrapper<ContentLight>,
    onHeaderClick: (String, String, String?, String?, List<String>?) -> Unit,
    relatedState: StateListWrapper<ContentLight>,
) {
    var isDescriptionExpanded by remember { mutableStateOf(false) }

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
    val coroutineScope = rememberCoroutineScope()
    val overlayListState = rememberLazyListState()
    val pagerState = rememberPagerState()

    val animateDuration = 250
    val animatedBgColor by animateColorAsState(
        targetValue = if (isGalleryPageOpen) Color.Black.copy(alpha = 0.7f) else Color.Transparent,
        animationSpec = tween(animateDuration)
    )
    val animatedYDp by animateDpAsState(
        targetValue = if (isGalleryPageOpen) statusBarHeight else yAxisOffset,
        animationSpec = tween(animateDuration)
    )

    val animatedContentWidth by animateDpAsState(
        targetValue = if (isGalleryPageOpen) screenWidth else 140.dp,
        animationSpec = tween(animateDuration)
    )

    val animatedContentHeight by animateDpAsState(
        targetValue = if (isGalleryPageOpen) contentHeight else 190.dp,
        animationSpec = tween(animateDuration)
    )

    val closeOverlayAction = {
        selectedImageUrl = if (pagerState.currentPage in 0 until screenshotsState.data.size) {
            screenshotsState.data[pagerState.currentPage]
        } else {
            ""
        }
        val index = pagerState.currentPage
        val isLastSelectedVisible = galleryListState.layoutInfo.visibleItemsInfo.find {
            it.index == index
        } == null

        coroutineScope.launch {
            if (isLastSelectedVisible) {
                galleryListState.scrollToItem(index)
            }
            val xOffset = galleryListState.layoutInfo.visibleItemsInfo.find {
                it.index == index
            }?.offset ?: 0
            xAxisOffset = with(density) {
                xOffset.toFloat().toDp() + extraContentPadding
            }
            xAxisStart = 0.dp
            xAxisTarget = xAxisOffset
            animatedXDp = 0.dp

            xAxisOffsetBlocker = xAxisOffset + 140.dp - 12.dp
            xAxisStartBlocker = 0.dp
            xAxisTargetBlocker = xAxisOffsetBlocker
            animatedXDpBlocker = 0.dp
            isGalleryPageOpen = false
        }
        Unit
    }

    LazyColumn (
        modifier = Modifier.fillMaxWidth(),
        state = lazyColumnState,
        contentPadding = PaddingValues(bottom = 4.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        item(key = ContentDetailsScreenSection.ContentGenre) {
            val genres = detailsState.data[0].genres
            if (genres.isNotEmpty()) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 0.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    itemsIndexed(
                        items = genres
                    ) { index, genre ->
                        GenreChip(text = genre.title)
                    }
                }
            }
        }
        item(key = ContentDetailsScreenSection.ContentData) {
            ContentDetailsThreeColumnSection(
                data = detailsState.data[0],
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp),
            )
        }
        item(key = ContentDetailsScreenSection.ContentDescription) {
            DetailDescription(
                detailState = detailsState,
                isExpanded = isDescriptionExpanded,
                onExpandedChanged = { isDescriptionExpanded = it }
            )
        }
        item(key = ContentDetailsScreenSection.ContentRelated) {
            ScrollableHorizontalContent(
                modifier = Modifier,
                shimmer = rememberShimmerCustomBounds(),
                headerTitle = "Связанные",
                contentState = relatedState,
                contentPadding = PaddingValues(horizontal = 0.dp),
                contentArrangement = ItemVerticalModifier.HorizontalArrangement.Default,
                headerModifier = HorizontalContentHeaderConfig.NulalbleStart,
                onIconClick = {
                    onHeaderClick(TypesOfMoreScreen.Minimize.name, ContentType.Anime.name, null, null, null)
                },
                onItemClick = onContentClick
            )
        }
        item(key = ContentDetailsScreenSection.ContentScreenshots) {
            ScrollableHorizontalScreenshots(
                modifier = Modifier,
                shimmer = rememberShimmerCustomBounds(),
                contentState = screenshotsState,
                contentPadding = PaddingValues(horizontal = 0.dp),
                contentArrangement = ItemVerticalModifier.HorizontalArrangement.Default,
                onItemClick = onContentClick,
                headerTitle = "Кадры"
            )
        }
        item(key = ContentDetailsScreenSection.ContentMedia) {
            ScrollableHorizontalMedia(
                modifier = Modifier,
                shimmer = rememberShimmerCustomBounds(),
                contentState = mediaState,
                contentPadding = PaddingValues(horizontal = 0.dp),
                contentArrangement = ItemVerticalModifier.HorizontalArrangement.Default,
                headerTitle = "Видео"
            )
        }
        item(key = ContentDetailsScreenSection.ContentSimilar) {
            ScrollableHorizontalContent(
                modifier = Modifier,
                shimmer = rememberShimmerCustomBounds(),
                headerTitle = "Похожие",
                contentState = similarState,
                contentPadding = PaddingValues(horizontal = 0.dp),
                contentArrangement = ItemVerticalModifier.HorizontalArrangement.Default,
                headerModifier = HorizontalContentHeaderConfig.NulalbleStart,
                onIconClick = {
                    onHeaderClick(TypesOfMoreScreen.Minimize.name, ContentType.Anime.name, null, null, null)
                },
                onItemClick = onContentClick
            )
        }
    }
}