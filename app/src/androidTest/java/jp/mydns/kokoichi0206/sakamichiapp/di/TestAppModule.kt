package jp.mydns.kokoichi0206.sakamichiapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.mydns.kokoichi0206.sakamichiapp.common.Constants
import jp.mydns.kokoichi0206.sakamichiapp.data.local.QuizRecordDatabase
import jp.mydns.kokoichi0206.sakamichiapp.data.remote.MockSakamichiApi
import jp.mydns.kokoichi0206.sakamichiapp.data.remote.SakamichiApi
import jp.mydns.kokoichi0206.sakamichiapp.data.repository.QuizRecordRepositoryImpl
import jp.mydns.kokoichi0206.sakamichiapp.data.repository.SakamichiRepositoryImpl
import jp.mydns.kokoichi0206.sakamichiapp.domain.repository.QuizRecordRepository
import jp.mydns.kokoichi0206.sakamichiapp.domain.repository.SakamichiRepository
import jp.mydns.kokoichi0206.sakamichiapp.domain.usecase.quiz_record.*
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
