package io.kokoichi.sample.sakamichiapp.presentation.blog

import io.kokoichi.sample.sakamichiapp.domain.model.Blog

/**
 * States for the web API return value.
 */
data class BlogApiState(
    var isLoading: Boolean = false,
    var members: MutableList<Blog> = mutableListOf(),
    var error: String = ""
)
