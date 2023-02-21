package com.example.android.presentation.home.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.SubcomposeAsyncImage
import com.example.android.composable.CenterCircularProgressIndicator
import com.example.android.ui.Shapes
import com.example.android.ui.red
import com.example.android.ui.teal200
import com.example.common.core.enum.ContentType
import com.example.common.models.mangaResponse.light.MangaLight

@ExperimentalCoilApi
@Composable
fun ItemVertical(
  modifier: Modifier = Modifier,
  data: MangaLight,
  thumbnailHeight: Dp = ItemVerticalModifier.ThumbnailHeightDefault,
  textAlign: TextAlign = TextAlign.Start,
  onClick: (String, String) -> Unit,
//  onClick: (String, ContentType) -> Unit
) {
  Column(
    modifier = modifier
      .clip(Shapes.Rounded12)
      .clickable { onClick(ContentType.Manga.name, data.id) }
  ) {
    val thumbnailModifier = Modifier
      .fillMaxWidth()
      .height(thumbnailHeight)
      .clip(Shapes.Rounded12)
    if (LocalInspectionMode.current) {
      Box(modifier = thumbnailModifier
        .background(teal200)
      )
    } else {
      SubcomposeAsyncImage(
        modifier = thumbnailModifier,
        model = data.image.replace("localhost", "192.168.0.44"),
        contentDescription = "Content thumbnail",
        contentScale = ContentScale.Crop,
        loading = {
          CenterCircularProgressIndicator(
            strokeWidth = 2.dp,
            size = 20.dp,
            color = red
          )
        }
      )
    }

    Text(
      text = data.title,
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 6.dp, bottom = 4.dp),
      maxLines = 2,
      overflow = TextOverflow.Ellipsis,
//      style = MyType.Body2.Normal.OnDarkSurfaceLight,
      textAlign = textAlign,
//      onTextLayout = {
//        titleLineCount = it.lineCount
//      }
    )

//    repeat(2 - titleLineCount) {
//      Text(
//        text = "",
//        style = MyType.Body2.Bold.OnDarkSurface
//      )
//    }
//
//    Text(
//      text = "${data.score}",
//      modifier = Modifier.padding(bottom = 3.dp),
//      style = TextStyle(
//        color = MyColor.OnDarkSurface,
//        fontSize = 13.sp,
//        fontWeight = FontWeight.Normal
//      )
//    )
  }
}