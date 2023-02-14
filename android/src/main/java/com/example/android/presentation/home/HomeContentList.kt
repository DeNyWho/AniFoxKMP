package com.example.android.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.android.composable.ItemSmallCard
import com.example.android.composable.content_horizontal.ScrollableHorizontalContent
import com.example.android.composable.shimmer.rememberShimmerCustomBounds
import com.example.android.navigation.navigateToContentDetailsScreen
import com.example.android.presentation.home.item.ItemVerticalModifier
import com.example.android.presentation.home.view_holder.ItemShimmer
import com.example.android.presentation.home.view_holder.ItemShimmerHeader
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.presentation.data.StateListWrapper
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.unclippedBoundsInWindow

@Composable
fun HomeContentList(
    navController: NavController,
    randomMangaState: StateListWrapper<MangaLight>,
    lazyColumnState: LazyListState = rememberLazyListState(),
    romanceMangaState: StateListWrapper<MangaLight>,
//    onContentClick: (String, Int) -> Unit
) {
    LazyColumn (
        state = lazyColumnState
    ) {
        item(key = "romance_manga") {
            ScrollableHorizontalContent(
                modifier = Modifier,
                shimmer = rememberShimmerCustomBounds(),
                headerTitle = "Романтика",
                contentState = romanceMangaState,
                contentPadding = PaddingValues(horizontal = 12.dp),
                contentArrangement = ItemVerticalModifier.HorizontalArrangement.Default,
                onIconClick = {
                },
//                onItemClick = navController::navigateToContentDetailsScreen
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
                onIconClick = {
                },
//                onItemClick = navController::navigateToContentDetailsScreen
            )
        }
    }

}



private fun LazyListScope.showShimmerPlaceHolder(shimmerInstance: Shimmer, count: Int){
    items(count){
        ItemShimmer(shimmerInstance)
    }
}
private fun LazyListScope.showShimmerHeaderPlaceHolder(shimmerInstance: Shimmer){
        item {
            ItemShimmerHeader(shimmerInstance)
        }
}









