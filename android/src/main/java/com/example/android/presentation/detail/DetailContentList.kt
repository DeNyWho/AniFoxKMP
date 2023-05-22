package com.example.android.presentation.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android.composable.content_horizontal.ScrollableHorizontalMedia
import com.example.android.composable.content_horizontal.ScrollableHorizontalScreenshots
import com.example.android.composable.item.ItemVerticalModifier
import com.example.android.composable.shimmer.rememberShimmerCustomBounds
import com.example.android.presentation.detail.composable.DetailDescription
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.common.ContentDetail
import com.example.common.models.common.ContentMedia

private object ContentDetailsScreenSection {
    const val ContentDescription = "content_description"
    const val ContentScreenshots = "content_screenshots"
    const val ContentMedia = "content_media"
    const val ContentGenre = "content_genre_chips"
}

@Composable
fun DetailContentList(
    lazyColumnState: LazyListState = rememberLazyListState(),
    onContentClick: (String, String) -> Unit,
    detailsState: StateListWrapper<ContentDetail>,
    mediaState: StateListWrapper<ContentMedia>,
    screenshotsState: StateListWrapper<String>
) {
    var isDescriptionExpanded by remember { mutableStateOf(false) }
    LazyColumn (
        modifier = Modifier.fillMaxWidth(),
        state = lazyColumnState,
        contentPadding = PaddingValues(bottom = 4.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        item(key = ContentDetailsScreenSection.ContentDescription) {
            DetailDescription(
                detailState = detailsState,
                isExpanded = isDescriptionExpanded,
                onExpandedChanged = { isDescriptionExpanded = it }
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
    }
}