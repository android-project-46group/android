package jp.mydns.kokoichi0206.data.remote

import jp.mydns.kokoichi0206.common.interceptor.AddHeaderInterceptor
import jp.mydns.kokoichi0206.common.interceptor.LoggingInterceptor
import jp.mydns.kokoichi0206.common.interceptor.RetryInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Create a SakamichiApi Impl.
 */
fun createSakamichiApi(
    baseURL: String,
): SakamichiApi {
    val okHttpClient = OkHttpClient.Builder()
        // サーバー側の設定か、なぜか指定が必要！
        .connectTimeout(777, TimeUnit.MILLISECONDS)
        .addInterceptor(AddHeaderInterceptor())
        .addInterceptor(LoggingInterceptor())
        .addInterceptor(RetryInterceptor())
        .build()
    return Retrofit.Builder()
        .baseUrl(baseURL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create().failOnUnknown())
        .build()
        .create(SakamichiApi::class.java)
}
