package com.khs.figle_m.Trade

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.khs.figle_m.Response.TradeResponse
import kotlinx.android.synthetic.main.item_trade_buy.view.*
import kotlinx.android.synthetic.main.item_trade_sell.view.*

open abstract class TradeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
   abstract fun bind(item: TradeResponse)
}

open class TradeSellViewHolder(itemView: View) : TradeViewHolder(itemView) {
   override fun bind(item: TradeResponse) {
      itemView.sell_txt_date.text = item.tradeDate
   }
}

open class TradeBuyViewHolder(itemView: View) : TradeViewHolder(itemView) {
   override fun bind(item: TradeResponse) {
      itemView.buy_txt_date.text = item.tradeDate
   }
}