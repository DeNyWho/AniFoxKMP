package com.example.android.presentation.detail.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.android.composable.CenterCircularProgressIndicator
import com.example.android.ui.*
import com.example.common.models.mangaResponse.detail.MangaDetail
import com.example.common.presentation.data.StateListWrapper
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.CollapsingToolbarScope
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun CollapsingToolbarScope.DetailScreenToolbar(
    detailState: StateListWrapper<MangaDetail> = StateListWrapper(listOf(MangaDetail())),
    toolbarScaffoldState: CollapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState(),
    onArrowClick: () -> Boolean,
){
    val blockerColorGradients = listOf(
        MaterialTheme.colors.background.copy(alpha = 0.8F),
        MaterialTheme.colors.background.copy(alpha = 0.9F),
        MaterialTheme.colors.background,
    )

    val isTitleVisible = toolbarScaffoldState.toolbarState.progress <= 0.25
    val data = detailState.data[0]

    val (
        headerCaptionIcon: ImageVector,
        headerCaptionStatus: String
    ) = resolveHeaderIconAndStatus(data)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .parallax(0.5f)
            .graphicsLayer {
                alpha = toolbarScaffoldState.toolbarState.progress
            }
    ) {
        Box {
            AsyncImage(
                model = data.image,
                contentDescription = "Background of header",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(colors = blockerColorGradients)
                )
        )
        Row (
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(top = 52.dp, start = 16.dp, end = 16.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight()
                    .clip(Shapes.Rounded12),
                model = data.image,
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

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = data.title,
                    style = TextStyle(
                        color = white,
                        fontWeight = FontWeight.Bold,
                        fontSize = Size.Text20
                    )
                )
            }
        }
    }



}

@Composable
private fun resolveHeaderIconAndStatus(
    data: MangaDetail
): Pair<ImageVector, String> {
    return if (
        data.types.type == "онгоинг"
    ) {
        Pair(
            ImageVector.vectorResource(id =  Icons.Outlined.Clock4),
            "Онгоинг"
        )
    } else {
        Pair(
            ImageVector.vectorResource(id = Icons.Outlined.DoubleCheck),
            "Завершён"
        )
    }

}