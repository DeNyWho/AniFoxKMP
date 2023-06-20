package com.example.android.presentation.read

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.android.composable.CenterCircularProgressIndicator
import com.example.android.presentation.morePage.item.MorePageToolbar
import com.example.android.ui.redLikeOrange
import com.example.common.nav.ContentReaderNavArgs
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


@SuppressLint("CoroutineCreationDuringComposition", "UnrememberedMutableState",
    "UnusedMaterialScaffoldPaddingParameter"
)
@Composable
fun ReadChapterInfoScreen(
    navController: NavHostController,
    navArgs: ContentReaderNavArgs
) {
    val (isLoading, setLoading) = remember { mutableStateOf(true) }
    println("ZZ FF = ${navArgs.chapterCode}")
    val (data, setData) = remember { mutableStateOf<List<String>>(emptyList()) }

    val url = "https://mangahub.ru/read/${navArgs.chapterCode}?page=1"
    println("URL = $url")
    LaunchedEffect(Unit) {
        try {
            val doc: Document = withContext(Dispatchers.IO) {
                Jsoup.connect(url).get()
            }
            val readerScanElement = doc.select("reader-scan")
            val imgData = readerScanElement.select("img").eachAttr("data-src")
            withContext(Dispatchers.Main) {
                setLoading(false)
                setData(imgData)
                println("Data = $data")
            }
        } catch (e: Exception) {
            println(e.message)
            e.printStackTrace()
            setLoading(false)
        }
    }

    println(data.size)
    data.forEach {
        println("DATA")
        println(it)
    }
    Scaffold(
        topBar = {
            MorePageToolbar (title = navArgs.chapterTitle, modifier = Modifier.padding(start = 48.dp), onArrowClick = { navController.navigateUp() })
        },
        modifier = Modifier.navigationBarsPadding()
    ) {
        if (isLoading) {
            CenterCircularProgressIndicator(
                strokeWidth = 2.dp,
                size = 20.dp,
                color = redLikeOrange
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed (
                    items = data
                ) {index, item ->
                    println("D = $item")
                    val request = ImageRequest.Builder(LocalContext.current)
                        .addHeader("Referer", "https://mangahub.ru")
                        .data("https:$item")
                        .build()

                    SubcomposeAsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = request,
                        contentDescription = "Content thumbnail",
                        contentScale = ContentScale.Crop,
                        loading = {
                            CenterCircularProgressIndicator(
                                strokeWidth = 2.dp,
                                size = 20.dp,
                                color = redLikeOrange
                            )
                        }
                    )

                }
            }
        }
    }

}