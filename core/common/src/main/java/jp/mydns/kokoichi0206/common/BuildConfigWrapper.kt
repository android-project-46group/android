package jp.mydns.kokoichi0206.common

/**
 * Share app BuildConfig by hilt.
 */
data class BuildConfigWrapper(
    val API_KEY: String,
    val VERSION: String,
    val APP_NAME: String,
)
