package jp.mydns.kokoichi0206.sakamichiapp.di

import android.app.Application
import androidx.room.Room
import jp.mydns.kokoichi0206.sakamichiapp.common.Constants
import jp.mydns.kokoichi0206.sakamichiapp.data.remote.SakamichiApi
import jp.mydns.kokoichi0206.sakamichiapp.data.repository.SakamichiRepositoryImpl
import jp.mydns.kokoichi0206.sakamichiapp.domain.repository.SakamichiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.mydns.kokoichi0206.sakamichiapp.data.local.QuizRecordDatabase
import jp.mydns.kokoichi0206.sakamichiapp.data.remote.LoggingInterceptor
import jp.mydns.kokoichi0206.sakamichiapp.data.repository.QuizRecordRepositoryImpl
import jp.mydns.kokoichi0206.sakamichiapp.domain.repository.QuizRecordRepository
import jp.mydns.kokoichi0206.sakamichiapp.domain.usecase.quiz_record.*
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
            .addInterceptor(LoggingInterceptor())
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
    fun provideSakamichiRepository(api: SakamichiApi): SakamichiRepository {
        return SakamichiRepositoryImpl(api)
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
}
