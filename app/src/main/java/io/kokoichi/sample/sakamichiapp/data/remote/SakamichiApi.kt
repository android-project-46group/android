package io.kokoichi.sample.sakamichiapp.data.remote

import io.kokoichi.sample.sakamichiapp.BuildConfig
import io.kokoichi.sample.sakamichiapp.data.remote.dto.MembersDto
import io.kokoichi.sample.sakamichiapp.data.remote.dto.PositionsDto
import io.kokoichi.sample.sakamichiapp.data.remote.dto.SongsDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface for accessing the APIs with Retrofit.
 */
interface SakamichiApi {

    @GET("api/v1/members")
    suspend fun getMembers(
        @Query("gn") groupName: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): MembersDto

    @GET("api/v1/songs")
    suspend fun getSongs(
        @Query("gn") groupName: String,
       @Query("key") apiKey: String = BuildConfig.API_KEY
    ): SongsDto

    @GET("api/v1/positions")
    suspend fun getPositions(
        @Query("title") title: String,
       @Query("key") apiKey: String = BuildConfig.API_KEY
    ): PositionsDto
}