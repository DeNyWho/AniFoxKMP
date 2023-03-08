package com.example.android.common

import androidx.compose.foundation.lazy.grid.LazyGridState


fun LazyGridState.isScrolledToTheEnd(): Boolean {
    return layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
}