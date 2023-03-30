package com.example.android.composable.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.android.composable.CenterCircularProgressIndicator
import com.example.android.ui.Shapes
import com.example.android.ui.redLikeOrange
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
      .height(thumbnailHeight + 40.dp)
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
      val request = ImageRequest.Builder(LocalContext.current)
        .data(data.image)
        .size(200,100)
//        .memoryCacheKey(data.image) // задаем ключ для кэширования в памяти
//        .diskCacheKey(data.image) // задаем ключ для кэширования на диске
//        .diskCachePolicy(CachePolicy.ENABLED) // разрешаем кэширование на диске
//        .memoryCachePolicy(CachePolicy.ENABLED) // разрешаем кэширование в памяти
//        .networkCachePolicy(CachePolicy.ENABLED) // разрешаем кэширование в сети
        .build()

//      LaunchedEffect(key1 = request) {
//        ImageLoaderSingleton.loadImage(request)
//      }

      SubcomposeAsyncImage(
        modifier = thumbnailModifier,
        model = request,
        contentDescription = "Content thumbnail",
        contentScale = ContentScale.Crop,
        loading = {
          CenterCircularProgressIndicator(
            strokeWidth = 2.dp,
            size = 20.dp,
            color = redLikeOrange
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
      color = MaterialTheme.colors.primary,
      textAlign = textAlign,
    )
  }
}