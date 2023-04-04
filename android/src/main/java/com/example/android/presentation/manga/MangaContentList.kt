package com.example.android.presentation.manga

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android.composable.content_horizontal.GridHorizontalContent
import com.example.android.composable.content_horizontal.HorizontalContentHeaderConfig
import com.example.android.composable.content_horizontal.ScrollableHorizontalContent
import com.example.android.composable.item.ItemVerticalModifier
import com.example.android.composable.shimmer.rememberShimmerCustomBounds
import com.example.common.core.enum.ContentType
import com.example.common.core.enum.TypesOfMoreScreen
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.common.ContentLight
import com.example.common.util.Constants

@Composable
fun MangaContentList(
    randomMangaState: StateListWrapper<ContentLight>,
    lazyColumnState: LazyListState = rememberLazyListState(),
    romanceMangaState: StateListWrapper<ContentLight>,
    onContentClick: (String, String) -> Unit,
    onGoingMangaState: StateListWrapper<ContentLight>,
    onFinalMangaState: StateListWrapper<ContentLight>,
    onHeaderClick: (String, String, String?, String?, List<String>?) -> Unit,
    onRandomClick: () -> Unit,
) {
    LazyColumn(
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
                onIconClick = {
                    onHeaderClick(TypesOfMoreScreen.Minimize.name, ContentType.Manga.name, null, null, null)
                },
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
                onIconClick = {
                    onHeaderClick(TypesOfMoreScreen.Minimize.name, ContentType.Manga.name, null, "завершён", null)
                },
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
                onIconClick = {
                    onHeaderClick(
                        TypesOfMoreScreen.Default.name, ContentType.Manga.name, null, null, listOf(
                            Constants.romance,
                            Constants.dramma,
                            Constants.sedze
                        )
                    )
                },
                onItemClick = onContentClick
            )
        }
        item(key = "random_manga") {
            GridHorizontalContent(
                modifier = Modifier,
                shimmer = rememberShimmerCustomBounds(),
                headerTitle = "Рандом",
                contentState = randomMangaState,
                contentPadding = PaddingValues(12.dp),
                headerModifier = HorizontalContentHeaderConfig.Home,
                onIconClick = {
                    onRandomClick()
                },
                onItemClick = onContentClick
            )
        }
    }
}








