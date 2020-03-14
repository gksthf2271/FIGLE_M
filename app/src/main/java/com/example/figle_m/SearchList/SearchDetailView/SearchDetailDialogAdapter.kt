package com.example.figle_m.SearchList.SearchDetailView

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import com.example.figle_m.R

class SearchDetailDialogAdapter() : PagerAdapter() {

    lateinit var mContext: Context
    var resId = 0

    constructor(context: Context) : this() {
        this.mContext = context
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(mContext)
        when (position) {
            0 -> resId = R.layout.fragment_search_detail
            1 -> resId = R.layout.fragment_search_detail
        }
        val layout = inflater.inflate(resId, collection, false) as ViewGroup
        collection.addView(layout)
        return layout
    }

    override fun getCount(): Int {
        return 2
    }

    override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
        return arg0 === arg1
    }
}