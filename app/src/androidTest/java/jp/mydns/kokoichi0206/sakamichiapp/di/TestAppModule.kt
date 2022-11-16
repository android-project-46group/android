package jp.mydns.kokoichi0206.sakamichiapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.mydns.kokoichi0206.common.BuildConfigWrapper
import jp.mydns.kokoichi0206.common.Constants
import jp.mydns.kokoichi0206.data.local.MembersDatabase
import jp.mydns.kokoichi0206.data.local.QuizRecordDatabase
import jp.mydns.kokoichi0206.data.remote.SakamichiApi
import jp.mydns.kokoichi0206.data.repository.*
import jp.mydns.kokoichi0206.domain.usecase.quiz_record.*
import jp.mydns.kokoichi0206.sakamichiapp.data.remote.MockSakamichiApi
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
    fun provideSakamichiRepository(api: SakamichiApi, config: BuildConfigWrapper): SakamichiRepository {
        return SakamichiRepositoryImpl(api, config)
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

    // Database
    @Provides
    @Singleton
    fun provideMembersDatabase(app: Application): MembersDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            MembersDatabase::class.java,
        ).build()
    }

    @Provides
    @Singleton
    fun provideMembersRepository(db: MembersDatabase): MembersRepository {
        return MembersRepositoryImpl(db.membersDao)
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
    fun provideBuildConfigWrapper(): BuildConfigWrapper {
        return BuildConfigWrapper(
            API_KEY = "test_api_key",
            VERSION = "1.0.3",
            APP_NAME = "this app"
        )
    }
}
