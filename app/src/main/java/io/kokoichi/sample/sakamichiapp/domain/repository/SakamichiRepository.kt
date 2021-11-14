package io.kokoichi.sample.sakamichiapp.domain.repository

import io.kokoichi.sample.sakamichiapp.data.remote.dto.BlogsDto
import io.kokoichi.sample.sakamichiapp.data.remote.dto.MembersDto
import io.kokoichi.sample.sakamichiapp.data.remote.dto.PositionsDto
import io.kokoichi.sample.sakamichiapp.data.remote.dto.SongsDto

/**
 * Repository Interface.
 */
interface SakamichiRepository {

    suspend fun getMembers(groupName: String): MembersDto

    suspend fun getBlogs(groupName: String): BlogsDto

    suspend fun getSongs(groupName: String): SongsDto

    suspend fun getPositions(title: String): PositionsDto
}