package com.khs.figle_m.utils

import java.text.NumberFormat

open class StringUtils() {
    open fun parseValue(value: Long) : String{
        return NumberFormat.getInstance().format(value)
    }
}