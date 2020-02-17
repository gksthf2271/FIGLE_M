package com.example.figle_m.Response.DTO

import android.util.Log
import com.google.gson.annotations.SerializedName

class DefenceDTO {
    private val TAG: String = javaClass.name

    @SerializedName("blockTry")
    var blockTry: Int? = null

    @SerializedName("blockSuccess")
    var blockSuccess: Int? = null

    @SerializedName("tackleTry")
    var tackleTry: Int? = null

    @SerializedName("tackleSuccess")
    var tackleSuccess: Int? = null

    override fun toString(): String {
        val string: String = "blockTry             : $blockTry \n" +
                "blockSuccess         : $blockSuccess \n" +
                "tackleTry            : $tackleTry \n" +
                "tackleSuccess        : $tackleSuccess \n"
        return string
    }

}