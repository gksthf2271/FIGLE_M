package com.example.figle_m.SearchList

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.figle_m.R
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.databinding.ItemSearchListBinding
import com.example.figle_m.utils.DateUtils
import java.text.SimpleDateFormat

class SearchListAdapter(context: Context, searchString: String, matchList: MutableList<MatchDetailResponse>?) :
    RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {
    private val TAG: String = javaClass.name

    val mSearchString: String
    val mContext: Context
    val mMatchList: List<MatchDetailResponse>?
    lateinit var mItemSearchListBinding: ItemSearchListBinding
    init {
        mContext = context
        mSearchString = searchString
        mMatchList = matchList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_list, parent, false)
        val viewHolder = ViewHolder(view, this)
        mItemSearchListBinding = ItemSearchListBinding.bind(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mMatchList.let { mMatchList!!.size - 1 }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mMatchList!![position].matchInfo.let {
            var opposingUserIndex = 1
            var myIndex = 0

            if (mSearchString.equals(mMatchList[position].matchInfo!![0].nickname.toLowerCase())){
                opposingUserIndex = 1
                myIndex = 0
            } else {
                opposingUserIndex = 0
                myIndex = 1
            }

            val myMatchInfo = mMatchList[position].matchInfo!![myIndex]
            val opposingUserMatchInfo = mMatchList[position].matchInfo!![opposingUserIndex]
            var matchDate = mMatchList[position].matchDate
            val date = DateUtils().getDate(matchDate)

            matchDate = DateUtils().formatTimeString(date)

            mItemSearchListBinding!!.txtLeftNickName.text = myMatchInfo.nickname
            mItemSearchListBinding!!.txtRightNickName.text = opposingUserMatchInfo.nickname

            mItemSearchListBinding!!.txtLeftScore.text = myMatchInfo.shoot.goalTotal.toString()
            mItemSearchListBinding!!.txtRightScore.text = opposingUserMatchInfo.shoot.goalTotal.toString()
            mItemSearchListBinding!!.txtDate.text = matchDate

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
            mItemSearchListBinding!!.txtLeftResult.text = myResult
            mItemSearchListBinding!!.txtRightResult.text = opposingUserResult

            val res =
                when (myMatchInfo.matchDetail!!.matchResult) {
                    "승" -> mContext.resources.getColor(R.color.search_list_win, null)
                    "패" -> mContext.resources.getColor(R.color.search_list_lose, null)
                    else -> mContext.resources.getColor(R.color.search_list_draw, null)
                }
            mItemSearchListBinding!!.rootLayout.setBackgroundColor(res)

        }
    }

    open class ViewHolder(itemView: View, searchListAdapter: SearchListAdapter) :
        RecyclerView.ViewHolder(itemView) {
        var mItemView: View
        var mSearchListAdapter: SearchListAdapter

        init {
            mItemView = itemView
            mSearchListAdapter = searchListAdapter
        }
    }
}