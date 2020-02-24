package com.example.figle_m.Response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserResponse: BaseResponse, Serializable {
    private val TAG : String = javaClass.name

    @SerializedName("accessId")
    var accessId: String? = null

    @SerializedName("nickname")
    var nickname: String? = null

    @SerializedName("level")
    var level: String? = null

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("------------------------------------------------\n")
        sb.append("------------------UserResponse--------------------\n")
        sb.append(" accessId                    : $accessId\n")
        sb.append(" nickname                    : $nickname\n")
        sb.append(" level                       : $level\n")
        sb.append("------------------------------------------------\n")

        return sb.toString()
    }
}