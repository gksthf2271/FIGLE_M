package com.khs.figle_m.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NexonAPIRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NexonCDNRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NexonStaticRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NexonDataCenterRetrofit