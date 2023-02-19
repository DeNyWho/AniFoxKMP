package com.example.android.composable.content_horizontal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import coil.annotation.ExperimentalCoilApi
import com.example.android.composable.shimmer.ContentListHeaderWithButtonShimmer
import com.example.android.composable.shimmer.onUpdateShimmerBounds
import com.example.android.composable.shimmer.rememberShimmerCustomBounds
import com.example.android.composable.shimmer.showItemVerticalAnimeShimmer
import com.example.android.presentation.home.item.ItemVertical
import com.example.android.presentation.home.item.ItemVerticalModifier
import com.example.android.presentation.home.item.showItemVerticalAnimeMoreWhenPastLimit
import com.example.common.core.enum.ContentType
import com.example.common.models.mangaResponse.light.MangaLight
import com.example.common.presentation.data.StateListWrapper
import com.valentinilk.shimmer.Shimmer

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ScrollableHorizontalContent(
	modifier: Modifier,
	headerModifier: Modifier = HorizontalContentHeaderConfig.Default,
	itemModifier: Modifier = ItemVerticalModifier.Default,
	shimmer: Shimmer = rememberShimmerCustomBounds(),
	thumbnailHeight: Dp = ItemVerticalModifier.ThumbnailHeightDefault,
	headerTitle: String,
	contentState: StateListWrapper<MangaLight>,
	contentPadding: PaddingValues,
	contentArrangement: Arrangement.Horizontal,
	textAlign: TextAlign = TextAlign.Start,
	onIconClick: () -> Unit,
//	onItemClick: (String, ContentType) -> Unit,
) {
	if (contentState.isLoading) {
		ContentListHeaderWithButtonShimmer(shimmerInstance = shimmer)
	} else if (contentState.data.isNotEmpty()) {
		HorizontalContentHeader(
			modifier = headerModifier,
			title = headerTitle,
			onButtonClick = onIconClick
		)
	}

	LazyRow(
		modifier = modifier.onUpdateShimmerBounds(shimmer),
		contentPadding = contentPadding,
		horizontalArrangement = contentArrangement
	) {
		if (contentState.isLoading) {
			showItemVerticalAnimeShimmer(
				modifier = itemModifier,
				shimmerInstance = shimmer,
				thumbnailHeight = thumbnailHeight
			)
		} else if (contentState.data.isNotEmpty()) {
			items(
				items = contentState.data.take(11), key =  {it.id}
			) {data ->
				ItemVertical(
					modifier = itemModifier,
					data = data,
					thumbnailHeight = thumbnailHeight,
					textAlign = textAlign,
//					onClick = onItemClick
				)
			}
			showItemVerticalAnimeMoreWhenPastLimit(
				modifier = itemModifier,
				thumbnailHeight = thumbnailHeight,
				size = 13,
				onClick = onIconClick
			)
		}
	}
}