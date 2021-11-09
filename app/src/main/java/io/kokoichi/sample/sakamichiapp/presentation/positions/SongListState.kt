package io.kokoichi.sample.sakamichiapp.presentation.positions

import io.kokoichi.sample.sakamichiapp.domain.model.Song

/**
 * States for the web API return value.
 */
data class SongListState(
    var isLoading: Boolean = false,
    var songs: List<Song> = emptyList(),
    var error: String = ""
)
