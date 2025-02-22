package com.khs.figle_m.common.util

import java.text.NumberFormat

object StringUtils {
    fun parseValue(value: Long) : String{
        return NumberFormat.getInstance().format(value)
    }
}