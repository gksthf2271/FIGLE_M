package com.khs.figle_m.SearchList

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.khs.figle_m.BuildConfig
import com.khs.figle_m.R
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.Utils.DateUtils
import com.khs.figle_m.Utils.LogUtil

class SearchListAdapter(context: Context, searchAccessId: String, matchList: ArrayList<MatchDetailResponse>, val itemClick: (MatchDetailResponse) -> Unit) :
    RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {
    private val TAG: String = javaClass.simpleName
    val DEBUG = BuildConfig.DEBUG
    private val mSearchAccessId: String
    private val mContext: Context
    private val mMatchList: ArrayList<MatchDetailResponse>

    init {
        mContext = context
        mSearchAccessId = searchAccessId
        mMatchList = matchList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_list, parent, false)
        viewHolder = ViewHolder(view, itemClick)
        return viewHolder
    }

    override fun getItemCount(): Int {
        LogUtil.vLog(LogUtil.TAG_SEARCH, TAG,"getItemCount : ${mMatchList.size}")
        return mMatchList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        LogUtil.dLog(LogUtil.TAG_SEARCH, TAG,"onBindViewHolder, position : $position")
        holder.bind(mSearchAccessId, mMatchList[position], mContext)
    }

    inner class ViewHolder(itemView: View, itemClick: (MatchDetailResponse) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val TAG: String = javaClass.simpleName
        var mRootLayout: ConstraintLayout
        var mItemView: View
        var mTxtLeftNickName: TextView
        var mTxtRightNickName: TextView
        var mTxtLeftScore: TextView
        var mTxtRightScore: TextView
        var mTxtLeftResult: TextView
        var mTxtRightResult: TextView
        var mTxtDate: TextView

        init {
            mItemView = itemView
            mRootLayout = mItemView.findViewById(R.id.root_layout)
            mTxtRightNickName = mItemView.findViewById(R.id.txt_right_nickName)
            mTxtLeftNickName = mItemView.findViewById(R.id.txt_left_nickName)
            mTxtLeftScore = mItemView.findViewById(R.id.txt_left_score)
            mTxtRightScore = mItemView.findViewById(R.id.txt_right_score)
            mTxtLeftResult = mItemView.findViewById(R.id.txt_left_result)
            mTxtRightResult = mItemView.findViewById(R.id.txt_right_result)
            mTxtDate = mItemView.findViewById(R.id.txt_date)
        }


        fun bind(searchAccessId: String, item: MatchDetailResponse, context: Context) {
            item.matchInfo.let {
                var opposingUserIndex = 1
                var myIndex = 0
                val matchInfo = item.matchInfo

                if (searchAccessId == matchInfo[0].ouid.toLowerCase()) {
                    opposingUserIndex = 1
                    myIndex = 0
                } else {
                    opposingUserIndex = 0
                    myIndex = 1
                }

                if (matchInfo.size <= 1) {
                    LogUtil.vLog(LogUtil.TAG_SEARCH, TAG,"경기 계정이 단일 계정으로 이상 데이터! $matchInfo")
                    return
                }
                val myMatchInfo = matchInfo[myIndex]
                val opposingUserMatchInfo = matchInfo[opposingUserIndex]
                var matchDate = item.matchDate

                matchDate = DateUtils().formatTimeString(matchDate.toLong())

                mTxtLeftNickName.text = myMatchInfo.nickname
                mTxtRightNickName.text = opposingUserMatchInfo.nickname

                if (myMatchInfo.shoot.goalTotal == myMatchInfo.shoot.goalTotalDisplay) {
                    mTxtLeftScore.text = myMatchInfo.shoot.goalTotal.toString()
                } else {
                    mTxtLeftScore.text = myMatchInfo.shoot.goalTotalDisplay.toString()
                }

                if (opposingUserMatchInfo.shoot.goalTotal == opposingUserMatchInfo.shoot.goalTotalDisplay) {
                    mTxtRightScore.text = opposingUserMatchInfo.shoot.goalTotal.toString()
                } else {
                    mTxtRightScore.text = opposingUserMatchInfo.shoot.goalTotalDisplay.toString()
                }

                mTxtDate.text = matchDate

                var myResult: String? = null
                var opposingUserResult: String? = null

                if (myMatchInfo.matchDetail.matchResult == null) {
                    if ("승" == opposingUserMatchInfo.matchDetail.matchResult) {
                        myMatchInfo.matchDetail.matchResult = "패"
                    } else {
                        myMatchInfo.matchDetail.matchResult = "승"
                    }
                }

                if (opposingUserMatchInfo.matchDetail.matchResult == null) {
                    if ("승" == myMatchInfo.matchDetail.matchResult) {
                        opposingUserMatchInfo.matchDetail.matchResult = "패"
                    } else {
                        opposingUserMatchInfo.matchDetail.matchResult = "승"
                    }
                }

                when (myMatchInfo.matchDetail.matchResult) {
                    "승" -> {
                        if (myMatchInfo.shoot.goalTotal == myMatchInfo.shoot.goalTotalDisplay) {
                            myResult = "승"
                        } else {
                            myResult = "몰수승\n(${myMatchInfo.shoot.goalTotal} : ${opposingUserMatchInfo.shoot.goalTotal})"
                        }
                        opposingUserResult = "패"
                    }
                    "무" -> {
                        myResult = "무"
                        opposingUserResult = "무"
                    }
                    "패" -> {
                        if (opposingUserMatchInfo.shoot.goalTotal == opposingUserMatchInfo.shoot.goalTotalDisplay) {
                            myResult = "패"
                        } else {
                            myResult = "몰수패\n(${myMatchInfo.shoot.goalTotal} : ${opposingUserMatchInfo.shoot.goalTotal})"
                        }
                        opposingUserResult = "승"
                    }
                }
                mTxtLeftResult.text = myResult
//                mTxtRightResult.text = opposingUserResult

                val drawable:Drawable = when (myMatchInfo.matchDetail!!.matchResult) {
                    "승" -> {
                        context.resources.getDrawable(R.drawable.rounded_win, null)
                    }
                    "패" -> {
                        context.resources.getDrawable(R.drawable.rounded_lose, null)
                    }
                    else -> {
                        context.resources.getDrawable(R.drawable.rounded_draw, null)
                    }
                }
                mRootLayout.background = drawable
                itemView.setOnClickListener { itemClick(item) }
            }
        }
    }

    fun updateList(matchDetailResponse: MatchDetailResponse) {
        mMatchList.add(matchDetailResponse)
        notifyItemInserted(mMatchList.lastIndex)
    }
}