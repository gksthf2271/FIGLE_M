package com.example.figle_m.SearchList

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.figle_m.R
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.utils.DateUtils

class SearchListAdapter(context: Context, searchAccessId: String, matchList: MutableList<MatchDetailResponse>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TAG: String = javaClass.name

    val mSearchAccessId: String
    val mContext: Context
    val mMatchList: List<MatchDetailResponse>?
    init {
        mContext = context
        mSearchAccessId = searchAccessId
        mMatchList = matchList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder:RecyclerView.ViewHolder? = null
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_list, parent, false)
        viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mMatchList.let { mMatchList!!.size }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.v(TAG,"onBindViewHolder, position : $position")
        if (holder is ViewHolder) {
            this.mMatchList!!.get(position).matchInfo.let {
                var opposingUserIndex = 1
                var myIndex = 0
                val matchInfo = mMatchList[position].matchInfo!!

                if (mSearchAccessId.equals(matchInfo[0].accessId.toLowerCase())) {
                    opposingUserIndex = 1
                    myIndex = 0
                } else {
                    opposingUserIndex = 0
                    myIndex = 1
                }

                if (matchInfo.size <= 1) {
                    Log.v(TAG, "경기 계정이 단일 계정으로 이상 데이터! $matchInfo")
                    return
                }
                val myMatchInfo = matchInfo[myIndex]
                val opposingUserMatchInfo = matchInfo[opposingUserIndex]
                var matchDate = mMatchList[position].matchDate

                matchDate = DateUtils().formatTimeString(matchDate.toLong())

                holder.mTxtLeftNickName.text = myMatchInfo.nickname
                holder.mTxtRightNickName.text = opposingUserMatchInfo.nickname

                holder.mTxtLeftScore.text = myMatchInfo.shoot.goalTotal.toString()
                holder.mTxtRightScore.text = opposingUserMatchInfo.shoot.goalTotal.toString()
                holder.mTxtDate.text = matchDate

                var myResult: String? = null
                var opposingUserResult: String? = null

                when (myMatchInfo.matchDetail!!.matchResult) {
                    "승" -> {
                        myResult = "WIN"
                        opposingUserResult = "LOSE"
                    }
                    "무" -> {
                        myResult = "DRAW"
                        opposingUserResult = "DRAW"
                    }
                    "패" -> {
                        myResult = "LOSE"
                        opposingUserResult = "WIN"
                    }
                }
                holder.mTxtLeftResult.text = myResult
                holder.mTxtRightResult.text = opposingUserResult

                var res = 0
                when (myMatchInfo.matchDetail!!.matchResult) {
                    "승" -> {
                        res = mContext.resources.getColor(R.color.search_list_win, null)
                    }
                    "패" -> {
                        res = mContext.resources.getColor(R.color.search_list_lose, null)
                    }
                    else -> {
                        res = mContext.resources.getColor(R.color.search_list_draw, null)
                    }
                }
                holder.mRootLayout.setBackgroundColor(res)
            }
        }
    }

    open class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
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
    }

    class HeaderViewHolder(headerView: View): RecyclerView.ViewHolder(headerView) {
        var mHeaderViewHolder : View
        var mHeaderView: TextView
        init {
            mHeaderViewHolder = headerView
            mHeaderView = headerView.findViewById(R.id.txt_header)
        }
    }
}