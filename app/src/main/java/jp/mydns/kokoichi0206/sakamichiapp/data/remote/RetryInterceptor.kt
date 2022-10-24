package jp.mydns.kokoichi0206.sakamichiapp.data.remote

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Retry HTTP request.
 */
class RetryInterceptor : Interceptor {

    companion object {
        private const val MAX_RETRY_COUNT = 3
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)

        var counts = 0
        while (!response.isSuccessful && counts < MAX_RETRY_COUNT) {
            counts++

            response.close()
            response = chain.proceed(request)
        }
        return response
    }
}