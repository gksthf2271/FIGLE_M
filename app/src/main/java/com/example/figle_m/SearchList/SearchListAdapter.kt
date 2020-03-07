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
import com.example.figle_m.databinding.ItemSearchListBinding
import com.example.figle_m.utils.DateUtils

class SearchListAdapter(context: Context, searchAccessId: String, matchList: MutableList<MatchDetailResponse>?) :
    RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {
    private val TAG: String = javaClass.name

    val mSearchAccessId: String
    val mContext: Context
    val mMatchList: List<MatchDetailResponse>?
    init {
        mContext = context
        mSearchAccessId = searchAccessId
        mMatchList = matchList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_list, parent, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mMatchList.let { mMatchList!!.size - 1 }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.v(TAG,"onBindViewHolder, position : $position")
        this.mMatchList!!.get(position).matchInfo.let {
            var opposingUserIndex = 1
            var myIndex = 0

            if (mSearchAccessId.equals(mMatchList[position].matchInfo!![0].accessId.toLowerCase())){
                opposingUserIndex = 1
                myIndex = 0
            } else {
                opposingUserIndex = 0
                myIndex = 1
            }

            if (mMatchList[position].matchInfo.size <= myIndex) {
                Log.v(TAG,"경기 계정이 단일 계정으로 이상 데이터!")
                return
            }
            val myMatchInfo = mMatchList[position].matchInfo!![myIndex]
            val opposingUserMatchInfo = mMatchList[position].matchInfo!![opposingUserIndex]
            var matchDate = mMatchList[position].matchDate
//            val date = DateUtils().getDate(matchDate)

            matchDate = DateUtils().formatTimeString(matchDate.toLong())

            holder.mTxtLeftNickName.text = myMatchInfo.nickname
            holder.mTxtRightNickName.text = opposingUserMatchInfo.nickname

            holder.mTxtLeftScore.text = myMatchInfo.shoot.goalTotal.toString()
            holder.mTxtRightScore.text = opposingUserMatchInfo.shoot.goalTotal.toString()
            holder.mTxtDate.text = matchDate

            var myResult:String? = null
            var opposingUserResult:String? = null

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

            val res =
                when (myMatchInfo.matchDetail!!.matchResult) {
                    "승" -> mContext.resources.getColor(R.color.search_list_win, null)
                    "패" -> mContext.resources.getColor(R.color.search_list_lose, null)
                    else -> mContext.resources.getColor(R.color.search_list_draw, null)
                }
            holder.mRootLayout.setBackgroundColor(res)

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
}