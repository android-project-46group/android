package io.kokoichi.sample.sakamichiapp.ui.formationPage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import io.kokoichi.sample.sakamichiapp.R
import io.kokoichi.sample.sakamichiapp.ui.home.HomeViewModel
import io.kokoichi.sample.sakamichiapp.webapi.getPositions
import io.kokoichi.sample.sakamichiapp.webapi.getSongs

@Composable
internal fun FormationView(navController: NavHostController, viewModel: HomeViewModel) {

    val uiState by viewModel.uiState.collectAsState()

    var title = "ってか"

    // これがないと、再描画が走るたびに「ってか」になっちゃう
    if (uiState.formations.size == 0) {
        getPositions(title = title, viewModel = viewModel)
    }


    if (uiState.formations.size > 0) {

        var firstRow: MutableList<Position> = mutableListOf()
        var secondRow: MutableList<Position> = mutableListOf()
        var thirdRow: MutableList<Position> = mutableListOf()

        for (position in uiState.formations) {
            if (position.position[2] != '0') {
                firstRow.add(position)
            } else if (position.position[1] != '0') {
                secondRow.add(position)
            } else if (position.position[0] != '0') {
                thirdRow.add(position)
            }
        }

        // Get songs from web api
        getSongs(groupName = "hinatazaka", viewModel = viewModel)

        Column() {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row() {
                    Box(
                        modifier = Modifier
                            .weight(3f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.formationTitle,
                            fontSize = 40.sp
                        )
                    }

                    var sortExpanded by remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(0.dp, 0.dp, 0.dp, 0.dp),
                    ) {
                        Button(
                            onClick = {
                                sortExpanded = true
                            }
                        ) {
                            Text(text = "曲名", fontSize = 8.sp)
                        }
                        DropdownMenu(
                            expanded = sortExpanded,
                            onDismissRequest = { sortExpanded = false }) {
                            for (song in uiState.songTitles) {
                                DropdownMenuItem(
                                    onClick = {
                                        sortExpanded = false

                                        getPositions(title = song.title, viewModel = viewModel)
                                        viewModel.setFormationTitle(song.title)
                                    }
                                ) {
                                    Text(song.title)
                                }
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                EachRow(positions = thirdRow)
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                EachRow(positions = secondRow)
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                EachRow(positions = firstRow)
            }
        }
    }
}

@Composable
fun EachRow(positions: MutableList<Position>) {

    val IMG_SIZE = 60.dp
    val FONT_SIZE = 15.sp
    val IMG_PADDING = 3.dp

    LazyRow {
        items(positions) { position ->

            Column(
                modifier = Modifier.padding(IMG_PADDING)
            ) {

                val imgUrl = position.img_url

                var loadError by remember { mutableStateOf(false) }

                val painter = rememberImagePainter(imgUrl,
                    builder = {
                        placeholder(R.drawable.ic_baseline_person_outline_24)
                        listener(
                            onStart = {
//                                loadError = false
                                Log.d("state", "reload")
                            },
                            onSuccess = { request, metadata ->
//                                loaded = true
                            },
                            onError = { req, thro ->
                                loadError = true
                            }
                        )
                    })

                // In the case where you cannot get a image from the URL
                if (loadError) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_person_outline_24),
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
                        // rememberImagePainterには size が必要！
                        painter = painter,
                        contentDescription = null,
                        // TODO .size が必要だったから設定した値であり、ハードコーディングを避ける！ -----
                        modifier = Modifier
                            .size(IMG_SIZE)
                            .fillMaxWidth()
                            .clip(CircleShape)
                            .clickable {
                            },
                        contentScale = ContentScale.Crop
                    )
                }

                Text(text = position.name_ja, fontSize = FONT_SIZE)
            }
        }
    }
}


data class Position(
    val title: String = "不明",
    val single: String = "不明",
    val name_en: String = "不明",
    val name_ja: String = "不明",
    val img_url: String? = null,
    val position: String = "不明",
    val is_center: Boolean = false,
)

data class Song(
    val single: String = "不明",
    val title: String = "不明",
    val center: String? = null,
)
