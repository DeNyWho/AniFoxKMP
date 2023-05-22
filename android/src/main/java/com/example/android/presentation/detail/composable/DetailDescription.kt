package com.example.android.presentation.detail.composable

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.android.ui.grey
import com.example.android.ui.onDarkSurface
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.common.ContentDetail

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DetailDescription(
    detailState: StateListWrapper<ContentDetail>,
    isExpanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit
) {
    val data = detailState.data[0].description
    val descriptionGradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colors.background.copy(alpha = 0F),
            MaterialTheme.colors.background.copy(alpha = 0.9F),
            MaterialTheme.colors.background,
        )
    )
    val visible = data?.length!! > 300

    if (visible) {
        AnimatedContent(
            targetState = isExpanded,
            transitionSpec = {
                expandVertically(animationSpec = tween(150, 150), initialHeight = { it }) with
                        shrinkVertically(animationSpec = tween(150, 0), targetHeight = { it }) using
                        SizeTransform(clip = true)
            }
        ) { targetExpanded ->
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onExpandedChanged(!isExpanded) }
                    )
            ) {
                if (targetExpanded) {
                    Text(
                        text = data.replace("&quot;",34.toChar().toString()),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onPrimary,
                        textAlign = TextAlign.Justify
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Switch",
                        tint = grey,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                } else {
                    Text(
                        text = data.replace("&quot;",34.toChar().toString()),
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onPrimary,
                        textAlign = TextAlign.Justify
                    )
                    Box(
                        modifier = Modifier
                            .zIndex(1F)
                            .fillMaxSize()
                            .align(Alignment.BottomCenter)
                            .background(
                                brush = descriptionGradient
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Expand",
                            tint = onDarkSurface,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .zIndex(2F)
                        )
                    }
                }
            }
        }
    } else {
        Text(
            text = data.replace("&quot;",34.toChar().toString()),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.Justify
        )
    }
}