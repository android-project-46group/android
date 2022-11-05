package jp.mydns.kokoichi0206.data.remote.dto

import com.google.gson.annotations.SerializedName
import jp.mydns.kokoichi0206.model.Blog

/**
 * Data class for one blog in the API.
 */
data class BlogDto(
    @SerializedName("blog_url")
    val blogUrl: String,
    @SerializedName("last_blog_img")
    val lastBlogImg: String,
    @SerializedName("last_updated_at")
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