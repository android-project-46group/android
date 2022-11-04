package jp.mydns.kokoichi0206.common.components

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView


const val webViewRoute = "web_view_route"
const val webViewProps = "url"

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
