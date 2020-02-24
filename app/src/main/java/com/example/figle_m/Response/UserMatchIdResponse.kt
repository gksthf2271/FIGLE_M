package com.example.figle_m.Response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserMatchIdResponse: BaseResponse, Serializable {
    private val TAG : String = javaClass.name

    @SerializedName("")
    var accessIdList: List<String>? = null

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("------------------------------------------------\n")
        sb.append("------------------UserMatchIdResponse--------------------\n")
        for (accessId in accessIdList!!.iterator()) {
            sb.append("${accessId.toString()}")
        }
        sb.append("------------------------------------------------\n")

        return sb.toString()
    }
}