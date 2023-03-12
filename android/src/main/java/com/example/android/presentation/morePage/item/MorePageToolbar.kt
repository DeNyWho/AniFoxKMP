package com.example.android.presentation.morePage.item

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MorePageToolbar(
    title: String = "title",
    onArrowClick: () -> Boolean,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box (
            modifier = Modifier.height(75.dp).fillMaxWidth()
                .statusBarsPadding(),
        ) {
            IconButton(
                onClick = { onArrowClick()},
                modifier = Modifier.align(Alignment.CenterStart)
            ){
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                )
            }
            Text(
                text = "title",
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Normal,
                    fontSize = 22.sp
                ),
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
            )
        }

    }

}