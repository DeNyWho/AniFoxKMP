package com.example.android.composable.content_horizontal

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android.composable.item.ItemVerticalModifier
import com.example.android.composable.shimmer.rememberShimmerCustomBounds
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.common.ContentLight

@Composable
fun CarouselDefaultItem(
    onContentClick: (String, String) -> Unit,
    state: StateListWrapper<ContentLight>,
    onHeaderClick: (String, String, String?, String?, List<String>?) -> Unit,
    headerTitle: String,
    onIconClick: () -> Unit
) {
    ScrollableHorizontalContent(
        modifier = Modifier,
        shimmer = rememberShimmerCustomBounds(),
        headerTitle = headerTitle,
        contentState = state,
        contentPadding = PaddingValues(horizontal = 12.dp),
        contentArrangement = ItemVerticalModifier.HorizontalArrangement.Default,
        headerModifier = HorizontalContentHeaderConfig.Home,
        onIconClick = onIconClick,
        onItemClick = onContentClick
    )
}