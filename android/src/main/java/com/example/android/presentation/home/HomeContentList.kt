package com.example.android.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.navigation.NavHostController
import com.example.android.composable.ItemSmallCard
import com.example.android.presentation.home.view_holder.ItemShimmer
import com.example.common.compose.RandomMangaState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.unclippedBoundsInWindow

@Composable
fun HomeContentList(
    navController: NavController,
    randomMangaState: RandomMangaState = RandomMangaState(),
    lazyColumnState: LazyListState = rememberLazyListState(),
//    onContentClick: (String, Int) -> Unit
) {
    LazyColumn (state = lazyColumnState) {
        item(key = "RandomManga") {
            val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)
            Row (
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Random Manga",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold
                )
            }

            LazyRow (
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                    val position = layoutCoordinates.unclippedBoundsInWindow()
                    shimmerInstance.updateBounds(position)
                }
            ) {
                println(randomMangaState.data)
                if(randomMangaState.isLoading){
                    showShimmerPlaceHolder(shimmerInstance, 12)
                } else {
                    items(randomMangaState.data, key = { item -> item.id }) { manga ->
                        ItemSmallCard(
                            modifier = Modifier
                                .width(160.dp)
                                .padding(12.dp, 0.dp),
                            manga = manga,
//            ) { onTopAnimeClick(ContentType.Anime.name, anime.malId) }
//                            onItemClick = { onContentClick }
                        )
                    }
                }
            }
        }
    }

}



private fun LazyListScope.showShimmerPlaceHolder(shimmerInstance: Shimmer, count: Int){
    items(count){
        ItemShimmer(shimmerInstance)
    }
}









