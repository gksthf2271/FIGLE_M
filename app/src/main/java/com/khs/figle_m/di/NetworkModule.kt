package com.khs.figle_m.di

import com.khs.data.nexon_api.NexonAPIService
import com.khs.data.nexon_api.NexonCDNService
import com.khs.data.nexon_api.NexonDataCenterService
import com.khs.data.nexon_api.NexonStaticService
import com.khs.figle_m.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideAuthInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val initialRequest = chain.request()

            val newUrl = initialRequest.url.newBuilder()
                .build()

            val newRequest = initialRequest.newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @NexonAPIRetrofit
    fun provideNexonAPIRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.nexon.co.kr/fifaonline4/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @NexonCDNRetrofit
    fun provideNexonCDNRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fo4.dn.nexoncdn.co.kr/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @NexonStaticRetrofit
    fun provideNexonStaticRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://static.api.nexon.co.kr/fifaonline4/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @NexonDataCenterRetrofit
    fun provideNexonDataCenterRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://fifaonline4.nexon.com/DataCenter/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun nexonAPIService(@NexonAPIRetrofit retrofit: Retrofit): NexonAPIService {
        return retrofit.create(NexonAPIService::class.java)
    }

    @Provides
    @Singleton
    fun nexonCDNService(@NexonCDNRetrofit retrofit: Retrofit): NexonCDNService {
        return retrofit.create(NexonCDNService::class.java)
    }

    @Provides
    @Singleton
    fun nexonStaticService(@NexonStaticRetrofit retrofit: Retrofit): NexonStaticService {
        return retrofit.create(NexonStaticService::class.java)
    }

    @Provides
    @Singleton
    fun nexonDataCenterService(@NexonDataCenterRetrofit retrofit: Retrofit): NexonDataCenterService {
        return retrofit.create(NexonDataCenterService::class.java)
    }

    @Provides
    fun apiKey() : String {
        return BuildConfig.NEXON_API_KEY
    }
}