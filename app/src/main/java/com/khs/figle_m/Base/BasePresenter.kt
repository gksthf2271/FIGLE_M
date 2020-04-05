package com.khs.figle_m.Base

interface BasePresenter<T> {
    fun takeView(view: T)
    fun dropView()
}