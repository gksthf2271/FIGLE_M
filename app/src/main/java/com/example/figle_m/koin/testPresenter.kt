package com.example.figle_m.koin

class testPresenter(private val repo: testRepository) {

    fun sayHello() = "${repo.giveHello()} from $this"
}