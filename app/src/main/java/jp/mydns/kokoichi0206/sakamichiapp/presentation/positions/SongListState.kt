package jp.mydns.kokoichi0206.sakamichiapp.presentation.positions

/**
 * States for the web API return value.
 */
data class SongListState(
    var isLoading: Boolean = false,
    var songs: List<jp.mydns.kokoichi0206.model.Song> = emptyList(),
    var error: String = ""
)
