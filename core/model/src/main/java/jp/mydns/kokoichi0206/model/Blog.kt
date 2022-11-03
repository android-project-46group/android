package jp.mydns.kokoichi0206.model

/**
 * Data class for one member in business logic.
 */
data class Blog(
    val name: String,
    val blogUrl: String,
    val lastBlogImg: String,
    val lastUpdatedAt: String,
)
