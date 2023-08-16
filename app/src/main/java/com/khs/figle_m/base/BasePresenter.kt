package com.khs.figle_m.base

interface BasePresenter<T> {
    fun takeView(view: T)
    fun dropView()
}