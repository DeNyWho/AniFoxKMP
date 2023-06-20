package com.example.android.ui

import androidx.compose.material.Colors
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.android.R


val vietnamPro = FontFamily(
    Font(R.font.vietnam_medium, weight = FontWeight.Medium),
    Font(R.font.vietnam_regular, weight = FontWeight.Normal)
)

val nunito = FontFamily(
    Font(R.font.nunito_medium, weight = FontWeight.Medium),
    Font(R.font.nunito_regular, weight = FontWeight.Normal),
)


@Composable
fun MyTypography(colors: Colors): Typography {
    return Typography(
        h1 = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            color = colors.primary
        ),
        h2 = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color = colors.primary
        ),
        h3 = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = colors.primary
        ),
        h4 = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = colors.primary
        ),
        h5 = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = colors.primary
        ),
        h6 = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = colors.primary
        ),
        body1 = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = Size.Text13
        ),
        caption = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = gray400
        ),
    )
}