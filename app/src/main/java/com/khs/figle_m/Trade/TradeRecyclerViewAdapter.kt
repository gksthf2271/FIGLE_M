package com.khs.figle_m.Trade

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.khs.figle_m.Common.CirclePlayerView
import com.khs.figle_m.R
import com.khs.figle_m.Response.TradeResponse
import kotlinx.android.synthetic.main.item_trade_buy.view.*
import kotlinx.android.synthetic.main.item_trade_sell.view.*

class TradeRecyclerViewAdapter(context: Context, tradeList:List<TradeResponse>, val itemClick: (TradeResponse) -> Unit) :
RecyclerView.Adapter<TradeViewHolder>() {
    private val TAG: String = javaClass.name
    val DEBUG = false
    val mContext: Context
    val mPlayerList: List<TradeResponse>?

    init {
        mContext = context
        mPlayerList = tradeList
    }

    inner class ViewHolderMaker(){
        fun getLayoutResId(type: Int) : Int{
            var resId = 0
            if (TradeHomeFragment.TradeType.sell.ordinal == type){
                resId = R.layout.item_trade_sell
            } else {
                resId = R.layout.item_trade_buy
            }
            return resId
        }

        fun getViewHolder(type: Int, view:View) : TradeViewHolder{
            var viewHolder : TradeViewHolder
            if (TradeHomeFragment.TradeType.sell.ordinal.equals(type)){
                viewHolder = TradeSellViewHolder(view)
            } else {
                viewHolder = TradeBuyViewHolder(view)
            }
            return viewHolder
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mPlayerList!!.get(position).tradeType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradeViewHolder {
        var viewHolder: RecyclerView.ViewHolder

        var resId : Int = ViewHolderMaker().getLayoutResId(viewType)

        val view: View =
            LayoutInflater.from(parent.context).inflate(resId, parent, false)

        val playerView = CirclePlayerView(mContext)
        playerView.tag = playerView.TAG

        if(viewType == TradeHomeFragment.TradeType.sell.ordinal) {
            view.sell_layout_trade_player.addView(playerView)
        } else {
            view.buy_layout_trade_player.addView(playerView)
        }
        viewHolder = ViewHolderMaker().getViewHolder(viewType, view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mPlayerList.let { mPlayerList!!.size }
    }

    override fun onBindViewHolder(holder: TradeViewHolder, position: Int) {
        if(DEBUG) Log.v(TAG, "onBindViewHolder, position : $position")
        holder.bind(mPlayerList!!.get(position))
    }
}