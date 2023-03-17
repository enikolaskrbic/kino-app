package com.app.kinogame.hilt

import android.app.Application
import android.content.Context
import com.app.kinogame.data.api.KinoApi
import com.app.kinogame.data.api.KinoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    companion object {
        const val TIMEOUT_TIME = 15L
    }

    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return " https://api.opap.gr/"
    }


    @Provides
    @Singleton
    fun provideApiInterceptor(context: Context): KinoInterceptor {
        return KinoInterceptor(context)
    }


    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }


    private fun getRetrofit(okHttpClient: OkHttpClient, baseUrl: String, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(kinoInterceptor: KinoInterceptor): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(kinoInterceptor)
        clientBuilder.connectTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
        clientBuilder.readTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
        return clientBuilder.build()
    }



    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }


    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson,url: String, okHttpClient: OkHttpClient): Retrofit = getRetrofit(okHttpClient, url, gson)


    @Provides
    @Singleton
    fun provideKinoApiService(retrofit: Retrofit): KinoApi {
        return retrofit.create(KinoApi::class.java)
    }
}
