package com.example.android.presentation.myList

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.android.R
import com.example.android.composable.item.ItemVertical
import com.example.android.composable.item.ItemVerticalModifier
import com.example.android.composable.shimmer.onUpdateShimmerBounds
import com.example.android.composable.shimmer.rememberShimmerCustomBounds
import com.example.android.composable.shimmer.showItemVerticalAnimeShimmer
import com.example.android.ui.orange400
import com.example.common.core.enum.ContentType
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.common.ContentLight
import com.google.accompanist.pager.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.valentinilk.shimmer.Shimmer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
    contentType: String = ContentType.Anime.name,
    onDropDownClick: (String) -> Unit,
    onRefreshInPlan: () -> Unit,
    onRefreshWatch: () -> Unit,
    onRefreshWatched: () -> Unit,
    onRefreshPostponed: () -> Unit
) {
    val tabTitles = listOf("Смотрю", "Запланировано", "Просмотрено", "Отложено")
    val pagerState = rememberPagerState(0)
    val coroutineScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(1000L)
            isRefreshing = false
        }
    }
    Column(
        modifier = Modifier
            .padding(top = 4.dp)
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        HorizontalTabs(
            titles = tabTitles,
            pagerState = pagerState,
            scope = coroutineScope,
            onDropDownClick = onDropDownClick,
            contentType = contentType
        )
        HorizontalPager(
            count = tabTitles.size,
            state = pagerState,
            modifier = Modifier.weight(1f).padding(top = 8.dp)
        ) { currentPage ->
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    when (currentPage) {
                        0 -> {
                            onRefreshWatch.invoke()
                        }

                        1 -> {
                            onRefreshInPlan.invoke()
                        }

                        2 -> {
                            onRefreshWatched.invoke()
                        }

                        3 -> {
                            onRefreshPostponed.invoke()
                        }
                    }
                },
            ) {
                LazyVerticalGrid(
                    modifier = modifier
                        .fillMaxSize()
                        .onUpdateShimmerBounds(shimmerInstance),
                    columns = if (contentState[currentPage].data.isEmpty() && !contentState[currentPage].isLoading) GridCells.Fixed(
                        1
                    ) else gridCells,
                    state = contentListState[currentPage],
                    contentPadding = contentPadding,
                    verticalArrangement = verticalArrangement,
                    horizontalArrangement = horizontalArrangement
                ) {
                    if (contentState[currentPage].data.isNotEmpty()) {
                        items(contentState[currentPage].data.size) { index ->
                            ItemVertical(
                                modifier = itemModifier,
                                data = contentState[currentPage].data[index],
                                thumbnailHeight = thumbnailHeight,
                                textAlign = textAlign,
                                onClick = onItemClick,
                                contentType = contentType
                            )
                        }
                    }
                    if (contentState[currentPage].data.isEmpty() && !contentState[currentPage].isLoading) {
                        items(1) {
                            PlaceholderContent(tabTitles[currentPage])
                        }
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

@Composable
fun PlaceholderContent(title: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.anifoxicon),
            contentDescription = "require auth icon"
        )
        Text(
            text = "Здесь пока-что ничего нет...",
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Добавляй запланированные аниме в категорию в «$title»",
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center
        )
    }
}


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalTabs(
    titles: List<String>,
    pagerState: PagerState,
    scope: CoroutineScope,
    onDropDownClick: (String) -> Unit,
    contentType: String
) {
    var expandedDropDownMenu by remember { mutableStateOf(false)}
    var titleChoice: String by remember { mutableStateOf(contentType)}
    val titleListChoice = listOf(ContentType.Anime.name, ContentType.Manga.name)

    Box(
        modifier = Modifier
            .padding(top = 8.dp, end = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .align(Alignment.CenterEnd)
                .clickable {
                    expandedDropDownMenu = !expandedDropDownMenu
                }
        ) {
            DropdownMenu(
                expanded = expandedDropDownMenu,
                onDismissRequest = {
                    expandedDropDownMenu = false
                }) {
                titleListChoice.forEach {
                    val isSelected = titleChoice == it
                    Text(
                        text = it,
                        style = MaterialTheme.typography.h2,
                        color = if(isSelected) orange400 else MaterialTheme.colors.primary,
                        modifier = Modifier.padding(8.dp)
                            .clickable {
                                titleChoice = it
                                expandedDropDownMenu = !expandedDropDownMenu
                                onDropDownClick.invoke(it)
                            }
                    )

                }
            }
            Text(text = titleChoice, style = MaterialTheme.typography.h2)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Choice type content"
            )
        }
    }

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
