package com.example.android.presentation.search.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.android.R
import com.example.android.composable.textFields.CustomTextField
import com.example.android.ui.Size
import com.example.android.ui.red

@Composable
fun SearchBoxField (
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    searchQuery: String = "",
    placeHolder: String = "Попробуйте ''Синяя тюрьма'''",
    focusRequest: FocusRequester = FocusRequester(),
    onSearchQueryChanged: (String) -> Unit = { },
    onSearchQueryCleared: () -> Unit = { }
) {
    CustomTextField(
        modifier = modifier,
        padding = PaddingValues(12.dp),
        leadingIcon = {
            SearchLeadingIcon(
                size = 16.dp,
                padding = PaddingValues(end = 8.dp)
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                SearchTrailingIcon(
                    size = 16.dp,
                    padding = PaddingValues(start = 8.dp),
                    onClick = onSearchQueryCleared
                )
            }
        }
    ) {
        if (isEnabled) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequest),
                value = searchQuery,
                textStyle = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Medium,
                    fontSize = Size.Text14
                ),
                singleLine = true,
                cursorBrush = SolidColor(red),
                onValueChange = { onSearchQueryChanged(it) },
            )
        }

        if (searchQuery.isEmpty()) {
            Text(
                text = placeHolder,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = Size.Text14,
                    color = MaterialTheme.colors.primary
                )
            )
        }
    }
}

@Composable
fun SearchLeadingIcon(
    size: Dp = 24.dp,
    padding: PaddingValues = PaddingValues(6.dp)
) {
    Icon(
        imageVector = Icons.Default.Search,
        contentDescription = "Search",
        modifier = Modifier.padding(padding),
        tint = MaterialTheme.colors.primary
    )
}

@Composable
fun SearchTrailingIcon(
    size: Dp = 24.dp,
    padding: PaddingValues = PaddingValues(6.dp),
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier.then(Modifier.size(size)),
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_close),
            contentDescription = "Close",
            tint = MaterialTheme.colors.primary
        )
    }
}