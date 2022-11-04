package jp.mydns.kokoichi0206.data.repository

import jp.mydns.kokoichi0206.common.BuildConfigWrapper
import jp.mydns.kokoichi0206.data.remote.SakamichiApi
import jp.mydns.kokoichi0206.data.remote.dto.*
import javax.inject.Inject

/**
 * Implementation of repository interface using actual API.
 */
class SakamichiRepositoryImpl @Inject constructor(
    private val api: SakamichiApi,
    private val buildConfig: BuildConfigWrapper,
) : SakamichiRepository {

    override suspend fun getMembers(groupName: String): MembersDto {
        return api.getMembers(groupName, buildConfig.API_KEY)
    }

    override suspend fun getBlogs(groupName: String): BlogsDto {
        return api.getBlogs(groupName, buildConfig.API_KEY)
    }

    override suspend fun getSongs(groupName: String): SongsDto {
        return api.getSongs(groupName, buildConfig.API_KEY)
    }

    override suspend fun getPositions(title: String): PositionsDto {
        return api.getPositions(title, buildConfig.API_KEY)
    }

    override suspend fun updateBlog(): UpdateBlogResponseDto {
        return api.updateBlog(buildConfig.API_KEY)
    }

    override suspend fun reportIssue(message: String): ReportIssueResponseDto {
        return api.reportIssue(message)
    }
}
