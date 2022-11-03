package jp.mydns.kokoichi0206.data.repository

import jp.mydns.kokoichi0206.data.remote.SakamichiApi
import jp.mydns.kokoichi0206.data.remote.dto.*
import jp.mydns.kokoichi0206.sakamichiapp.data.remote.dto.*
import repository.SakamichiRepository
import javax.inject.Inject

/**
 * Implementation of repository interface using actual API.
 */
class SakamichiRepositoryImpl @Inject constructor(
    private val api: SakamichiApi
) : repository.SakamichiRepository {

    override suspend fun getMembers(groupName: String): MembersDto {
        return api.getMembers(groupName)
    }

    override suspend fun getBlogs(groupName: String): BlogsDto {
        return api.getBlogs(groupName)
    }

    override suspend fun getSongs(groupName: String): SongsDto {
        return api.getSongs(groupName)
    }

    override suspend fun getPositions(title: String): PositionsDto {
        return api.getPositions(title)
    }

    override suspend fun updateBlog(): UpdateBlogResponseDto {
        return api.updateBlog()
    }

    override suspend fun reportIssue(message: String): ReportIssueResponseDto {
        return api.reportIssue(message)
    }
}
