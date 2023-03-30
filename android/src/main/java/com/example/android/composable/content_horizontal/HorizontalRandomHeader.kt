package com.example.android.composable.content_horizontal

import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.android.R


@Composable
fun HorizontalRandomHeader (
    modifier: Modifier = Modifier,
    title: String,
    onButtonClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .heightIn(min = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = TextStyle(
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        )
        Spacer(modifier = Modifier.weight(1f))

        var isPlaying by remember { mutableStateOf(false) }

        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.refresh)
        )

        if (onButtonClick != null) {
            IconButton(onClick = {
                isPlaying = true
                onButtonClick()
            }
            ) {
                LottieAnimation(
                    isPlaying = true,
                    restartOnPlay = true,
                    composition = composition,
                    modifier = Modifier.size(40.dp),
                    iterations = 1,
                )
            }
        }
    }
}