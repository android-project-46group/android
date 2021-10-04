package io.kokoichi.sample.sakamichiapp.ui.detailedPage

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewWidget(
    contentUrl: String
) {
    AndroidView(
        factory = {
            WebView(it)
        },
        update = { webView ->
            webView.webViewClient = WebViewClient()
            Log.d("TAG", contentUrl)
            webView.loadUrl(contentUrl)
        }
    )
}