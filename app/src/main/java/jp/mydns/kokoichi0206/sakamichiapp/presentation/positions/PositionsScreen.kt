package jp.mydns.kokoichi0206.sakamichiapp.presentation.positions

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import jp.mydns.kokoichi0206.sakamichiapp.R
import jp.mydns.kokoichi0206.sakamichiapp.domain.model.Position

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
fun EachRow(positions: List<Position>) {
    val IMG_SIZE = 60.dp
    val FONT_SIZE = 15.sp
    val IMG_PADDING = 3.dp

    LazyRow {
        items(positions) { position ->

            Column(
                modifier = Modifier.padding(IMG_PADDING)
            ) {

                var loadError by remember { mutableStateOf(false) }

                val painter = rememberImagePainter(position.imgUrl,
                    builder = {
                        placeholder(R.drawable.hinata_official_icon)
                        listener(
                            onStart = {
                            },
                            onSuccess = { request, metadata ->
                            },
                            onError = { req, thro ->
                                loadError = true
                            }
                        )
                    })

                // In the case where you cannot get a image from the URL
                if (loadError) {
                    Image(
                        painter = painterResource(id = R.drawable.hinata_official_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(IMG_SIZE)
                            .fillMaxWidth()
                            .clip(CircleShape)
                            .clickable {
                                // TODO:
                                // Error が出てしまった後は、曲名を切り替えてもそこのエラーが残ってしまう
                                // 一時的な対策として、タップすると再リロサイリロード走るようにする
                                loadError = false
                            },
                        contentScale = ContentScale.Crop
                    )
                } else {

                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(IMG_SIZE)
                            .fillMaxWidth()
                            .clip(CircleShape)
                            .clickable {
                            },
                        contentScale = ContentScale.Crop
                    )
                }

                Text(text = position.memberName, fontSize = FONT_SIZE)
            }
        }
    }
}
