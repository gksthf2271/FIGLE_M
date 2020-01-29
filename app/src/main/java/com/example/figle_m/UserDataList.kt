package com.example.figle_m

object UserDataList{
    fun getUserDataList(): List<User>{
        return listOf(
            User("testA", "10"),
            User("testB", "12"),
            User("testC", "8")
        )
    }
}