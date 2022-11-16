package jp.mydns.kokoichi0206.sakamichiapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.mydns.kokoichi0206.common.BuildConfigWrapper
import jp.mydns.kokoichi0206.data.repository.QuizRecordRepository
import jp.mydns.kokoichi0206.domain.usecase.quiz_record.*
import jp.mydns.kokoichi0206.sakamichiapp.BuildConfig
import javax.inject.Singleton

/**
 * Dependency injection with Hilt.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
    fun provideBuildConfigWrapper(): BuildConfigWrapper {
        return BuildConfigWrapper(
            API_KEY = BuildConfig.API_KEY,
            VERSION = BuildConfig.VERSION_NAME,
            APP_NAME = BuildConfig.APP_NAME,
        )
    }
}
