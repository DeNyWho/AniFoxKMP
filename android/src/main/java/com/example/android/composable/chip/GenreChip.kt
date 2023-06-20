package com.example.android.composable.chip

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android.ui.orange400

@Composable
fun GenreChip(
    modifier: Modifier = Modifier,
    text: String
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = orange400,
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = MaterialTheme.colors.primary,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}