package io.kokoichi.sample.sakamichiapp.presentation.positions

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SongTitle(
    viewModel: PositionsViewModel,
    uiState: PositionsUiState,
) {
    Row() {
        Box(
            modifier = Modifier
                .weight(3f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = uiState.selectedSong
            )
        }

        var sortExpanded by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(0.dp),
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
                uiState.songs.forEach { song ->
                    DropdownMenuItem(
                        onClick = {
                            sortExpanded = false

                            viewModel.getPositions(songName = song.title)
                            viewModel.setSelectedSong(title = song.title)
                        }
                    ) {
                        Text(song.title)
                    }
                }
            }
        }
    }
}