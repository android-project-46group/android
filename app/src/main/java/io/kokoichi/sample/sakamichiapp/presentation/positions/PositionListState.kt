package io.kokoichi.sample.sakamichiapp.presentation.positions

import io.kokoichi.sample.sakamichiapp.domain.model.Position

/**
 * States for the web API return value.
 */
data class PositionListState(
    var isLoading: Boolean = false,
    var positions: List<Position> = emptyList(),
    var error: String = ""
)
