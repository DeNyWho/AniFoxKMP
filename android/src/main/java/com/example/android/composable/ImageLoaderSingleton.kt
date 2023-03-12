package com.example.android.composable

import android.content.Context
import coil.ImageLoader
import coil.request.ImageRequest
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class ImageLoaderSingleton {
    companion object {
        private lateinit var imageLoader: ImageLoader

        private val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        fun initialize(context: Context) {
            imageLoader = ImageLoader.Builder(context)
                .okHttpClient { okHttpClient }
                .build()
        }

        fun loadImage(request: ImageRequest) {
            imageLoader.enqueue(request)
        }
    }
}
