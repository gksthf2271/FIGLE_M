package com.khs.figle_m.Trade

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
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

        val playerView = LayoutInflater.from(mContext).inflate(R.layout.cview_player_item_view, null)

        if(viewType == TradeHomeFragment.TradeType.sell.ordinal) {
            initLayout(view.sell_layout_trade_player, playerView)
            view.sell_layout_trade_player.addView(playerView)
        } else {
            initLayout(view.buy_layout_trade_player, playerView)
            view.buy_layout_trade_player.addView(playerView)
        }

        viewHolder = ViewHolderMaker().getViewHolder(viewType, view)
        return viewHolder
    }

    fun initLayout(constraintLayout: ConstraintLayout, itemView: View){
        var constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.connect(
            itemView.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP,
            0
        )
        constraintSet.connect(
            itemView.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
            0
        )
        constraintSet.connect(
            itemView.id,
            ConstraintSet.LEFT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.LEFT,
            0
        )
        constraintSet.connect(
            itemView.id,
            ConstraintSet.RIGHT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.RIGHT,
            0
        )
        constraintSet.applyTo(constraintLayout)
    }

    override fun getItemCount(): Int {
        return mPlayerList.let { mPlayerList!!.size }
    }

    override fun onBindViewHolder(holder: TradeViewHolder, position: Int) {
        if(DEBUG) Log.v(TAG, "onBindViewHolder, position : $position")
        holder.bind(mPlayerList!!.get(position))
    }
}