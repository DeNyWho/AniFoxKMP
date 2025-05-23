package com.example.android.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = getViewModel (),
    modifier: Modifier = Modifier,
) {
    Box (
        modifier = modifier.fillMaxSize()
    ) {
        Text("Здесь будет HomeScreen", modifier = Modifier.align(Alignment.Center))
    }

}