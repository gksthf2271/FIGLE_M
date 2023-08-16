package com.khs.figle_m.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.R

class ViewUtils{
    constructor()

    open fun addIndicator(
        context: Context,
        rootiew: View,
        rootRes: Int,
        imgRes: Int,
        count: Int,
        isOverlap: Boolean
    ) {
        val view = rootiew!!.findViewById(rootRes) as ConstraintLayout
        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view.removeAllViews()
        for (i in 1..count) {
            val imageView: ImageView =
                inflater.inflate(R.layout.cview_card, view, false) as ImageView
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView.background = context.getDrawable(imgRes)

            var layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            if (isOverlap) layoutParams.leftMargin = i * 40
            layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            imageView.layoutParams = layoutParams
            imageView.layoutParams.height = 30
            imageView.layoutParams.width = 30
            view.addView(imageView)
        }
    }
}