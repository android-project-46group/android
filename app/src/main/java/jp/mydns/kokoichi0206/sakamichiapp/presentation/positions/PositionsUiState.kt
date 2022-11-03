package jp.mydns.kokoichi0206.sakamichiapp.presentation.positions

import jp.mydns.kokoichi0206.common.GroupName

/**
 * States for Positions UI.
 */
data class PositionsUiState(
    var groupName: GroupName = GroupName.HINATAZAKA,
    var selectedSong: String? = null,
    var firstRow: List<jp.mydns.kokoichi0206.model.Position> = emptyList(),
    var secondRow: List<jp.mydns.kokoichi0206.model.Position> = emptyList(),
    var thirdRow: List<jp.mydns.kokoichi0206.model.Position> = emptyList(),
    var songs: List<jp.mydns.kokoichi0206.model.Song> = emptyList(),
)
