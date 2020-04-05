package com.khs.figle_m.koin

import org.koin.dsl.module

var testModule = module {
    single { testRepositoryImpl() }

    factory { testPresenter(get()) }
}