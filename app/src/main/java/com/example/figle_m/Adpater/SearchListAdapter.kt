package com.example.figle_m.Adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.figle_m.R
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.databinding.ItemSearchListBinding

class SearchListAdapter(context: Context?, matchList: MutableList<MatchDetailResponse>?) :
    RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {
    private val TAG: String = javaClass.name

    val mContext: Context?
    val mMatchList: MutableList<MatchDetailResponse>?
    var mItemSearchListBinding: ItemSearchListBinding? = null
    init {
        mContext = context
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
            var score: String? = mMatchList[position].matchInfo!![0].shoot.goalTotal.toString()
            score += " : " + mMatchList[position].matchInfo!![1].shoot.goalTotal.toString()

            var user: String? = mMatchList[position].matchInfo!![0].nickname
            user += " vs " + mMatchList[position].matchInfo!![1].nickname

            mItemSearchListBinding!!.txtResult.text =
                mMatchList[position].matchInfo!![0].matchDetail!!.matchResult
            mItemSearchListBinding!!.txtNickName.text = user
            mItemSearchListBinding!!.txtScore.text = score
        }
//        mDataBinding.txtNickName = mMatchList[position].matchInfoList[0].nickname
//        holder.mItemView = mMatchList[position].matchInfoList[0].nickname
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