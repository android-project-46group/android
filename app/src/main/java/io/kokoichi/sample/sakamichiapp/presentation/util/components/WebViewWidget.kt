package io.kokoichi.sample.sakamichiapp.presentation.util.components

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
            webView.loadUrl(contentUrl)
        }
    )
}
