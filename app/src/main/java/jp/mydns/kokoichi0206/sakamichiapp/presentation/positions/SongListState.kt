package jp.mydns.kokoichi0206.sakamichiapp.presentation.positions

import jp.mydns.kokoichi0206.sakamichiapp.domain.model.Song

/**
 * States for the web API return value.
 */
data class SongListState(
    var isLoading: Boolean = false,
    var songs: List<Song> = emptyList(),
    var error: String = ""
)
