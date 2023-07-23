package com.khs.figle_m.SearchDetail

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.khs.figle_m.Analytics.Squad.SquadFieldView
import com.khs.data.nexon_api.response.DTO.PlayerDTO
import com.khs.data.nexon_api.response.MatchDetailResponse
import com.khs.figle_m.SearchDetail.FirstView.SearchDetailDialogGameResultView
import com.khs.figle_m.SearchDetail.FirstView.SearchDetailDialogPlayerInfoView


class SearchDetailDialogAdapter() : PagerAdapter() {

    lateinit var mContext: Context
    lateinit var mMatchDetailResponse: MatchDetailResponse
    lateinit var mItemClick: (Pair<PlayerDTO,Boolean>) -> Unit

    constructor(context: Context, matchDetailResponse: MatchDetailResponse, itemClick : (Pair<PlayerDTO,Boolean>) -> Unit) : this() {
        this.mContext = context
        this.mMatchDetailResponse = matchDetailResponse
        this.mItemClick = itemClick
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        var view: View? = null
        when (position) {
            0 -> {
                view =
                    SearchDetailDialogGameResultView(mContext)
                (view as SearchDetailDialogGameResultView).updateMatchInfo(mMatchDetailResponse)
            }
            1 -> {
                var userNickname = ""
                if (mMatchDetailResponse.matchInfo[0].accessId == SearchDetailDialogFragment.getInstance().mSearchAccessId) {
                    userNickname = mMatchDetailResponse.matchInfo[0].nickname
                } else {
                    userNickname = mMatchDetailResponse.matchInfo[1].nickname
                }
                view = SquadFieldView(mContext)
                (view as SquadFieldView).updateMatchInfo(
                    userNickname,
                    SearchDetailDialogFragment.getInstance().mSearchAccessId,
                    SearchDetailDialogFragment.getInstance().getPlayerImgMap()) {
                    mItemClick(it)
                }
            }
            2 -> {
                var userNickname = ""
                if (mMatchDetailResponse.matchInfo[0].accessId == SearchDetailDialogFragment.getInstance().mOpposingUserId) {
                    userNickname = mMatchDetailResponse.matchInfo[0].nickname
                } else {
                    userNickname = mMatchDetailResponse.matchInfo[1].nickname
                }
                view = SquadFieldView(mContext)
                (view as SquadFieldView).updateMatchInfo(
                    userNickname,
                    SearchDetailDialogFragment.getInstance().mOpposingUserId,
                    SearchDetailDialogFragment.getInstance().getPlayerImgMap()) {
                    mItemClick(it)
                }
            }
            3 -> {
                view =
                    SearchDetailDialogPlayerInfoView(mContext)
                (view as SearchDetailDialogPlayerInfoView).updatePlayerInfo(
                    SearchDetailDialogFragment.getInstance().mSearchAccessId,
                    SearchDetailDialogFragment.getInstance().mOpposingUserId,
                    SearchDetailDialogFragment.getInstance().getPlayerImgMap()
                ) {
                    mItemClick(it)
                }
            }
        }
        collection.addView(view)
        return view!!
    }

    override fun getCount(): Int {
        return 4
    }

    override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
        return arg0 === arg1
    }

    override fun destroyItem(collection: View, position: Int, view: Any) {
        (collection as ViewPager).removeView(view as View)
    }
}