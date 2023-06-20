package com.example.android.composable

import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import java.net.URLEncoder

@Composable
fun WebViewComponent(urlw: String) {
    val currentUrl = "https://anifox.club/"
    val generatedLink = generateLink(urlw.drop(18), currentUrl)

    AndroidView(factory = { context ->
        val webView = WebView(context).apply {
            settings.apply {
                javaScriptEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                builtInZoomControls = false
                displayZoomControls = false
                cacheMode = WebSettings.LOAD_NO_CACHE
                setSupportMultipleWindows(true)
                domStorageEnabled = true
            }
            setPadding(0, 0, 0, 0)
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    request?.let {
                        val headers = HashMap<String, String>()
                        headers["Referer"] = currentUrl
                        view?.loadUrl(request.url.toString(), headers)
                    }
                    return true
                }
            }
        }

        val iframe = "<html><body style='margin:0;padding:0;'><div style='position:relative;padding-bottom:47%;overflow:hidden;'><iframe src='$generatedLink' frameborder='0' allowfullscreen style='position:absolute;top:0;left:0;width:100%;height:100%;'></iframe></div></body></html>"
        webView.loadDataWithBaseURL(currentUrl, iframe, "text/html", "UTF-8", null)

        webView
    }, modifier = Modifier.fillMaxSize())
}

fun generateLink(serialUrl: String, currentUrl: String): String {
    val regex = Regex("/serial/\\d+/[a-f0-9]+")
    val newSerialUrl = regex.replace(serialUrl) { matchResult ->
        val matchedText = matchResult.value
        val serialId = matchedText.split("/")[2]
        val serialKey = matchedText.split("/")[3]
        "/serial/$serialId/$serialKey"
    }

    val url = "https://kodik.info$newSerialUrl"
    val queryParams = mapOf(
        "d" to "anifox.club",
        "d_sign" to "7570fdfb8ae6082d89373ab0c43ecfcbf3a52915009793b2cf322054ba67b7d0",
        "pd" to "kodik.info",
        "pd_sign" to "09ffe86e9e452eec302620225d9848eb722efd800e15bf707195241d9b7e4b2b",
        "ref" to currentUrl,
        "ref_sign" to "1b7398d1048ab5008680761c154d5dc2ff5e0b13ae9e9ded913a1e9c03b40075",
        "advert_debug" to "true"
    )

    val queryString = queryParams.entries.joinToString("&") { (key, value) ->
        "$key=${value.encodeURIComponent()}"
    }

    return "$url?$queryString"
}

fun String.encodeURIComponent(): String {
    return URLEncoder.encode(this, "UTF-8")
        .replace("+", "%20")
        .replace("%21", "!")
        .replace("%27", "'")
        .replace("%28", "(")
        .replace("%29", ")")
        .replace("%7E", "~")
}