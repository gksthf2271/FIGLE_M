package com.khs.figle_m.Utils

import android.content.Context
import android.graphics.Point
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.view.WindowManager


object DisplayUtils {
    fun getDisplaySize(context: Context): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size
    }

//    ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length()
//    , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // Style

//    ssb.setSpan(new ForegroundColorSpan(Color.BLUE), 0, title.length()
//    , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // Color

//    ssb.setSpan(new AbsoluteSizeSpan(50), 0, title.length()
//    , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // Size

//    ssb.setSpan(new UnderLineSpan(), 0, title.length()
//    , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // UnderLine

    fun updateTextSize(fullStr: String, targetStr: String) : SpannableStringBuilder{
        val ssb = SpannableStringBuilder(fullStr)
        ssb.setSpan(
            AbsoluteSizeSpan (30), fullStr.length - targetStr.length, fullStr.length
            , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return ssb
    }
}