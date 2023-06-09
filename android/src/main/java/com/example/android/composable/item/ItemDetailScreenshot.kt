package com.example.android.composable.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.android.composable.CenterCircularProgressIndicator
import com.example.android.ui.Shapes
import com.example.android.ui.redLikeOrange
import com.example.android.ui.teal200

@ExperimentalCoilApi
@Composable
fun ItemDetailScreenshot(
    modifier: Modifier = Modifier,
    data: String,
    thumbnailHeight: Dp = ItemVerticalModifier.ThumbnailHeightScreenShot,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .height(thumbnailHeight)
            .clip(Shapes.Rounded12)
            .clickable {
                onClick.invoke()
            }
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
                .data(data)
                .build()
            Card(
                elevation = 2.dp,
                shape = Shapes.Rounded12
            ) {
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
        }
    }
}