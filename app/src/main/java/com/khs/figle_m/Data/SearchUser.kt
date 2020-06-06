package com.khs.figle_m.Data

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object SearchUser {

    var okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    private val api =
        Retrofit.Builder()
            .baseUrl("https://api.nexon.co.kr/fifaonline4/") // 도메인 주소
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val cdn =
        Retrofit.Builder()
            .baseUrl("https://fo4.dn.nexoncdn.co.kr/") // 도메인 주소
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val playerNameApi =
        Retrofit.Builder()
            .baseUrl("https://static.api.nexon.co.kr/fifaonline4/") // 도메인 주소
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val dataCenterCrawlingUrl =
        Retrofit.Builder()
            .baseUrl("http://fifaonline4.nexon.com/DataCenter/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    private val authorizationKey: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50X2lkIjoiMTI0MTUyOTI2NCIsImF1dGhfaWQiOiIyIiwidG9rZW5fdHlwZSI6IkFjY2Vzc1Rva2VuIiwic2VydmljZV9pZCI6IjQzMDAxMTQ4MSIsIlgtQXBwLVJhdGUtTGltaXQiOiIyMDAwMDoxMCIsIm5iZiI6MTU3NjkxNjU0MSwiZXhwIjoxNjM5OTg4NTQxLCJpYXQiOjE1NzY5MTY1NDF9.emF4Bd9O7zbC1giC4s3IrZ4S8Oax6-5IhDe3nZ0gCi4"
    private val builder: Request.Builder = Request.Builder()
        .addHeader("Authorization", authorizationKey)

    val mApiRetrofitService = api.create(RetrofitService::class.java)
    val mCDNRetrofitService = cdn.create(RetrofitService::class.java)
    val mPlayerNameApiService = playerNameApi.create(RetrofitService::class.java)
    val mCrawlingService = dataCenterCrawlingUrl.create(RetrofitService::class.java)

    fun getApiService() : RetrofitService = mApiRetrofitService

    fun getServiceImage() : RetrofitService = mCDNRetrofitService

    fun getPlayerNameApiService() : RetrofitService = mPlayerNameApiService

    fun getCrawlingService() : RetrofitService = mCrawlingService
}