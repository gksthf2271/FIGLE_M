package com.example.figle_m.Data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SearchUser {
    private val retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.nexon.co.kr/fifaonline4/") // 도메인 주소
            .addConverterFactory(GsonConverterFactory.create()) // GSON을 사요아기 위해 ConverterFactory에 GSON 지정
            .build()

    val mRetrofitService = retrofit.create(RetrofitService::class.java)

    fun getService() : RetrofitService = mRetrofitService
}