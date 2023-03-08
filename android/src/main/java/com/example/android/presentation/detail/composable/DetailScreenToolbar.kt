package com.example.android.presentation.detail.composable

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.android.composable.CenterCircularProgressIndicator
import com.example.android.ui.*
import com.example.common.models.mangaResponse.detail.MangaDetail
import com.example.common.domain.common.StateListWrapper
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.CollapsingToolbarScope
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import kotlin.math.roundToInt

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
            .height(280.dp)
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
                    .width(130.dp)
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
                    ),
                    color = MaterialTheme.colors.primary
                )
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        imageVector = headerCaptionIcon,
                        modifier = Modifier.size(20.dp),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(grey),
                    )
                    Text(
                        text = headerCaptionStatus,
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }

    val density = LocalDensity.current
    val initialOffset = with(density){
        40.dp.toPx().roundToInt()
    }
    val targetOffset = with(density){
        -40.dp.toPx().roundToInt()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onArrowClick()}){
            Icon(
                imageVector = Default.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }

        AnimatedVisibility(
            visible = isTitleVisible,
            enter = slideInVertically(
                initialOffsetY = { initialOffset },
                animationSpec = tween(
                    durationMillis = 1200,
                    delayMillis = 50,
                    easing = LinearOutSlowInEasing
                )
            ) + fadeIn(initialAlpha = 0F),
            exit = slideOutVertically(
                targetOffsetY = { targetOffset },
                animationSpec = tween(
                    durationMillis = 1200,
                    delayMillis = 50,
                    easing = LinearOutSlowInEasing
                )
            ) + fadeOut()
        ) {
            Text(
                text = data.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 12.dp)
            )
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