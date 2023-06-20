package com.example.android.presentation.read

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.android.common.isScrolledToTheEnd
import com.example.android.navigation.Screen
import com.example.android.presentation.morePage.item.MorePageToolbar
import com.example.common.nav.ContentChaptersNavArgs
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReaderChaptersScreen(
    viewModel: ReaderViewModel = getViewModel<ReaderViewModel>(),
    navController: NavHostController,
    navArgs: ContentChaptersNavArgs
) {
    val listState = rememberLazyListState()

    val searchQuery = rememberSaveable { mutableStateOf("") }

    val contentState by viewModel.contentState

    LaunchedEffect(viewModel) {
        viewModel.getNextContentPart(navArgs.mangaID)
        snapshotFlow {
            listState.isScrolledToTheEnd() && listState.layoutInfo.totalItemsCount != listState.layoutInfo.visibleItemsInfo.size && !listState.isScrollInProgress && searchQuery.value.isEmpty()
        }.distinctUntilChanged().onEach {
            if (it) {
                viewModel.getNextContentPart(navArgs.mangaID)
                viewModel.getNextContentPart(navArgs.mangaID)
            }
        }.launchIn(this)
    }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            MorePageToolbar (title = "Выберите главу", onArrowClick = { navController.navigateUp() })

        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.navigationBarsPadding()
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            if(contentState.data.isNotEmpty()) {
                itemsIndexed(contentState.data) { index, item ->
                    Column(
                        modifier = Modifier
                            .padding(4.dp)
                            .border(
                                1.dp,
                                shape = RoundedCornerShape(20),
                                color = MaterialTheme.colors.primary
                            )
                            .clickable {
                                navController.navigate("${Screen.ReadChapter.route}/${navArgs.mangaID}/${item.urlCode}/${item.id}/${item.title}")
                            }
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = item.title,
                            style = TextStyle(
                                color = MaterialTheme.colors.primary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                        Text(
                            text = item.date.toString(),
                            style = TextStyle(
                                color = MaterialTheme.colors.primary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }





}
