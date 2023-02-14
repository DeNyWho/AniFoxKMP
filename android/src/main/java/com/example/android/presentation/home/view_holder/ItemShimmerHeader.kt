package com.example.android.presentation.home.view_holder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android.ui.grey
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun ItemShimmerHeader(
    shimmerInstance: Shimmer
) {
    Column(
        modifier = Modifier
            .shimmer(shimmerInstance)
            .width(160.dp)
            .padding(start = 16.dp, top = 12.dp, bottom = 8.dp, end = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(18.dp)
                .background(color = grey)
        )

    }
}