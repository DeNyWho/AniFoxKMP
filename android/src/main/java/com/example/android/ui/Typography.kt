package com.example.android.ui

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.android.R


val vietnamPro = FontFamily(
    Font(R.font.vietnam_medium, weight = FontWeight.Medium),
    Font(R.font.vietnam_regular, weight = FontWeight.Normal),
)


val Typography = Typography(
    h1 = TextStyle(
        fontFamily = vietnamPro,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
    h2 = TextStyle(
        fontFamily = vietnamPro,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    h3 = TextStyle(
        fontFamily = vietnamPro,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    h4 = TextStyle(
        fontFamily = vietnamPro,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h5 = TextStyle(
        fontFamily = vietnamPro,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    h6 = TextStyle(
        fontFamily = vietnamPro,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = Size.Text13
    ),
)