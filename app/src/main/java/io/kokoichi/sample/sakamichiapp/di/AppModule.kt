package io.kokoichi.sample.sakamichiapp.di

import android.app.Application
import androidx.room.Room
import io.kokoichi.sample.sakamichiapp.common.Constants
import io.kokoichi.sample.sakamichiapp.data.remote.SakamichiApi
import io.kokoichi.sample.sakamichiapp.data.repository.SakamichiRepositoryImpl
import io.kokoichi.sample.sakamichiapp.domain.repository.SakamichiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.kokoichi.sample.sakamichiapp.data.local.QuizRecordDatabase
import io.kokoichi.sample.sakamichiapp.data.repository.QuizRecordRepositoryImpl
import io.kokoichi.sample.sakamichiapp.domain.repository.QuizRecordRepository
import io.kokoichi.sample.sakamichiapp.domain.usecase.quiz_record.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
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
