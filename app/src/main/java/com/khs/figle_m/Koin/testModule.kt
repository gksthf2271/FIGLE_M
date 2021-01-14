package com.khs.figle_m.Koin

import org.koin.dsl.module

var testModule = module {
    single { testRepositoryImpl() }

    factory { testPresenter(get()) }
}