package jp.mydns.kokoichi0206.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

/**
 * Set Header for Retrofit request.
 */
class AddHeaderInterceptor : Interceptor {

    companion object {
        private const val ACCEPT_LANGUAGE_HEADER = "Accept-Language"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Add Accept-Language header according to device language (in settings app).
        val newReq = request.newBuilder()
            .header(ACCEPT_LANGUAGE_HEADER, Locale.getDefault().toString())
            .build()

        return chain.proceed(newReq)
    }
}
