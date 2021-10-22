package io.kokoichi.sample.sakamichiapp.webapi

import android.util.Log
import io.kokoichi.sample.sakamichiapp.ui.formationPage.Position
import io.kokoichi.sample.sakamichiapp.ui.home.HomeViewModel
import io.kokoichi.sample.sakamichiapp.ui.util.Member
import io.kokoichi.sample.sakamichiapp.ui.util.birthdayStrength
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val TAG = "FormationApi"

fun getPositions(title: String, viewModel: HomeViewModel) {

    val BASE_URL = "https://kokoichi0206.mydns.jp/api/v1/"

    val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(ApiInterface::class.java)

    val retrofitData = retrofitBuilder.getPositions(title)

    retrofitData.enqueue(object : Callback<Positions?> {

        var tmps = mutableListOf<Position>()

        override fun onResponse(call: Call<Positions?>?, response: Response<Positions?>) {
            val responseBody = response.body()!!

            for (info in responseBody.positions) {
                val position = Position(
                    name_ja = info.member_name,
                    img_url = info.img_url,
                    position = info.position,
                    is_center = info.is_center,
                )

                tmps.add(position)
            }
            viewModel.setFormations(tmps)
            viewModel.finishLoading()
            Log.d(TAG, "positions $title download finished")
        }

        override fun onFailure(call: Call<Positions?>, t: Throwable) {

            viewModel.setFormations(tmps)
            viewModel.finishLoading()
            Log.d(TAG, "positions $title download failed")
        }
    })
}
