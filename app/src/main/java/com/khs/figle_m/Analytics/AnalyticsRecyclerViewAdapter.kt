package com.khs.figle_m.Analytics

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Trade.TradeViewHolder

class AnalyticsRecyclerViewAdapter(context: Context, playerMap : HashMap<Int, List<PlayerDTO>>, val itemClick : (PlayerDTO) -> Unit)
    : RecyclerView.Adapter<TradeViewHolder>() {
    private val TAG: String = javaClass.name
    val DEBUG = false
    val mContext: Context
    val mPlayerMap: HashMap<Int, List<PlayerDTO>>?

    init {
        mContext = context
        mPlayerMap = playerMap
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradeViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return mPlayerMap!!.keys.size
    }

    override fun onBindViewHolder(holder: TradeViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}