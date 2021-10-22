package io.kokoichi.sample.sakamichiapp.webapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("members")
    fun getData(@Query("gn") groupName: String): Call<MemberInfos>

    @GET("songs")
    fun getAllSongs(@Query("gn") groupName: String): Call<MemberInfos>
}
