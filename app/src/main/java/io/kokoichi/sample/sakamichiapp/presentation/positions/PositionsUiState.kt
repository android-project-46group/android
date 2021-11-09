package io.kokoichi.sample.sakamichiapp.presentation.positions

import io.kokoichi.sample.sakamichiapp.domain.model.Position
import io.kokoichi.sample.sakamichiapp.domain.model.Song
import io.kokoichi.sample.sakamichiapp.presentation.member_list.GroupName

/**
 * States for Positions UI.
 */
data class PositionsUiState(
    var groupName: GroupName = GroupName.HINATAZAKA,
    var selectedSong: String = "楽曲を選択してください",
    var firstRow: List<Position> = emptyList(),
    var secondRow: List<Position> = emptyList(),
    var thirdRow: List<Position> = emptyList(),
    var songs: List<Song> = emptyList(),
)
