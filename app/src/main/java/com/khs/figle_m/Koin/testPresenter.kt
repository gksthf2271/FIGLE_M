package com.khs.figle_m.Koin

class testPresenter(private val repo: testRepository) {

    fun sayHello() = "${repo.giveHello()} from $this"
}