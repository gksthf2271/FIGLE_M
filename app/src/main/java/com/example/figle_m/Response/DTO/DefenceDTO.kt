package com.example.figle_m.Response.DTO

import android.util.Log
import com.google.gson.annotations.SerializedName
import android.system.Os.link



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
        val sb = StringBuilder()
        sb.append("------------------------------------------------\n")
        sb.append("------------------DefenceDTO--------------------\n")
        sb.append(" blockTry                     : $blockTry\n")
        sb.append(" blockSuccess                 : $blockSuccess\n")
        sb.append(" tackleTry                    : $tackleTry\n")
        sb.append(" tackleSuccess                : $tackleSuccess\n")
        sb.append("------------------------------------------------\n")

        return sb.toString()
    }

}