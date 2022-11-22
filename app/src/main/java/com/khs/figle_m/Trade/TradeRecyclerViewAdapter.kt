package com.khs.figle_m.Trade

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khs.figle_m.BuildConfig
import com.khs.figle_m.Common.CirclePlayerView
import com.khs.figle_m.R
import com.khs.figle_m.Response.TradeResponse
import com.khs.figle_m.Utils.LogUtil
import kotlinx.android.synthetic.main.item_trade_buy.view.*

class TradeRecyclerViewAdapter(context: Context, tradeList:List<TradeResponse>, val itemClick: (TradeResponse) -> Unit) :
RecyclerView.Adapter<TradeViewHolder>() {
    private val TAG: String = javaClass.simpleName
    val DEBUG = BuildConfig.DEBUG
    val mContext: Context
    val mPlayerList: List<TradeResponse>?

    init {
        mContext = context
        mPlayerList = tradeList
    }

    inner class ViewHolderMaker(){
        fun getLayoutResId(type: Int) : Int{
            var resId = 0
//            if (TradeHomeFragment.TradeType.sell.ordinal == type){
//                resId = R.layout.item_trade_sell
//            } else {
                resId = R.layout.item_trade_buy
//            }
            return resId
        }

        fun getViewHolder(type: Int, view:View) : TradeViewHolder{
            var viewHolder : TradeViewHolder
//            if (TradeHomeFragment.TradeType.sell.ordinal.equals(type)){
//                viewHolder = TradeSellViewHolder(view)
//            } else {
                viewHolder = TradeBuyViewHolder(view)
//            }
            return viewHolder
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mPlayerList!![position].tradeType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradeViewHolder {
        val viewHolder: RecyclerView.ViewHolder

        val resId : Int = ViewHolderMaker().getLayoutResId(viewType)

        val view: View =
            LayoutInflater.from(parent.context).inflate(resId, parent, false)

        val playerView = CirclePlayerView(mContext)
        playerView.tag = playerView.TAG
        playerView.initView(R.layout.cview_trade_player)

//        if(viewType == TradeHomeFragment.TradeType.sell.ordinal) {
//            view.sell_layout_trade_player.addView(playerView)
//        } else {
            view.buy_layout_trade_player.addView(playerView)
//        }
        viewHolder = ViewHolderMaker().getViewHolder(viewType, view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mPlayerList?.size ?: 0
    }

    override fun onBindViewHolder(holder: TradeViewHolder, position: Int) {
        mPlayerList?.let {
            holder.bind(it[position])
        }
    }
}