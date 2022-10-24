package jp.mydns.kokoichi0206.sakamichiapp.presentation.member_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.ImageRequest
import jp.mydns.kokoichi0206.sakamichiapp.R
import jp.mydns.kokoichi0206.sakamichiapp.data.remote.LoggingInterceptor
import jp.mydns.kokoichi0206.sakamichiapp.domain.model.Member
import jp.mydns.kokoichi0206.sakamichiapp.presentation.member_detail.components.memberImage
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Composable
fun OnePerson(
    member: Member,
    modifier: Modifier = Modifier,
    onclick: (Member) -> Unit = {},
    extraInfo: String? = null
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clickable {
                onclick(member)
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current

            val request = ImageRequest.Builder(context)
                .data(member.imgUrl)
                .placeholder(
                    when (member.group) {
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
                        .addInterceptor(LoggingInterceptor())
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
                    .clickable {
                        onclick(member)
                    }
                    .memberImage(),
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = member.name,
                color = MaterialTheme.colors.secondary,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(0.dp))

            // Extra information (like birthday, height and so on)
            if (extraInfo != null) {
                Text(
                    text = extraInfo,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
