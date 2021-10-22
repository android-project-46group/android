package io.kokoichi.sample.sakamichiapp.webapi

import android.util.Log
import io.kokoichi.sample.sakamichiapp.TAG
import io.kokoichi.sample.sakamichiapp.ui.home.HomeViewModel
import io.kokoichi.sample.sakamichiapp.ui.util.Member
import io.kokoichi.sample.sakamichiapp.ui.util.birthdayStrength
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Get MemberInfos From API
 * and Save to the passed viewModel (MAYBE: separate this process)
 */
fun GetMemberInfos(groupName: String, viewModel: HomeViewModel) {

    val BASE_URL = "https://kokoichi0206.mydns.jp/api/v1/"

    val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(ApiInterface::class.java)

    val retrofitData = retrofitBuilder.getData(groupName)

    retrofitData.enqueue(object : Callback<MemberInfos?> {

        var tmps = mutableListOf<Member>()

        override fun onResponse(call: Call<MemberInfos?>?, response: Response<MemberInfos?>) {
            val responseBody = response.body()!!

            for (info in responseBody.members) {
                val member = Member(
                    name_ja = info.user_name,
                    birthday = info?.birthday,
                    b_strength = birthdayStrength(info.birthday!!),
                    bloodType = info.blood_type!!,
                    generation = info.generation,
                    blog_url = info.blog_url,
                    imgUrl = info.img_url,
                )

                tmps.add(member)
            }
            viewModel.addMembers(tmps)
            viewModel.finishLoading()
            Log.d(TAG, "downloader finished")
        }

        override fun onFailure(call: Call<MemberInfos?>, t: Throwable) {

            for (i in 0..6) {
                val member = Member(
                    name_ja = "日村 勇紀",
                    birthday = "1972年5月14日",
                    b_strength = birthdayStrength("1972年5月14日"),
                    bloodType = "O型",
                    generation = "0期生",
                    blog_url = "null",
                    imgUrl = null,
                )
                tmps.add(member)
            }

            viewModel.addMembers(tmps)
            viewModel.finishLoading()
            Log.d(TAG, "downloader failed")
        }
    })
}