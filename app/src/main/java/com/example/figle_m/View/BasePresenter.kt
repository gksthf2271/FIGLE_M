package com.example.figle_m.View

interface BasePresenter<T> {
    fun takeView(view: T)
    fun dropView()
}