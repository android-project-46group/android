package io.kokoichi.sample.sakamichiapp.data.remote.dto

import io.kokoichi.sample.sakamichiapp.domain.model.Song

/**
 * Data class for one song in the API.
 */
data class SongDto(
    val center: Any,
    val single: String,
    val title: String
)

fun SongDto.toSong(): Song {
    return Song(
        center = center,
        single = single,
        title = title
    )
}