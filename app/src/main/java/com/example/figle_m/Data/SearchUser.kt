package com.example.figle_m.Data

import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SearchUser {
    private val retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.nexon.co.kr/fifaonline4/") // 도메인 주소
            .addConverterFactory(GsonConverterFactory.create()) // GSON을 사요아기 위해 ConverterFactory에 GSON 지정
            .build()

    private val authorizationKey: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50X2lkIjoiMTI0MTUyOTI2NCIsImF1dGhfaWQiOiIyIiwidG9rZW5fdHlwZSI6IkFjY2Vzc1Rva2VuIiwic2VydmljZV9pZCI6IjQzMDAxMTQ4MSIsIlgtQXBwLVJhdGUtTGltaXQiOiIyMDAwMDoxMCIsIm5iZiI6MTU3NjkxNjU0MSwiZXhwIjoxNjM5OTg4NTQxLCJpYXQiOjE1NzY5MTY1NDF9.emF4Bd9O7zbC1giC4s3IrZ4S8Oax6-5IhDe3nZ0gCi4"
    private val builder: Request.Builder = Request.Builder()
        .addHeader("Authorization", authorizationKey)
    val mRetrofitService = retrofit.create(RetrofitService::class.java)

    fun getService() : RetrofitService = mRetrofitService
}