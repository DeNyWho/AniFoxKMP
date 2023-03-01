package com.example.android.presentation.morePage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.android.presentation.morePage.item.MorePageToolbar
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.koin.androidx.compose.getViewModel

@Composable
fun MorePageScreen(
    navController: NavController,
    viewModel: MorePageViewModel = getViewModel(),
) {
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
    CollapsingToolbarScaffold (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        state = rememberCollapsingToolbarScaffoldState(),
        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
        toolbar = {
            MorePageToolbar(
                toolbarScaffoldState = toolbarScaffoldState
            ) { navController.popBackStack() }
        }
    ) {

    }
}