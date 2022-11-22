package com.khs.figle_m.Analytics

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khs.figle_m.BuildConfig
import com.khs.figle_m.R
import com.khs.figle_m.Response.DTO.MatchInfoDTO
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.Utils.LogUtil

class AnalyticsAdapter (context: Context, matchInfoList: List<MatchInfoDTO>, val itemClick: (MatchDetailResponse) -> Unit) :
    RecyclerView.Adapter<AnalyticsAdapter.ViewHolder>() {
    private val TAG: String = javaClass.simpleName
    val DEBUG = BuildConfig.DEBUG
    val mMatchInfoList: List<MatchInfoDTO>?
    val mContext: Context

    init {
        mContext = context
        mMatchInfoList = matchInfoList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_match_info, parent, false)
        viewHolder = ViewHolder(view, itemClick)
        return viewHolder
    }

    override fun getItemCount(): Int {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"getItemCount : ${mMatchInfoList?.size}")
        return mMatchInfoList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mMatchInfoList!![position], mContext)
    }

    inner class ViewHolder(itemView: View, itemClick: (MatchDetailResponse) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val TAG: String = javaClass.simpleName
        var mItemView: View

        init {
            mItemView = itemView
        }

        fun bind(item: MatchInfoDTO, context: Context) {

        }
    }
}