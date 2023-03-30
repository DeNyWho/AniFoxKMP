package com.example.android.composable.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object ItemVerticalModifier {
  val Default = 120.dp
  val Small = 110.dp


  val fillParentWidth = Modifier
    .fillMaxWidth()

  val ThumbnailHeightDefault = 190.dp
  val ThumbnailHeightGrid = 170.dp
  val ThumbnailHeightSmall = 140.dp

  object HorizontalArrangement {
    val Default = Arrangement.spacedBy(12.dp)
  }
}