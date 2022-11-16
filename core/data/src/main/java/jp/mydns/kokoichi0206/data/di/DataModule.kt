package jp.mydns.kokoichi0206.data.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.mydns.kokoichi0206.common.BuildConfigWrapper
import jp.mydns.kokoichi0206.common.Constants
import jp.mydns.kokoichi0206.common.interceptor.AddHeaderInterceptor
import jp.mydns.kokoichi0206.common.interceptor.LoggingInterceptor
import jp.mydns.kokoichi0206.common.interceptor.RetryInterceptor
import jp.mydns.kokoichi0206.data.local.QuizRecordDatabase
import jp.mydns.kokoichi0206.data.remote.SakamichiApi
import jp.mydns.kokoichi0206.data.repository.QuizRecordRepository
import jp.mydns.kokoichi0206.data.repository.QuizRecordRepositoryImpl
import jp.mydns.kokoichi0206.data.repository.SakamichiRepository
import jp.mydns.kokoichi0206.data.repository.SakamichiRepositoryImpl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Dependency injection with Hilt.
 */
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    // API
    @Provides
    @Singleton
    fun provideSakamichiApi(): SakamichiApi {
        val okHttpClient = OkHttpClient.Builder()
            // サーバー側の設定か、なぜか指定が必要！
            .connectTimeout(777, TimeUnit.MILLISECONDS)
            .addInterceptor(AddHeaderInterceptor())
            .addInterceptor(LoggingInterceptor())
            .addInterceptor(RetryInterceptor())
            .build()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SakamichiApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSakamichiRepository(
        api: SakamichiApi,
        buildConfig: BuildConfigWrapper,
    ): SakamichiRepository {
        return SakamichiRepositoryImpl(api, buildConfig)
    }

    // Database
    @Provides
    @Singleton
    fun provideQuizRecordDatabase(app: Application): QuizRecordDatabase {
        return Room.databaseBuilder(
            app,
            QuizRecordDatabase::class.java,
            QuizRecordDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideQuizRecordRepository(db: QuizRecordDatabase): QuizRecordRepository {
        return QuizRecordRepositoryImpl(db.quizRecordDao)
    }
}
