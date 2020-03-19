package com.example.figle_m.SearchList.SearchDetailView

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.SearchList.SearchDetailView.customView.SearchDetailDialogGameResultView
import com.example.figle_m.SearchList.SearchDetailView.customView.SearchDetailDialogPlayerInfoView

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
                view =
                    SearchDetailDialogGameResultView(
                        mContext
                    )
                (view as SearchDetailDialogGameResultView).updateMatchInfo(mMatchDetailResponse)
            }
            1 -> {
                view =
                    SearchDetailDialogPlayerInfoView(
                        mContext
                    )
                (view as SearchDetailDialogPlayerInfoView).updatePlayerInfo(mMatchDetailResponse)
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