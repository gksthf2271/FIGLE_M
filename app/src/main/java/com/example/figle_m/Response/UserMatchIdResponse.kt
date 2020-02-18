package com.example.figle_m.Response

import com.google.gson.annotations.SerializedName

class UserMatchIdResponse {
    private val TAG : String = javaClass.name

    @SerializedName("")
    var accessIdList: List<String>? = null

    override fun toString(): String {
        val string: String = "accessIdList             : $accessIdList, "
        return string
    }
}