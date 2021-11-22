package io.kokoichi.sample.sakamichiapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.kokoichi.sample.sakamichiapp.common.Constants
import io.kokoichi.sample.sakamichiapp.data.local.QuizRecordDatabase
import io.kokoichi.sample.sakamichiapp.data.remote.MockSakamichiApi
import io.kokoichi.sample.sakamichiapp.data.remote.SakamichiApi
import io.kokoichi.sample.sakamichiapp.data.repository.QuizRecordRepositoryImpl
import io.kokoichi.sample.sakamichiapp.data.repository.SakamichiRepositoryImpl
import io.kokoichi.sample.sakamichiapp.domain.repository.QuizRecordRepository
import io.kokoichi.sample.sakamichiapp.domain.repository.SakamichiRepository
import io.kokoichi.sample.sakamichiapp.domain.usecase.quiz_record.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideSakamichiApi(): SakamichiApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val behavior = NetworkBehavior.create()

        behavior.setDelay(0, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(0)
        behavior.setErrorPercent(0)

        val mockRetrofit = MockRetrofit.Builder(retrofit)
            .networkBehavior(behavior)
            .build()

        val delegate = mockRetrofit.create(SakamichiApi::class.java)

        return MockSakamichiApi(delegate)
    }

    @Provides
    @Singleton
    fun provideSakamichiRepository(api: SakamichiApi): SakamichiRepository {
        return SakamichiRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideQuizRecordDatabase(app: Application): QuizRecordDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            QuizRecordDatabase::class.java,
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
