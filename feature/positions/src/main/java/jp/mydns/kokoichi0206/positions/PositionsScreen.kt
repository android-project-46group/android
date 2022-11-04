package jp.mydns.kokoichi0206.positions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.ImageRequest
import jp.mydns.kokoichi0206.feature.positions.R
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Composable
fun PositionsScreen(
    viewModel: PositionsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    // Initialize the songs when first loaded.
    if (viewModel.getNeedApiCall()) {
        viewModel.setSongs()
        viewModel.setHasInitialized(false)
    }

    Column(
        modifier = Modifier
            .padding(bottom = 56.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            SongTitle(
                viewModel = viewModel,
                uiState = uiState
            )
        }
        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            EachRow(positions = uiState.thirdRow)
        }

        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            EachRow(positions = uiState.secondRow)
        }

        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            EachRow(positions = uiState.firstRow)
        }
    }
}

@Composable
fun EachRow(positions: List<jp.mydns.kokoichi0206.model.Position>) {
    val IMG_SIZE = 60.dp
    val FONT_SIZE = 15.sp
    val IMG_PADDING = 3.dp

    LazyRow {
        items(positions) { position ->

            Column(
                modifier = Modifier.padding(IMG_PADDING)
            ) {

                val context = LocalContext.current

                val request = ImageRequest.Builder(context)
                    .data(position.imgUrl)
                    .placeholder(R.drawable.hinata_official_icon)
                    .build()
                val imageLoader = ImageLoader.Builder(context)
                    .crossfade(true)
                    .okHttpClient {
                        OkHttpClient.Builder()
                            .addInterceptor(jp.mydns.kokoichi0206.data.remote.LoggingInterceptor())
                            .addInterceptor(jp.mydns.kokoichi0206.data.remote.RetryInterceptor())
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
                    contentDescription = null,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .size(IMG_SIZE)
                        .fillMaxWidth()
                        .clip(CircleShape)
                        .clickable {
                        },
                )

                Text(text = position.memberName, fontSize = FONT_SIZE)
            }
        }
    }
}

