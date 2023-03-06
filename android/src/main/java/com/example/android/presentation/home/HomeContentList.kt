package com.example.android.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.android.composable.content_horizontal.HorizontalContentHeaderConfig
import com.example.android.composable.content_horizontal.ScrollableHorizontalContent
import com.example.android.composable.shimmer.rememberShimmerCustomBounds
import com.example.android.composable.item.ItemVerticalModifier
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.presentation.data.StateListWrapper

@Composable
fun HomeContentList(
    navController: NavController,
    randomMangaState: StateListWrapper<MangaLight>,
    lazyColumnState: LazyListState = rememberLazyListState(),
    romanceMangaState: StateListWrapper<MangaLight>,
    onContentClick: (String, String) -> Unit,
    onIconClick: () -> Unit,
    onGoingMangaState: StateListWrapper<MangaLight>,
    onFinalMangaState: StateListWrapper<MangaLight>
) {
    LazyColumn (
        state = lazyColumnState
    ) {
        item(key = "ongoing_manga") {
            ScrollableHorizontalContent(
                modifier = Modifier,
                shimmer = rememberShimmerCustomBounds(),
                headerTitle = "Выходит сейчас",
                contentState = onGoingMangaState,
                contentPadding = PaddingValues(horizontal = 12.dp),
                contentArrangement = ItemVerticalModifier.HorizontalArrangement.Default,
                headerModifier = HorizontalContentHeaderConfig.Home,
                onIconClick = onIconClick,
                onItemClick = onContentClick
            )
        }
        item(key = "finish_manga") {
            ScrollableHorizontalContent(
                modifier = Modifier,
                shimmer = rememberShimmerCustomBounds(),
                headerTitle = "Завершено",
                contentState = onFinalMangaState,
                contentPadding = PaddingValues(horizontal = 12.dp),
                contentArrangement = ItemVerticalModifier.HorizontalArrangement.Default,
                headerModifier = HorizontalContentHeaderConfig.Home,
                onIconClick = onIconClick,
                onItemClick = onContentClick
            )
        }
        item(key = "romance_manga") {
            ScrollableHorizontalContent(
                modifier = Modifier,
                shimmer = rememberShimmerCustomBounds(),
                headerTitle = "Романтика",
                contentState = romanceMangaState,
                contentPadding = PaddingValues(horizontal = 12.dp),
                contentArrangement = ItemVerticalModifier.HorizontalArrangement.Default,
                headerModifier = HorizontalContentHeaderConfig.Home,
                onIconClick = onIconClick,
                onItemClick = onContentClick
            )
        }
        item(key = "random_manga") {
            ScrollableHorizontalContent(
                modifier = Modifier,
                shimmer = rememberShimmerCustomBounds(),
                headerTitle = "Рандом",
                contentState = randomMangaState,
                contentPadding = PaddingValues(horizontal = 12.dp),
                contentArrangement = ItemVerticalModifier.HorizontalArrangement.Default,
                headerModifier = HorizontalContentHeaderConfig.Home,
                onIconClick = onIconClick,
                onItemClick = onContentClick
            )
        }
    }

}








