package com.example.android.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.android.composable.CenterCircularProgressIndicator
import com.example.android.navigation.Screen
import com.example.android.presentation.detail.composable.ContentDetailsScreenToolbar
import com.example.android.ui.MyIcons
import com.example.android.ui.orange
import com.example.android.ui.red
import com.example.android.ui.white
import com.example.common.nav.ContentDetailsNavArgs
import com.google.accompanist.insets.LocalWindowInsets
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.koin.androidx.compose.getViewModel

@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel = getViewModel(),
    navArgs: ContentDetailsNavArgs
) {
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
    val animateDuration = 250
    val density = LocalDensity.current
    val statusBarHeight = with (density) { LocalWindowInsets.current.statusBars.top.toDp() }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val contentHeight = with(density) {
        (190 * screenWidth.roundToPx() / 140).toDp()
    }

    LaunchedEffect(navArgs, viewModel) {
        viewModel.getDetailAnime(navArgs.contentType.name, navArgs.id)
        viewModel.getScreenshots(navArgs.id)
        viewModel.getMedia(navArgs.id)
        viewModel.getSimilar(navArgs.id)
        viewModel.getRelated(navArgs.id)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (viewModel.detailAnime.value.data.isEmpty()) {
            CenterCircularProgressIndicator(
                size = 40.dp,
                color = red,
                modifier = Modifier.background(MaterialTheme.colors.background)
            )
        } else {
            CollapsingToolbarScaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                state = toolbarScaffoldState,
                scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
                toolbar = {
                    ContentDetailsScreenToolbar(
                        contentDetailsState = viewModel.detailAnime.value,
                        toolbarScaffoldState = toolbarScaffoldState
                    ) { navController.popBackStack() }
                }
            ) {
                DetailContentList(
                    detailsState = viewModel.detailAnime.value,
                    screenshotsState = viewModel.screenshots.value,
                    mediaState = viewModel.media.value,
                    similarState = viewModel.similar.value,
                    relatedState = viewModel.related.value,
                    onContentClick = { type, id ->
                        navController.navigate("${Screen.Details.route}/$type/$id")
                    },
                    onHeaderClick = { typeOfScreen: String, type: String, order: String?, status: String?, genres: List<String>? ->
                        navController.navigate(
                            "${Screen.MorePage.route}/$typeOfScreen/$type/$order/$status/${
                                genres?.joinToString(
                                    ","
                                )
                            }"
                        )
                    },
                )
            }
            FloatingActionButton(
                onClick = {
                    val modifiedUrl = viewModel.detailAnime.value.data[0].linkPlayer!!.replace("/", "-")
                    navController.navigate("${Screen.Player.route}/$modifiedUrl")
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        start = 16.dp,
                        end = 32.dp,
                        bottom = 32.dp,
                        top = 0.dp
                    )
                    .size(64.dp),
                backgroundColor = orange
            ) {
                Icon(
                    painter = painterResource(MyIcons.Outlined.play),
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    contentDescription = "Play anime",
                    tint = white
                )
            }
        }
    }
}