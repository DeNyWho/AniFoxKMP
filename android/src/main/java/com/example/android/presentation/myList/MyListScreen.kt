package com.example.android.presentation.myList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun MyListScreen (
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Box (
        modifier = modifier.fillMaxSize()
    ) {
        Text("Здесь будет MyListScreen", modifier = Modifier.align(Alignment.Center))
    }
}