package com.example.android.presentation.detail.composable

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.android.composable.CenterCircularProgressIndicator
import com.example.android.composable.common.SubComposeAsyncImageWithSSL
import com.example.android.ui.*
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.common.ContentDetail
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.CollapsingToolbarScope
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import kotlin.math.roundToInt

@Composable
fun CollapsingToolbarScope.ContentDetailsScreenToolbar(
    contentDetailsState: StateListWrapper<ContentDetail> = StateListWrapper(),
    toolbarScaffoldState: CollapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState(),
    onArrowClick: () -> Boolean = { false }
) {
    val blockerColorGradients = listOf(
        darkGreyBackground.copy(alpha = 0.8F),
        darkGreyBackground.copy(alpha = 0.9F),
        darkGreyBackground
    )

    val data = contentDetailsState.data[0]
    val isTitleVisible = toolbarScaffoldState.toolbarState.progress <= 0.25
    val imageUrl = data.image

    val (
        headerCaptionIcon: ImageVector,
        headerCaptionDescription: String
    ) = resolveHeaderIconAndDescription(data)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .parallax(0.5f)
            .graphicsLayer {
                alpha = toolbarScaffoldState.toolbarState.progress
            },
    ) {
        Box {
            val request = ImageRequest.Builder(LocalContext.current)
                .data(data.image.replace("http", "https").replace("httpss", "https"))
                .scale(Scale.FILL)
                .build()

            SubComposeAsyncImageWithSSL(
                image = imageUrl,
                modifier = Modifier
                    .fillMaxSize(),
                request = request
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(colors = blockerColorGradients)
                    )
            )

            // Header Content
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(top = 52.dp, start = 16.dp, end = 16.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left cover image
                SubComposeAsyncImageWithSSL(
                    image = imageUrl,
                    modifier = Modifier
                        .width(120.dp)
                        .height(240.dp)
                        .clip(Shapes.Rounded12),
                    request = request
                )

                // Header right content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = data.title ?: "-",
                        style = TextStyle(
                            color = OnDarkSurfaceLight,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )

                    // Ongoing / Airing status
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Icon(
                            imageVector = headerCaptionIcon,
                            contentDescription = headerCaptionDescription,
                            tint = onDarkSurface,
                            modifier = Modifier
                                .height(14.dp)
                                .padding(end = 6.dp))

                        Text(
                            text = data.status ?: "-",
                            style = TextStyle(
                                color = onDarkSurface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        )
                    }
                }
            }
        }
    }


    // Toolbar
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onArrowClick() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back", tint = OnDarkSurfaceLight
            )
        }

        val density = LocalDensity.current
        val initialOffset = with(density) {
            40.dp.toPx().roundToInt()
        }
        val targetOffset = with(density) {
            -40.dp.toPx().roundToInt()
        }

        AnimatedVisibility(
            visible = isTitleVisible,
            enter = slideInVertically(
                initialOffsetY = { initialOffset },
                animationSpec = tween(
                    durationMillis = 800,
                    delayMillis = 50,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(initialAlpha = 0f),
            exit = slideOutVertically(
                targetOffsetY = { targetOffset },
                animationSpec = tween(
                    durationMillis = 800,
                    delayMillis = 50,
                    easing = LinearOutSlowInEasing
                )
            ) + fadeOut()
        ) {
            Text(
                text = data.title ?: "-",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = TextStyle(
                    color = OnDarkSurfaceLight,
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
private fun resolveHeaderIconAndDescription(
    data: ContentDetail
): Pair<ImageVector, String> {
    return if (
        data.type == "ongoing"
    ) {
        Pair(
            ImageVector.vectorResource(id =  MyIcons.Outlined.Clock4),
            "Онгоинг"
        )
    } else {
        Pair(
            ImageVector.vectorResource(id = MyIcons.Outlined.DoubleCheck),
            "Завершён"
        )
    }
}