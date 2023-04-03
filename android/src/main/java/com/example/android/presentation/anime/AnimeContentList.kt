package com.example.android.presentation.anime

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android.composable.content_horizontal.HorizontalContentHeaderConfig
import com.example.android.composable.content_horizontal.ScrollableHorizontalContent
import com.example.android.composable.item.ItemVerticalModifier
import com.example.android.composable.shimmer.rememberShimmerCustomBounds
import com.example.common.core.enum.ContentType
import com.example.common.core.enum.TypesOfMoreScreen
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.animeResponse.light.AnimeLight
import com.example.common.models.common.ContentLight
import com.example.common.models.mangaResponse.light.MangaLight

@Composable
fun AnimeContentList(
    lazyColumnState: LazyListState = rememberLazyListState(),
    onContentClick: (String, String) -> Unit,
    onGoingAnimeState: StateListWrapper<ContentLight>,
    onHeaderClick: (String, String, String?, String?, List<String>?) -> Unit,
) {
    LazyColumn(
        state = lazyColumnState
    ) {
        item(key = "ongoing_manga") {
            ScrollableHorizontalContent(
                modifier = Modifier,
                shimmer = rememberShimmerCustomBounds(),
                headerTitle = "Выходит сейчас",
                contentState = onGoingAnimeState,
                contentPadding = PaddingValues(horizontal = 12.dp),
                contentArrangement = ItemVerticalModifier.HorizontalArrangement.Default,
                headerModifier = HorizontalContentHeaderConfig.Home,
                onIconClick = {
                    onHeaderClick(TypesOfMoreScreen.Minimize.name, ContentType.Manga.name, null, null, null)
                },
                onItemClick = onContentClick
            )
        }
    }
}

