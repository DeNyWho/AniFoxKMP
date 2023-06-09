package com.example.android.composable.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.android.composable.CenterCircularProgressIndicator
import com.example.android.ui.redLikeOrange
import com.example.android.util.getTrustManager
import com.example.common.di.getSSLContext
import okhttp3.OkHttpClient

@Composable
fun SubComposeAsyncImageWithSSL(
    modifier: Modifier = Modifier,
    request: ImageRequest,
    image: String,
) {
    val context = LocalContext.current
    return SubcomposeAsyncImage(
        modifier = modifier,
        model = request,
        contentDescription = "Content thumbnail",
        contentScale = ContentScale.Crop,
        loading = {
            CenterCircularProgressIndicator(
                strokeWidth = 2.dp,
                size = 20.dp,
                color = redLikeOrange
            )
        },
        imageLoader = if(image.contains("anifox")) {
            ImageLoader.Builder(context)
                .okHttpClient {
                    OkHttpClient().newBuilder().sslSocketFactory(
                        getSSLContext(context).socketFactory,
                        getTrustManager()
                    ).build()
                }.build()
        } else ImageLoader.Builder(context).build(),
        onError = {
            println(it.result.throwable.message)
        }
    )
}

