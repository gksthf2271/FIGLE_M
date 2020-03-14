package com.example.figle_m.utils

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

class DisplayUtils {
    constructor()

    open fun getDisplaySize(context: Context): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size
    }

}