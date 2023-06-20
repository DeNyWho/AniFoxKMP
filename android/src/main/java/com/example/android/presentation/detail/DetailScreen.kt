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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.android.composable.CenterCircularProgressIndicator
import com.example.android.navigation.Screen
import com.example.android.presentation.detail.composable.ContentDetailsScreenToolbar
import com.example.android.ui.MyIcons
import com.example.android.ui.orange400
import com.example.android.ui.red
import com.example.android.ui.white
import com.example.common.core.enum.ContentType
import com.example.common.nav.ContentDetailsNavArgs
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.koin.androidx.compose.getViewModel

@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel = getViewModel<DetailViewModel>(),
    navArgs: ContentDetailsNavArgs
) {
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()

    val updatedNavArgs = rememberUpdatedState(navArgs)

    DisposableEffect(updatedNavArgs, viewModel) {
        viewModel.getDetail(updatedNavArgs.value.contentType.name, updatedNavArgs.value.id)

        if (updatedNavArgs.value.contentType.name == "Anime") {
            viewModel.getRelated(updatedNavArgs.value.contentType.name, updatedNavArgs.value.id)
            viewModel.getScreenshots(updatedNavArgs.value.id)
            viewModel.getMedia(updatedNavArgs.value.id)
            viewModel.getSimilar(updatedNavArgs.value.contentType.name, updatedNavArgs.value.id)
        }

        if (updatedNavArgs.value.contentType.name == "Manga") {
            viewModel.getRelated(updatedNavArgs.value.contentType.name, updatedNavArgs.value.id)
            viewModel.getSimilar(updatedNavArgs.value.contentType.name, updatedNavArgs.value.id)
        }

        onDispose {

        }
    }

    if(viewModel.detailAnime.value.data.isEmpty() && viewModel.detailAnime.value.isLoading) {
        viewModel.getDetail(updatedNavArgs.value.contentType.name, updatedNavArgs.value.id)
    }

    println("FFF W = ${viewModel.detailAnime.value}")
    println("FFF W = ${viewModel.detailAnime.value.data.isEmpty()}")
    println("FFF W = ${navArgs.id}")

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (viewModel.detailAnime.value.data.isEmpty() || viewModel.detailAnime.value.isLoading) {
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
                        toolbarScaffoldState = toolbarScaffoldState,
                        onStatusClick  = { status, contentType, url ->
                            viewModel.setList(status, contentType, url)
                        },
                        contentType = navArgs.contentType.name,
                        onArrowClick = { navController.navigateUp() }
                    )
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
                          if(navArgs.contentType == ContentType.Anime) {
                              val modifiedUrl = viewModel.detailAnime.value.data[0].linkPlayer!!.replace("/", "-")
                              navController.navigate("${Screen.Player.route}/$modifiedUrl")
                          } else {
                              navController.navigate("${Screen.ReaderChapters.route}/${navArgs.id}")
                          }
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
                backgroundColor = orange400
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