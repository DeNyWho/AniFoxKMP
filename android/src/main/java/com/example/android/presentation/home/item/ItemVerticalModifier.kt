package com.example.android.presentation.home.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object ItemVerticalModifier {
  val Default = Modifier
    .width(140.dp)
  val Small = Modifier
    .width(100.dp)


  val fillParentWidth = Modifier
    .fillMaxWidth()

  val ThumbnailHeightDefault = 190.dp
  val ThumbnailHeightGrid = 160.dp
  val ThumbnailHeightSmall = 140.dp

  object HorizontalArrangement {
    val Default = Arrangement.spacedBy(12.dp)
  }
}