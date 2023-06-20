package com.example.android.presentation.anime

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android.composable.content_horizontal.CarouselDefaultItem
import com.example.android.composable.content_horizontal.HorizontalContentHeaderConfig
import com.example.android.composable.content_horizontal.ScrollableHorizontalContent
import com.example.android.composable.item.ItemVerticalModifier
import com.example.android.composable.shimmer.rememberShimmerCustomBounds
import com.example.common.core.enum.ContentType
import com.example.common.core.enum.TypesOfMoreScreen
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.common.ContentLight

@Composable
fun AnimeContentList(
    lazyColumnState: LazyListState = rememberLazyListState(),
    onContentClick: (String, String) -> Unit,
    onGoingAnimeState: StateListWrapper<ContentLight>,
    onWinterAnimeState: StateListWrapper<ContentLight>,
    onSpringAnimeState: StateListWrapper<ContentLight>,
    onSummerAnimeState: StateListWrapper<ContentLight>,
    onFallAnimeState: StateListWrapper<ContentLight>,
    onHeaderClick: (String, String, String?, String?, List<String>?) -> Unit,
    currentSeason: String,
) {
    val onWinterElement: @Composable () -> Unit = {
        CarouselDefaultItem(
            onContentClick = onContentClick,
            state = onWinterAnimeState,
            onHeaderClick = onHeaderClick,
            headerTitle = "Зима",
            onIconClick = {
                onHeaderClick(TypesOfMoreScreen.Minimize.name, ContentType.Anime.name, null, null, null)
            }
        )
    }
    val onSpringElement: @Composable () -> Unit = {
        CarouselDefaultItem(
            onContentClick = onContentClick,
            state = onSpringAnimeState,
            onHeaderClick = onHeaderClick,
            headerTitle = "Весна",
            onIconClick = {
                onHeaderClick(TypesOfMoreScreen.Minimize.name, ContentType.Anime.name, null, null, null)
            }
        )
    }
    val onSummerElement: @Composable () -> Unit = {
        CarouselDefaultItem(
            onContentClick = onContentClick,
            state = onSummerAnimeState,
            onHeaderClick = onHeaderClick,
            headerTitle = "Лето",
            onIconClick = {
                onHeaderClick(TypesOfMoreScreen.Minimize.name, ContentType.Anime.name, null, null, null)
            }
        )
    }
    val onFallElement: @Composable () -> Unit = {
        CarouselDefaultItem(
            onContentClick = onContentClick,
            state = onFallAnimeState,
            onHeaderClick = onHeaderClick,
            headerTitle = "Осень",
            onIconClick = {
                onHeaderClick(TypesOfMoreScreen.Minimize.name, ContentType.Anime.name, null, null, null)
            }
        )
    }
    val list: Map<String, @Composable () -> Unit> = when(currentSeason) {
        "Winter" -> {
            mapOf(
                "anime_winter" to onWinterElement,
                "anime_spring" to onSpringElement,
                "anime_summer" to onSummerElement,
                "anime_fall" to onFallElement
            )
        }
        "Spring" -> {
            mapOf(
                "anime_spring" to onSpringElement,
                "anime_summer" to onSummerElement,
                "anime_fall" to onFallElement,
                "anime_winter" to onWinterElement
            )
        }
        "Summer" -> {
            mapOf(
                "anime_summer" to onSummerElement,
                "anime_fall" to onFallElement,
                "anime_winter" to onWinterElement,
                "anime_spring" to onSpringElement
            )
        }
        else -> {
            mapOf(
                "anime_fall" to onFallElement,
                "anime_winter" to onWinterElement,
                "anime_spring" to onSpringElement,
                "anime_summer" to onSummerElement
            )
        }
    }
    LazyColumn(
        state = lazyColumnState
    ) {
        item(key = "ongoing_anime") {
            ScrollableHorizontalContent(
                modifier = Modifier,
                shimmer = rememberShimmerCustomBounds(),
                headerTitle = "Выходит сейчас",
                contentState = onGoingAnimeState,
                contentPadding = PaddingValues(horizontal = 12.dp),
                contentArrangement = ItemVerticalModifier.HorizontalArrangement.Default,
                headerModifier = HorizontalContentHeaderConfig.Home,
                onIconClick = {
                    onHeaderClick(TypesOfMoreScreen.Minimize.name, ContentType.Anime.name, null, null, null)
                },
                onItemClick = onContentClick
            )
        }
        list.forEach { (t, u) ->
            item(key = t) {
                u.invoke()
            }
        }
    }
}