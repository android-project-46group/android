package jp.mydns.kokoichi0206.member_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.ImageRequest
import jp.mydns.kokoichi0206.feature.member_detail.R
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * Member Image
 */
@Composable
fun MemberImage(
    uiState: MemberDetailUiState
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 60.dp)
    ) {
        val member = uiState.member
        val context = LocalContext.current

        val request = ImageRequest.Builder(context)
            .data(member?.imgUrl)
            .placeholder(
                when (member?.group) {
                    "乃木坂" ->
                        R.drawable.nogizaka_official_icon
                    "櫻坂" ->
                        R.drawable.sakurazaka_official_icon
                    else ->
                        R.drawable.hinata_official_icon
                }
            )
            .build()
        val imageLoader = ImageLoader.Builder(context)
            .crossfade(true)
            .okHttpClient {
                OkHttpClient.Builder()
                    .addInterceptor(jp.mydns.kokoichi0206.data.remote.LoggingInterceptor())
                    // サーバー側の設定か、なぜか指定が必要！
                    .connectTimeout(777, TimeUnit.MILLISECONDS)
                    .build()
            }
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()

        AsyncImage(
            model = request,
            imageLoader = imageLoader,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopStart,
            modifier = Modifier
                .size(350.dp),
        )
    }
}