package com.photo.data.di

import com.photo.data.data_core.remote.interceptor.HeaderInterceptor
import com.photo.data.data_core.remote.interceptor.LogInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://dapi.kakao.com/v3/"

    @Provides
    @Singleton
    fun provideNetworkClient(
        httpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    @Singleton
    fun provideHttpClient(
        loggingInterceptor: com.photo.data.data_core.remote.interceptor.LogInterceptor,
        headerInterceptor: com.photo.data.data_core.remote.interceptor.HeaderInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(60L, TimeUnit.SECONDS)
        .connectTimeout(60L, TimeUnit.SECONDS)
        .writeTimeout(60L, TimeUnit.SECONDS)
        .also {
            it.addInterceptor(loggingInterceptor)
            it.addInterceptor(headerInterceptor)
        }.build()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): com.photo.data.data_core.remote.interceptor.LogInterceptor =
        com.photo.data.data_core.remote.interceptor.LogInterceptor()

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): com.photo.data.data_core.remote.interceptor.HeaderInterceptor =
        com.photo.data.data_core.remote.interceptor.HeaderInterceptor()

    @Provides
    @Singleton
    fun provideConverterFactory() : GsonConverterFactory = GsonConverterFactory.create(GsonBuilder().create())

}