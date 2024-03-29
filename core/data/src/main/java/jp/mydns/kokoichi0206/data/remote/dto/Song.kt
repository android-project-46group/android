package jp.mydns.kokoichi0206.data.remote.dto

/**
 * Data class for one song in the API.
 */
data class SongDto(
    val center: Any?,
    val single: String,
    val title: String
)

fun SongDto.toSong(): jp.mydns.kokoichi0206.model.Song {
    return jp.mydns.kokoichi0206.model.Song(
        center = center ?: "",
        single = single,
        title = title
    )
}