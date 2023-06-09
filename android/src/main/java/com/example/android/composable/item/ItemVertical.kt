package com.example.android.composable.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
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
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.android.composable.CenterCircularProgressIndicator
import com.example.android.composable.common.SubComposeAsyncImageWithSSL
import com.example.android.ui.Shapes
import com.example.android.ui.redLikeOrange
import com.example.android.ui.teal200
import com.example.common.core.enum.ContentType
import com.example.common.di.getSSLContext
import com.example.common.models.common.ContentLight
import okhttp3.OkHttpClient
import java.security.KeyStore
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

@ExperimentalCoilApi
@Composable
fun ItemVertical(
  modifier: Modifier = Modifier,
  data: ContentLight,
  thumbnailHeight: Dp = ItemVerticalModifier.ThumbnailHeightDefault,
  textAlign: TextAlign = TextAlign.Start,
  onClick: (String, String) -> Unit,
  contentType: String
) {

  val context = LocalContext.current

  Column(
    modifier = modifier
      .height(thumbnailHeight + 40.dp)
      .clip(Shapes.Rounded12)
      .clickable { onClick(contentType, data.url) }
  ) {
    val thumbnailModifier = Modifier
      .fillMaxWidth()
      .height(thumbnailHeight)
      .clip(Shapes.Rounded12)
    if (LocalInspectionMode.current) {
      Card(
        elevation = 2.dp,
        shape = Shapes.Rounded12,
      ) {
        Box(modifier = thumbnailModifier
          .background(teal200)
        )
      }
    } else {
      val request = ImageRequest.Builder(LocalContext.current)
        .data(data.image.replace("http", "https").replace("httpss", "https"))
        .size(200, 100)
        .scale(Scale.FILL)
        .build()

      Card(
        elevation = 2.dp,
        shape = Shapes.Rounded12
      ) {
        SubComposeAsyncImageWithSSL(
          modifier = thumbnailModifier,
          image = data.image,
          request = request
        )
      }
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
