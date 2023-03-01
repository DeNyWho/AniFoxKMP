package com.example.android.presentation.home.composable

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun SearchEditText (
    fieldPlaceHolder: String = ""
) {
    Text(
        text = fieldPlaceHolder,
        style = TextStyle( color = MaterialTheme.colors.primary, fontSize = 16.sp)
    )
}