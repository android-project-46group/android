package jp.mydns.kokoichi0206.data.remote.dto

import com.squareup.moshi.Json

/**
 * Data class for one blog in the API.
 */
data class BlogDto(
    @field:Json(name = "blog_url")
    val blogUrl: String,
    @field:Json(name = "last_blog_img")
    val lastBlogImg: String,
    @field:Json(name = "last_updated_at")
    val lastUpdatedAt: String,
    val name: String
)

fun BlogDto.toBlog(): jp.mydns.kokoichi0206.model.Blog {
    return jp.mydns.kokoichi0206.model.Blog(
        name = name,
        blogUrl = blogUrl,
        lastBlogImg = lastBlogImg,
        lastUpdatedAt = lastUpdatedAt,
    )
}
