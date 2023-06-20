package com.example.android.presentation.detail.composable

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.request.ImageRequest
import coil.size.Scale
import com.example.android.composable.common.SubComposeAsyncImageWithSSL
import com.example.android.ui.*
import com.example.android.ui.Shapes
import com.example.common.core.enum.StatusListType
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
    onArrowClick: () -> Boolean = { false },
    onStatusClick: (StatusListType, String, String) -> Unit,
    contentType: String
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
            .height(320.dp)
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
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(top = 52.dp, start = 16.dp, end = 16.dp, bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left cover image
                SubComposeAsyncImageWithSSL(
                    image = imageUrl,
                    modifier = Modifier
                        .width(130.dp)
                        .padding(bottom = 20.dp)
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
                                .padding(end = 6.dp)
                        )

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

            val statusListType = listOf(
                StatusListType.Watching,
                StatusListType.InPlan,
                StatusListType.Watched,
                StatusListType.Postponed,
            )
            val statusListTitle = listOf("Смотрю", "Запланировано", "Просмотрено", "Отложено")

            var expandedDropDownMenu by remember { mutableStateOf(false) }
            var titleChoice: String by remember { mutableStateOf("Добавить в список") }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                    .width(180.dp)
            ) {
                Row(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .clickable {
                            expandedDropDownMenu = !expandedDropDownMenu
                        }
                        .background(
                            shape = RoundedCornerShape(10.dp),
                            color = orange400
                        )
                        .width(180.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DropdownMenu(
                        expanded = expandedDropDownMenu,
                        onDismissRequest = {
                            expandedDropDownMenu = false
                        }) {
                        statusListTitle.forEachIndexed { index, s ->
                            val isSelected = titleChoice == s
                            Text(
                                text = s,
                                style = MaterialTheme.typography.h3,
                                color = if (isSelected) orange400 else MaterialTheme.colors.primary,
                                modifier = Modifier.padding(8.dp)
                                    .clickable {
                                        println("Wafl?!@#")
                                        titleChoice = s
                                        onStatusClick.invoke(statusListType[index], contentType, data.url)
                                        expandedDropDownMenu = !expandedDropDownMenu
                                    }
                            )
                        }
                    }
                    Text(text = titleChoice, style = MaterialTheme.typography.h3)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Choice type content"
                    )
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