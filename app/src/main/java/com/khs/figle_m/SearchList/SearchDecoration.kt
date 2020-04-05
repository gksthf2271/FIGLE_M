package com.khs.figle_m.SearchList

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SearchDecoration(divHeight: Int) : RecyclerView.ItemDecoration() {
    var mDivHeight:Int
    init{
        mDivHeight = divHeight
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = mDivHeight
    }

}