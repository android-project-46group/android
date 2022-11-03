package jp.mydns.kokoichi0206.data.remote

import jp.mydns.kokoichi0206.data.remote.dto.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Interface for accessing the APIs with Retrofit.
 */
interface SakamichiApi {

    @GET("api/v1/members")
    suspend fun getMembers(
        @Query("gn") groupName: String,
        @Query("key") apiKey: String,
    ): MembersDto

    @GET("api/v1/blogs")
    suspend fun getBlogs(
        @Query("gn") groupName: String,
        @Query("key") apiKey: String,
    ): BlogsDto

    @GET("api/v1/songs")
    suspend fun getSongs(
        @Query("gn") groupName: String,
        @Query("key") apiKey: String,
    ): SongsDto

    @GET("api/v1/positions")
    suspend fun getPositions(
        @Query("title") title: String,
        @Query("key") apiKey: String,
    ): PositionsDto

    // This GET method updated blog info in db.
    @GET("blog/update/")
    suspend fun updateBlog(
        @Query("key") apiKey: String,
    ): UpdateBlogResponseDto

    // This POST method send messages to server.
    @POST("issues")
    suspend fun reportIssue(
        @Body message: String
    ): ReportIssueResponseDto
}
