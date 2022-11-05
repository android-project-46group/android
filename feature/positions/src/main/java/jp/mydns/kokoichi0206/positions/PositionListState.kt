package jp.mydns.kokoichi0206.positions

/**
 * States for the web API return value.
 */
data class PositionListState(
    var isLoading: Boolean = false,
    var positions: List<jp.mydns.kokoichi0206.model.Position> = emptyList(),
    var error: String = ""
)
