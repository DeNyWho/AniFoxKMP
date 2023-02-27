package com.example.android.presentation.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android.composable.content_horizontal.ScrollableHorizontalContent
import com.example.android.composable.shimmer.rememberShimmerCustomBounds
import com.example.android.navigation.Screen
import com.example.android.presentation.detail.composable.DetailDescription
import com.example.android.presentation.home.item.ItemVerticalModifier
import com.example.common.models.mangaResponse.detail.MangaDetail
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.presentation.data.StateListWrapper

private object ContentDetailsScreenSection {
    const val ContentDescription = "content_description"
    const val ContentGenre = "content_genre_chips"
    const val ContentLinked = "content_linked"
    const val ContentSimilar = "content_similar"
}

@Composable
fun DetailContentList(
    lazyColumnState: LazyListState = rememberLazyListState(),
    onContentClick: (String, String) -> Unit,
    detailsState: StateListWrapper<MangaDetail>,
    similarState: StateListWrapper<MangaLight>,
    linkedState: StateListWrapper<MangaLight>
) {
    var isDescriptionExpanded by remember { mutableStateOf(false) }
    LazyColumn (
        state = lazyColumnState,
        contentPadding = PaddingValues(bottom = 32.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        item(key = ContentDetailsScreenSection.ContentDescription) {
            DetailDescription(
                detailState = detailsState,
                isExpanded = isDescriptionExpanded,
                onExpandedChanged = { isDescriptionExpanded = it }
            )
        }
        item(key = ContentDetailsScreenSection.ContentLinked) {
            ScrollableHorizontalContent(
                modifier = Modifier,
                shimmer = rememberShimmerCustomBounds(),
                headerTitle = "Связанные",
                contentState = linkedState,
                contentPadding = PaddingValues(horizontal = 0.dp),
                contentArrangement = ItemVerticalModifier.HorizontalArrangement.Default,
                onIconClick = {
                },
                onItemClick = onContentClick
            )
        }
        println("ZXCF = $similarState")
        item(key = ContentDetailsScreenSection.ContentSimilar) {
            ScrollableHorizontalContent(
                modifier = Modifier,
                shimmer = rememberShimmerCustomBounds(),
                headerTitle = "Похожие",
                contentState = similarState,
                contentPadding = PaddingValues(horizontal = 0.dp),
                contentArrangement = ItemVerticalModifier.HorizontalArrangement.Default,
                onIconClick = {
                },
                onItemClick = onContentClick
            )
        }
    }

}