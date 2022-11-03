package jp.mydns.kokoichi0206.sakamichiapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.mydns.kokoichi0206.common.Constants
import jp.mydns.kokoichi0206.data.local.QuizRecordDatabase
import jp.mydns.kokoichi0206.sakamichiapp.data.remote.MockSakamichiApi
import jp.mydns.kokoichi0206.data.remote.SakamichiApi
import jp.mydns.kokoichi0206.data.repository.QuizRecordRepository
import jp.mydns.kokoichi0206.data.repository.QuizRecordRepositoryImpl
import jp.mydns.kokoichi0206.data.repository.SakamichiRepository
import jp.mydns.kokoichi0206.data.repository.SakamichiRepositoryImpl
import jp.mydns.kokoichi0206.domain.usecase.quiz_record.RecordUseCases
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
    fun provideSakamichiApi(): jp.mydns.kokoichi0206.data.remote.SakamichiApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(jp.mydns.kokoichi0206.common.Constants.BASE_URL)
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

        val delegate = mockRetrofit.create(jp.mydns.kokoichi0206.data.remote.SakamichiApi::class.java)

        return MockSakamichiApi(delegate)
    }

    @Provides
    @Singleton
    fun provideSakamichiRepository(api: jp.mydns.kokoichi0206.data.remote.SakamichiApi): SakamichiRepository {
        return jp.mydns.kokoichi0206.data.repository.SakamichiRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideQuizRecordDatabase(app: Application): jp.mydns.kokoichi0206.data.local.QuizRecordDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            jp.mydns.kokoichi0206.data.local.QuizRecordDatabase::class.java,
        ).build()
    }

    @Provides
    @Singleton
    fun provideQuizRecordRepository(db: jp.mydns.kokoichi0206.data.local.QuizRecordDatabase): QuizRecordRepository {
        return jp.mydns.kokoichi0206.data.repository.QuizRecordRepositoryImpl(db.quizRecordDao)
    }

    @Provides
    @Singleton
    fun provideQuizRecordUseCases(repository: QuizRecordRepository): RecordUseCases {
        return usecase.quiz_record.RecordUseCases(
            getRecords = usecase.quiz_record.GetRecordsUseCase(repository),
            getRecord = usecase.quiz_record.GetRecordUseCase(repository),
            getAccuracy = usecase.quiz_record.GetAccuracyRateByGroupUseCase(repository),
            insertRecord = usecase.quiz_record.InsertRecordUseCase(repository),
        )
    }
}
