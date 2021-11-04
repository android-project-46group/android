package io.kokoichi.sample.sakamichiapp.di

import io.kokoichi.sample.sakamichiapp.common.Constants
import io.kokoichi.sample.sakamichiapp.data.remote.SakamichiApi
import io.kokoichi.sample.sakamichiapp.data.repository.SakamichiRepositoryImpl
import io.kokoichi.sample.sakamichiapp.domain.repository.SakamichiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dependency injection with Hilt.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
}