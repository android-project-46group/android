package jp.mydns.kokoichi0206.sakamichiapp.presentation.blog

/**
 * States for the web API return value.
 */
data class BlogApiState(
    var isLoading: Boolean = false,
    var members: MutableList<jp.mydns.kokoichi0206.model.Blog> = mutableListOf(),
    var error: String = ""
)
