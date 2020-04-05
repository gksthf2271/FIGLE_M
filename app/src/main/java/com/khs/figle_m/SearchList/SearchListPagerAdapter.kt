package com.khs.figle_m.SearchList

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class SearchListPagerAdapter() : PagerAdapter() {
    private val TAG :String = javaClass.name
    lateinit var mContext : Context
    lateinit var mFirstView : View
    lateinit var mLastView : View

    constructor(context: Context, firstView : View, lastView : View) : this() {
        this.mContext = context
        this.mFirstView = firstView
        this.mLastView = lastView
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        var view: View? = null
        when (position) {
            0 -> {
                view = mFirstView
            }
            1 -> {
                view = mLastView
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