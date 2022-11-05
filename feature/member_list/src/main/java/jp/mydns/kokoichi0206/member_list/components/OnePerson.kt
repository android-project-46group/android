package jp.mydns.kokoichi0206.member_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.ImageRequest
import jp.mydns.kokoichi0206.common.GroupName
import jp.mydns.kokoichi0206.common.R
import jp.mydns.kokoichi0206.common.interceptor.LoggingInterceptor
import jp.mydns.kokoichi0206.common.interceptor.RetryInterceptor
import jp.mydns.kokoichi0206.common.ui.theme.CustomSakaTheme
import jp.mydns.kokoichi0206.member_list.MainView
import jp.mydns.kokoichi0206.member_list.MemberListUiState
import jp.mydns.kokoichi0206.model.Member
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
                        .addInterceptor(RetryInterceptor())
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
                contentDescription = "image of ${member.name}",
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

@Preview
@Composable
fun MainViewPreview() {
    val member = Member(
        name = "名前 A",
        bloodType = "A型",
        generation = "1期生",
        height = "212cm",
        birthday = "2001年8月29日",
        imgUrl = "https://kokoichi0206.mydns.jp/imgs/example/0.png",
    )

    CustomSakaTheme(group = GroupName.NOGIZAKA.jname) {
        Box(modifier = Modifier.background(Color.White)) {
            OnePerson(member = member)
        }
    }
}

@Preview
@Composable
fun MainViewWithSakuraPreview() {
    val member = Member(
        name = "名前 A",
        bloodType = "A型",
        generation = "1期生",
        height = "212cm",
        birthday = "2001年8月29日",
        imgUrl = "https://kokoichi0206.mydns.jp/imgs/example/0.png",
    )

    CustomSakaTheme(group = GroupName.SAKURAZAKA.jname) {
        Box(modifier = Modifier.background(Color.White)) {
            OnePerson(member = member)
        }
    }
}

@Preview
@Composable
fun MainViewWithExtraInfoPreview() {
    val member = Member(
        name = "名前 A",
        bloodType = "A型",
        generation = "1期生",
        height = "212cm",
        birthday = "2001年8月29日",
        imgUrl = "https://kokoichi0206.mydns.jp/imgs/example/0.png",
    )

    CustomSakaTheme(group = GroupName.NOGIZAKA.jname) {
        Box(modifier = Modifier.background(Color.White)) {
            OnePerson(member = member, extraInfo = member.birthday)
        }
    }
}
