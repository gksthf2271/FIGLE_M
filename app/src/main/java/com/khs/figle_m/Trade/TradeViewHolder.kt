package com.khs.figle_m.Trade

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.khs.figle_m.Common.CirclePlayerView
import com.khs.figle_m.R
import com.khs.figle_m.Response.TradeResponse
import kotlinx.android.synthetic.main.item_trade_buy.view.*
import kotlinx.android.synthetic.main.item_trade_sell.view.*

open abstract class TradeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
   abstract fun bind(item: TradeResponse)
}

open class TradeSellViewHolder(itemView: View) : TradeViewHolder(itemView) {
   override fun bind(item: TradeResponse) {
      itemView.sell_txt_date.text = item.tradeDate.replace("T","\n")
      var circlePlayerView = itemView.sell_layout_trade_player.findViewWithTag<CirclePlayerView>("com.khs.figle_m.Common.CirclePlayerView")
      circlePlayerView.initView(R.layout.cview_trade_player)
      circlePlayerView.updateView(item.spid.toString(), -1, false, item.grade.toInt(), -1, -1, item.imageResUrl)
   }
}

open class TradeBuyViewHolder(itemView: View) : TradeViewHolder(itemView) {
   override fun bind(item: TradeResponse) {
      itemView.buy_txt_date.text = item.tradeDate
      var circlePlayerView = itemView.buy_layout_trade_player.findViewWithTag<CirclePlayerView>("com.khs.figle_m.Common.CirclePlayerView")
      circlePlayerView.initView(R.layout.cview_trade_player)
      circlePlayerView.updateView(item.spid.toString(), -1, false, item.grade.toInt(), -1, -1, item.imageResUrl)
   }
}