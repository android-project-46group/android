package jp.mydns.kokoichi0206.sakamichiapp.presentation.blog

import jp.mydns.kokoichi0206.sakamichiapp.domain.model.Blog

/**
 * States for the web API return value.
 */
data class BlogApiState(
    var isLoading: Boolean = false,
    var members: MutableList<Blog> = mutableListOf(),
    var error: String = ""
)
