package com.khs.figle_m.Trade

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khs.figle_m.R
import com.khs.figle_m.Ranking.Ranker

class TradeRecyclerViewAdapter(context: Context, rankerList:List<Ranker>, val itemClick: (Ranker) -> Unit) :
RecyclerView.Adapter<TradeRecyclerViewAdapter.ViewHolder>() {
    private val TAG: String = javaClass.name
    val DEBUG = false
    val mContext: Context
    val mPlayerList: List<Ranker>?

    init {
        mContext = context
        mPlayerList = rankerList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_trade_buy, parent, false)
        viewHolder = ViewHolder(view, itemClick)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mPlayerList.let { mPlayerList!!.size }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(DEBUG) Log.v(TAG, "onBindViewHolder, position : $position")
        holder.bind(mPlayerList!!.get(position), mContext)
    }

    inner class ViewHolder(itemView: View, itemClick: (Ranker) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val TAG: String = javaClass.name

        fun bind(item: Ranker, context: Context) {

        }
    }
}