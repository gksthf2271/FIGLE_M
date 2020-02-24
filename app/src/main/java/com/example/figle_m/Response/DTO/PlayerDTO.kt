package com.example.figle_m.Response.DTO

import android.util.Log
import com.google.gson.annotations.SerializedName

class PlayerDTO {
    private val TAG : String = javaClass.name

    @SerializedName("spId")
    var spId: Int? = null

    @SerializedName("spPosition")
    var spPosition: Int? = null

    @SerializedName("spGrade")
    var spGrade: Int? = null

    @SerializedName("status")
    var status: StatusDTO? = null

    override fun toString(): String {
        val string: String = "\nspId             : $spId \n" +
                        "spPosition       : $spPosition \n" +
                        "spGrade          : $spGrade \n " +
                        "status           : ${status.toString()} \n"
        return string
    }
}