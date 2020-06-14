package com.khs.figle_m.SearchList.Common

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager



class CustomPagerAdapter() : PagerAdapter() {
    private val TAG :String = javaClass.name
    lateinit var mContext : Context
    var mFirstView : View? = null
    var mLastView : View? = null
    var mEmptyView : View? = null

    constructor(context: Context, firstView : View, lastView : View) : this() {
        this.mContext = context
        this.mFirstView = firstView
        this.mLastView = lastView
    }

    constructor(context: Context, firstView : View, lastView : View, emptyView: View) : this() {
        this.mContext = context
        this.mFirstView = firstView
        this.mLastView = lastView
        this.mEmptyView = emptyView
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        var view: View? = null
        when (position) {
            0 -> {
                if(mFirstView == null) mFirstView = mEmptyView
                view = mFirstView
            }
            1 -> {
                if(mLastView == null) mLastView = mEmptyView
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

    override fun destroyItem(collection: View, position: Int, view: Any) {
        (collection as ViewPager).removeView(view as View)
    }
}