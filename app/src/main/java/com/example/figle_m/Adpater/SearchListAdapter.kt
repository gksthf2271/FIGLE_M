package com.example.figle_m.Adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.figle_m.R
import com.example.figle_m.Response.MatchDetailResponse

class SearchListAdapter(context: Context, matchList:MutableList<MatchDetailResponse>) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {
    private val TAG:String = javaClass.name

    val mContext: Context
    val mMatchList:MutableList<MatchDetailResponse>
    init {
        mContext = context
        mMatchList = matchList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_search_list, parent, false)
        val viewHolder = ViewHolder(view, this)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mMatchList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.mItemView = mMatchList[position].matchInfoList[0].nickname

    }

    open class ViewHolder(itemView: View, searchListAdapter: SearchListAdapter): RecyclerView.ViewHolder(itemView) {
        val mItemView: View
        val mSearchListAdapter:SearchListAdapter

        init {
            mItemView = itemView
            mSearchListAdapter = searchListAdapter
        }
    }
}