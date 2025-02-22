package com.khs.figle_m.common.util

import java.text.SimpleDateFormat

object DateUtils {
    private object TIME_MAXIMUM {
        val ONE_MIN = 60 * 1000
        val ONE_HOUR = 60 * ONE_MIN
        val ONE_DAY = 24 * ONE_HOUR
        val ONE_MONTH = 30 * ONE_DAY
        val ONE_YEARS = 12 * ONE_MONTH
    }

    fun formatTimeString(regTime: Long): String {
        val curTime = System.currentTimeMillis()
        var diffTime = (curTime - regTime)

        if (diffTime < TIME_MAXIMUM.ONE_MIN) {
            return "방금 전"
        }
        if ((diffTime / TIME_MAXIMUM.ONE_MIN.toLong()) < 60) {
            return (diffTime / TIME_MAXIMUM.ONE_MIN.toLong()).toString() + "분 전"
        }
        if ((diffTime / TIME_MAXIMUM.ONE_HOUR.toLong()) < 24) {
            return (diffTime / TIME_MAXIMUM.ONE_HOUR.toLong()).toString() + "시간 전"
        }
        return (diffTime / TIME_MAXIMUM.ONE_DAY.toLong()).toString() + "일 전"
    }

    fun getDate(date: String): Long {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd-HH:mm:ss")
        val result = simpleDateFormat.parse(date.replace("T","-"))
        return result.time
    }

    fun getDateforImg(date: Long): String {
        val simpleDateFormat = SimpleDateFormat("yyyyMMddHHmm")
        val result = simpleDateFormat.format(date)
        return result
    }
}