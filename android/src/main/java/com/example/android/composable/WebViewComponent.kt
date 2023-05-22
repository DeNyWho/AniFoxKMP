package com.example.android.composable

import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun WebViewComponent(url: String) {
    val refererUrl = "https://anifox.club" // Укажите нужный реферер URL

    val iframeHtml = remember {
        """
        <html>
        <head>
        </head>
        <body>
        <iframe src="$url" frameborder="0" allowfullscreen ></iframe>
        </body>
        </html>
        """.trimIndent()
    }

    AndroidView(factory = { context ->
        val webView = WebView(context).apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    request?.let {
                        val headers = HashMap<String, String>()
                        headers["Referer"] = refererUrl
                        view?.loadUrl(request.url.toString(), headers)
                    }
                    return true
                }
            }
        }

        webView.loadDataWithBaseURL(null, iframeHtml, "text/html", "UTF-8", null)
        webView
    })

//    AndroidView(
//        modifier = Modifier.fillMaxSize(),
//        factory = { webView }
//    )

//    DisposableEffect(Unit) {
//        onDispose {
//            webView.clearCache(true)
//            webView.destroy()
//        }
//    }

    LaunchedEffect(iframeHtml) {
    }
}