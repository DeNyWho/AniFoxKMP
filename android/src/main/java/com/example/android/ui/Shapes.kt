package com.example.android.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp


object Shapes {
    val ThemeShapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    val Rounded6 = RoundedCornerShape(6.dp)
    val Rounded12 = RoundedCornerShape(12.dp)
    val RoundedAllPercent50 = RoundedCornerShape(50)
}
