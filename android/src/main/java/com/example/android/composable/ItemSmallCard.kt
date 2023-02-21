package com.example.android.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.common.models.mangaResponse.light.MangaLight

@Composable
fun ItemSmallCard(
    modifier: Modifier = Modifier,
    manga: MangaLight,
//    onItemClick: () -> Unit
) {
    val titleLines = remember { mutableStateOf(0)}
    println(manga.image)
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(manga.image.replace("localhost", "192.168.0.44"))
            .crossfade(true)
            .build()
    )

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable {
//                onItemClick()
            }
    ) {
        Box(
            modifier = Modifier
                .height(210.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (painter.state is AsyncImagePainter.State.Loading) {
                CenterCircularProgressIndicator(
                    strokeWidth = 2.dp,
                    size = 15.dp,
                    color = Color.Red
                )
            }
            Image(
                painter = painter,
                contentDescription = "Thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .align(Alignment.Center),
            )

        }

        Text(
            text = manga.title,
            modifier = Modifier.padding(0.dp, 6.dp, 0.dp, 0.dp).fillMaxWidth(),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                color = MaterialTheme.colors.primary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            ),
            textAlign = TextAlign.Center,
            onTextLayout = { res -> titleLines.value = res.lineCount },
        )

        for (index in titleLines.value..2) {
            Text(text = " ", style = TextStyle(fontSize = 14.sp))
        }
    }
}