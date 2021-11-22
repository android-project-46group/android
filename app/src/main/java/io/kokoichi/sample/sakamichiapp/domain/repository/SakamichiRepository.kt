package io.kokoichi.sample.sakamichiapp.domain.repository

import io.kokoichi.sample.sakamichiapp.data.remote.dto.*

/**
 * Repository Interface.
 */
interface SakamichiRepository {

    suspend fun getMembers(groupName: String): MembersDto

    suspend fun getBlogs(groupName: String): BlogsDto

    suspend fun getSongs(groupName: String): SongsDto

    suspend fun getPositions(title: String): PositionsDto

    suspend fun updateBlog(): UpdateBlogResponseDto

    suspend fun reportIssue(message: String): ReportIssueResponseDto
}
