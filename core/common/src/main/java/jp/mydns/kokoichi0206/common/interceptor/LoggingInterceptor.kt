package jp.mydns.kokoichi0206.common.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

/**
 * Logger for HTTP request.
 */
class LoggingInterceptor : Interceptor {

    private val tag = "LoggingInterceptor"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val t1 = System.nanoTime()
        Timber.tag(tag).d("${request.url}, ${request.url}")

        val response: Response = chain.proceed(request)
        val t2 = System.nanoTime()
        Timber.tag(tag).d("${response.request.url}, ${(t2 - t1) / 1e6}, ${response.headers}")
        Timber.tag(tag).d("${(t2 - t1) / 1e6} m sec")

        return response
    }
}
