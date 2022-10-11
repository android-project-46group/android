package jp.mydns.kokoichi0206.sakamichiapp.presentation.positions

import jp.mydns.kokoichi0206.sakamichiapp.domain.model.Position

/**
 * States for the web API return value.
 */
data class PositionListState(
    var isLoading: Boolean = false,
    var positions: List<Position> = emptyList(),
    var error: String = ""
)
