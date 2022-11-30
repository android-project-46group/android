package jp.mydns.kokoichi0206.data.di

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
import jp.mydns.kokoichi0206.data.remote.createSakamichiApi
import jp.mydns.kokoichi0206.data.repository.*
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
        return createSakamichiApi(Constants.BASE_URL)
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

    // Database
    @Provides
    @Singleton
    fun provideMembersDatabase(app: Application): MembersDatabase {
        return Room.databaseBuilder(
            app,
            MembersDatabase::class.java,
            MembersDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMembersRepository(db: MembersDatabase): MembersRepository {
        return MembersRepositoryImpl(db.membersDao)
    }
}
