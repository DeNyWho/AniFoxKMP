package com.example.android.presentation.detail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.common.nav.ContentDetailsNavArgs
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

private object ContentDetailsScreenSection {
    const val ContentDescription = "content_description_composable"
    const val ContentGenre = "content_genre_chips"
    const val ContentLinked = "content_linked"
    const val ContentSimilar = "content_similar"
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel,
    navArgs: ContentDetailsNavArgs
) {

}

