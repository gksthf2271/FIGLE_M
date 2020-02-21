package com.example.figle_m.Response

import com.google.gson.annotations.SerializedName

class UserResponse: BaseResponse {
    private val TAG : String = javaClass.name

    @SerializedName("accessId")
    var accessId: String? = null

    @SerializedName("nickname")
    var nickname: String? = null

    @SerializedName("level")
    var level: String? = null

    override fun toString(): String {
        val string: String = "accessId             : $accessId, " +
                "nickname             : $nickname, " +
                "level                   : $level, "
        return string
    }
}