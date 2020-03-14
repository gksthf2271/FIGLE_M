package com.example.figle_m.SearchList.SearchDetailView

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.figle_m.Response.MatchDetailResponse

class SearchDetailDialogAdapter() : PagerAdapter() {

    lateinit var mContext: Context
    lateinit var mMatchDetailResponse: MatchDetailResponse
    var resId = 0

    constructor(context: Context, matchDetailResponse: MatchDetailResponse) : this() {
        this.mContext = context
        this.mMatchDetailResponse = matchDetailResponse
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        var view: View? = null
        when (position) {
            0 -> {
                view = SearchDetailDialogPageView(mContext)
                (view as SearchDetailDialogPageView).updateMatchInfo(mMatchDetailResponse)
            }
            1 -> {
                view = SearchDetailDialogPageView(mContext)
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