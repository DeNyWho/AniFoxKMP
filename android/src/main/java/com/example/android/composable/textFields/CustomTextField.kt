package com.example.android.composable.textFields

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.android.presentation.manga.composable.SearchEditText
import com.example.android.ui.blackestBack

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
    padding: PaddingValues = PaddingValues(0.dp),
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    content: (@Composable () -> Unit)? = null
) {
    Surface (
        modifier = modifier,
        border = BorderStroke(2.dp, blackestBack),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colors.surface,
        elevation = elevation,
    ) {
        Row (
            modifier = Modifier.padding(padding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(leadingIcon != null) {
                leadingIcon()
            }

            Box (
                modifier = Modifier.weight(1f)
            ) {
                if(content != null) {
                    content()
                }
            }

            if(trailingIcon != null) {
                trailingIcon()
            }
        }
    }
}


@Preview(
    widthDp = 280
)
@Composable
fun CustomTextFieldPreview () {
    CustomTextField(
        content = { SearchEditText() },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            Icon(imageVector = Icons.Default.Image, contentDescription = null)
        },
        padding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
    )
}