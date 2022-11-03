package jp.mydns.kokoichi0206.sakamichiapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.mydns.kokoichi0206.common.BuildConfigWrapper
import jp.mydns.kokoichi0206.common.Constants
import jp.mydns.kokoichi0206.data.local.QuizRecordDatabase
import jp.mydns.kokoichi0206.data.remote.AddHeaderInterceptor
import jp.mydns.kokoichi0206.data.remote.LoggingInterceptor
import jp.mydns.kokoichi0206.data.remote.RetryInterceptor
import jp.mydns.kokoichi0206.data.remote.SakamichiApi
import jp.mydns.kokoichi0206.data.repository.QuizRecordRepository
import jp.mydns.kokoichi0206.data.repository.QuizRecordRepositoryImpl
import jp.mydns.kokoichi0206.data.repository.SakamichiRepository
import jp.mydns.kokoichi0206.data.repository.SakamichiRepositoryImpl
import jp.mydns.kokoichi0206.domain.usecase.quiz_record.*
import jp.mydns.kokoichi0206.sakamichiapp.BuildConfig
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
object AppModule {

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

    @Provides
    @Singleton
    fun provideQuizRecordUseCases(repository: QuizRecordRepository): RecordUseCases {
        return RecordUseCases(
            getRecords = GetRecordsUseCase(repository),
            getRecord = GetRecordUseCase(repository),
            getAccuracy = GetAccuracyRateByGroupUseCase(repository),
            insertRecord = InsertRecordUseCase(repository),
        )
    }

    @Provides
    @Singleton
    fun provideBuildConfigWrapper() : BuildConfigWrapper {
        return BuildConfigWrapper(
            API_KEY = BuildConfig.API_KEY
        )
    }
}
