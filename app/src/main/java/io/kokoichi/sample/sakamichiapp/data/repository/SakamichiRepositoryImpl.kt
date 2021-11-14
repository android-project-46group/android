package io.kokoichi.sample.sakamichiapp.data.repository

import io.kokoichi.sample.sakamichiapp.data.remote.SakamichiApi
import io.kokoichi.sample.sakamichiapp.data.remote.dto.BlogsDto
import io.kokoichi.sample.sakamichiapp.data.remote.dto.MembersDto
import io.kokoichi.sample.sakamichiapp.data.remote.dto.PositionsDto
import io.kokoichi.sample.sakamichiapp.data.remote.dto.SongsDto
import io.kokoichi.sample.sakamichiapp.domain.repository.SakamichiRepository
import javax.inject.Inject

/**
 * Implementation of repository interface using actual API.
 */
class SakamichiRepositoryImpl @Inject constructor(
    private val api: SakamichiApi
) : SakamichiRepository {

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
}