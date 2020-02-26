package com.example.figle_m.SearchList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.figle_m.R
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.databinding.ItemSearchListBinding

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
        return mMatchList.let { mMatchList!!.size }
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

            var score: String? = mMatchList[position].matchInfo!![myIndex].shoot.goalTotal.toString()
            score += " : " + mMatchList[position].matchInfo!![opposingUserIndex].shoot.goalTotal.toString()

            var user: String? = mMatchList[position].matchInfo!![myIndex].nickname
            user += " vs " + mMatchList[position].matchInfo!![opposingUserIndex].nickname

            val result = mMatchList[position].matchInfo!![myIndex].matchDetail!!.matchResult

            mItemSearchListBinding!!.txtResult.text = result
            mItemSearchListBinding!!.txtNickName.text = user
            mItemSearchListBinding!!.txtScore.text = score

            val res =
                when (result) {
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