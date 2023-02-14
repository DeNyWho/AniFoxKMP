package com.example.android.composable.shimmer

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.android.presentation.home.item.ItemVerticalModifier
import com.example.android.ui.grey
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun ItemVerticalMangaShimmer(
  modifier: Modifier,
  shimmerInstance: Shimmer,
  thumbnailHeight: Dp = ItemVerticalModifier.ThumbnailHeightDefault,
) {
  Column(
    modifier = modifier
      .shimmer(shimmerInstance)
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(thumbnailHeight)
        .clip(RoundedCornerShape(12.dp))
        .background(color = grey)
    )

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(18.dp)
        .padding(0.dp, 6.dp, 0.dp, 0.dp)
        .background(color = grey)
    )

    Box(
      modifier = Modifier
        .width(62.dp)
        .height(18.dp)
        .padding(0.dp, 6.dp, 0.dp, 0.dp)
        .background(color = grey)
    )

    Box(
      modifier = Modifier
        .width(32.dp)
        .height(18.dp)
        .padding(0.dp, 6.dp, 0.dp, 0.dp)
        .background(color = grey)
    )

  }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyGridScope.showItemVerticalAnimeShimmer(
  modifier: Modifier = ItemVerticalModifier.fillParentWidth,
  shimmerInstance: Shimmer,
  count: Int = 9,
  thumbnailHeight: Dp = ItemVerticalModifier.ThumbnailHeightDefault
) {
  items(count) {
    ItemVerticalMangaShimmer(
      modifier = modifier,
      shimmerInstance = shimmerInstance,
      thumbnailHeight = thumbnailHeight
    )
  }
}

fun LazyListScope.showItemVerticalAnimeShimmer(
  modifier: Modifier = ItemVerticalModifier.Default,
  shimmerInstance: Shimmer,
  count: Int = 5,
  thumbnailHeight: Dp = ItemVerticalModifier.ThumbnailHeightDefault
) {
  items(count) {
    ItemVerticalMangaShimmer(
      modifier = modifier,
      shimmerInstance = shimmerInstance,
      thumbnailHeight = thumbnailHeight
    )
  }
}