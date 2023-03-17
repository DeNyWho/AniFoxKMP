package com.example.android.composable.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.android.ui.Icons
import com.example.android.ui.Shapes
import com.example.android.ui.darkGreyBackground
import com.example.android.ui.onDarkSurface

@Composable
fun ItemVerticalMore(
  modifier: Modifier = Modifier,
  thumbnailHeight: Dp = ItemVerticalModifier.ThumbnailHeightDefault,
  onClick: () -> Unit
) {
  Column(
    modifier = modifier
      .clip(Shapes.Rounded12)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .height(thumbnailHeight)
        .clip(Shapes.Rounded12)
        .background(MaterialTheme.colors.secondaryVariant)
        .clickable { onClick() },
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
    ) {
      Icon(
        imageVector = ImageVector.vectorResource(id = Icons.Filled.ChevronDown),
        contentDescription = "",
        tint = MaterialTheme.colors.onSurface,
        modifier = Modifier
          .size(32.dp)
          .padding(bottom = 6.dp)
          .rotate(-90f)
      )
      Text(
        text = "Показать ещё",
        color = MaterialTheme.colors.primary
      )
    }
  }
}

fun LazyListScope.showItemVerticalAnimeMoreWhenPastLimit(
  modifier: Modifier = Modifier.width(ItemVerticalModifier.Default),
  thumbnailHeight: Dp = ItemVerticalModifier.ThumbnailHeightDefault,
  limit: Int = 11,
  size: Int = 0,
  onClick: () -> Unit
) {
  if (size > limit) {
    item {
      ItemVerticalMore(
        modifier = modifier,
        thumbnailHeight = thumbnailHeight,
        onClick = onClick
      )
    }
  }
}