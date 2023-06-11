package com.example.android.presentation.myList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.android.composable.item.ItemVertical
import com.example.android.composable.item.ItemVerticalModifier
import com.example.android.composable.shimmer.onUpdateShimmerBounds
import com.example.android.composable.shimmer.rememberShimmerCustomBounds
import com.example.android.composable.shimmer.showItemVerticalAnimeShimmer
import com.example.common.core.enum.ContentType
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.common.ContentLight
import com.google.accompanist.pager.*
import com.valentinilk.shimmer.Shimmer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)
@Composable
fun MyListContentList(
    contentListState: List<LazyGridState>,
    contentState: List<StateListWrapper<ContentLight>>,
    modifier: Modifier = Modifier,
    itemModifier: Modifier = ItemVerticalModifier.fillParentWidth,
    shimmerInstance: Shimmer = rememberShimmerCustomBounds(),
    contentPadding: PaddingValues = PaddingValues(start = 12.dp, end = 12.dp, top = 0.dp, bottom = 12.dp),
    thumbnailHeight: Dp = ItemVerticalModifier.ThumbnailHeightGrid,
    gridCells: GridCells = GridCells.Fixed(3),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
    textAlign: TextAlign = TextAlign.Start,
    onItemClick: (String, String) -> Unit,
) {
    val tabTitles = listOf("Смотрю", "Запланировано", "Просмотрено", "Отложено")

    val pagerState = rememberPagerState(0)
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(top = 4.dp)
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        HorizontalTabs(titles = tabTitles, pagerState = pagerState, scope = coroutineScope)
        HorizontalPager(
            count = tabTitles.size,
            state = pagerState,
            modifier = Modifier.weight(1f).padding(top = 8.dp)
        ) { currentPage ->
            LazyVerticalGrid(
                modifier = modifier
                    .fillMaxSize()
                    .onUpdateShimmerBounds(shimmerInstance),
                columns = gridCells,
                state = contentListState[currentPage],
                contentPadding = contentPadding,
                verticalArrangement = verticalArrangement,
                horizontalArrangement = horizontalArrangement
            ) {
                if (contentState[currentPage].data != null) {
                    items(contentState[currentPage].data.size) { index ->
                        ItemVertical(
                            modifier = itemModifier,
                            data = contentState[currentPage].data[index],
                            thumbnailHeight = thumbnailHeight,
                            textAlign = textAlign,
                            onClick = onItemClick,
                            contentType = ContentType.Manga.name,
                        )
                    }

                    if (contentState[currentPage].isLoading) {
                        showItemVerticalAnimeShimmer(
                            modifier = itemModifier,
                            shimmerInstance = shimmerInstance,
                            thumbnailHeight = thumbnailHeight
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalTabs(
    titles: List<String>,
    pagerState: PagerState,
    scope: CoroutineScope
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .height(4.dp)
                    .pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        titles.forEachIndexed { index, title ->
            Tab(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .height(50.dp)
                    .clip(CircleShape),
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch(Dispatchers.Main) {
                        pagerState.animateScrollToPage(page = index)
                    }
                }
            ) {
                Text(text = title, style = MaterialTheme.typography.h5, color = MaterialTheme.colors.primary)
            }
        }
    }
}
