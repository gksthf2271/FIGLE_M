package com.example.figle_m.SearchList

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class SearchListPagerAdapter() : PagerAdapter() {
    lateinit var mContext : Context
    lateinit var mView : View

    constructor(context: Context, view : View) : this() {
        this.mContext = context
        this.mView = view
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        var view: View? = null
        when (position) {
            0 -> {
                view = mView
            }
            1 -> {
                view = mView
            }
        }
        collection.addView(view)
        return view!!
    }

    override fun getCount(): Int {
        return 2
    }

    override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
        return arg0 === arg1
    }
}