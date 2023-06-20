package com.example.android.presentation.myList

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.android.R
import com.example.android.common.isScrolledToTheEnd
import com.example.android.navigation.Screen
import com.example.android.ui.orange400
import com.google.accompanist.pager.*
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyListScreen (
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MyListViewModel = getViewModel<MyListViewModel>(),
) {
    val listStateOnWatch = rememberLazyGridState()
    val listStateOnWatched = rememberLazyGridState()
    val listStateOnPostponed = rememberLazyGridState()
    val listStateInPlan = rememberLazyGridState()

    val watchState by viewModel.onWatch
    val watchedState by viewModel.onWatched
    val inPlanState by viewModel.inPlan
    val onPostponedState by viewModel.onPostponed
    val token by viewModel.token

    val contentState = listOf(watchState, inPlanState, watchedState, onPostponedState)

    val contentListState = listOf(listStateOnWatch, listStateInPlan, listStateOnWatched, listStateOnPostponed)

    println(viewModel.inPlan.value )

    LaunchedEffect(viewModel) {
            snapshotFlow {
                listStateOnWatch.isScrolledToTheEnd() && listStateOnWatch.layoutInfo.totalItemsCount != listStateOnWatch.layoutInfo.visibleItemsInfo.size && !listStateOnWatch.isScrollInProgress
            }.distinctUntilChanged().onEach {
                if (it) {
                    viewModel.getNextContentPartOnWatch()
                }
            }.launchIn(this)

            snapshotFlow {
                listStateInPlan.isScrolledToTheEnd() && listStateInPlan.layoutInfo.totalItemsCount != listStateInPlan.layoutInfo.visibleItemsInfo.size && !listStateInPlan.isScrollInProgress
            }.distinctUntilChanged().onEach {
                if (it) {
                    viewModel.getNextContentPartInPlan()
                    viewModel.getNextContentPartInPlan()
                }
            }.launchIn(this)

            snapshotFlow {
                listStateOnWatched.isScrolledToTheEnd() && listStateOnWatched.layoutInfo.totalItemsCount != listStateOnWatched.layoutInfo.visibleItemsInfo.size && !listStateOnWatched.isScrollInProgress
            }.distinctUntilChanged().onEach {
                if (it) {
                    viewModel.getNextContentPartOnWatched()
                    viewModel.getNextContentPartOnWatched()
                }
            }.launchIn(this)

            snapshotFlow {
                listStateOnPostponed.isScrolledToTheEnd() && listStateOnPostponed.layoutInfo.totalItemsCount != listStateOnPostponed.layoutInfo.visibleItemsInfo.size && !listStateOnPostponed.isScrollInProgress
            }.distinctUntilChanged().onEach {
                if (it) {
                    viewModel.getNextContentPartOnPostponed()
                    viewModel.getNextContentPartOnPostponed()
                }
            }.launchIn(this)
    }

    if(!token.isLoading && token.data != null) {
        MyListContentList(
            contentState = contentState,
            contentListState = contentListState,
            onItemClick = { type, id ->
                navController.navigate("${Screen.Details.route}/$type/$id")
            },
            onRefreshWatch = { viewModel.refreshDataWatch() },
            onRefreshWatched = { viewModel.refreshDataWatched() },
            onRefreshInPlan = { viewModel.refreshDataInPlan() },
            onRefreshPostponed = { viewModel.refreshDataPostponed() },
            onDropDownClick = { type ->
                viewModel.setContentType(type)
            },
            contentType = viewModel.getContentType()
        )
    } else if(token.data == null && !token.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.anifoxicon),
                contentDescription = "require auth icon"
            )
            Text(
                text = "Чтобы увидеть то, что находится здесь\nвам необходимо авторизоваться по кнопке снизу",
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center
            )

            Button(
                onClick = {
                    navController.navigate(Screen.SignIn.route)
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = orange400,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp, top = 35.dp)
                    .height(50.dp),
            ) {
                Text(text = stringResource(R.string.SignIn), fontSize = 20.sp)
            }
        }
    }

}
