package com.khs.figle_m.Trade

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.khs.figle_m.Common.CirclePlayerView
import com.khs.figle_m.R
import com.khs.figle_m.Response.TradeResponse
import com.khs.figle_m.Utils.StringUtils
import kotlinx.android.synthetic.main.cview_trade_player.view.*
import kotlinx.android.synthetic.main.item_trade_buy.view.*
import kotlinx.android.synthetic.main.item_trade_sell.view.*

open abstract class TradeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
   abstract fun bind(item: TradeResponse)
}

open class TradeSellViewHolder(itemView: View) : TradeViewHolder(itemView) {
   override fun bind(item: TradeResponse) {
      itemView.sell_txt_date.text = item.tradeDate.replace("T","\n")
      var circlePlayerView = itemView.sell_layout_trade_player.findViewWithTag<CirclePlayerView>("com.khs.figle_m.Common.CirclePlayerView")
      circlePlayerView.updateView(item.spid.toString(), -1, false, item.grade.toInt(), -1, -1, item.imageResUrl)
   }
}

open class TradeBuyViewHolder(itemView: View) : TradeViewHolder(itemView) {
   override fun bind(item: TradeResponse) {
      itemView.buy_txt_date.text = com.khs.figle_m.Utils.DateUtils().formatTimeString(item.tradeDateMs)
      var circlePlayerView = itemView.buy_layout_trade_player.findViewWithTag<CirclePlayerView>("com.khs.figle_m.Common.CirclePlayerView")
      circlePlayerView.updateView(item.spid.toString(), -1, false, item.grade.toInt(), -1, -1, item.imageResUrl)
      itemView.txt_player_value.text = StringUtils().parseValue(item.value) + " BP"
      if (item.tradeType == TradeHomeFragment.TradeType.sell.ordinal) {
         itemView.trade_type.text = "SELL"
         itemView.trade_type.setTextColor(itemView.context.getColor(R.color.trade_red_color))
      } else {
         itemView.trade_type.text = "BUY"
         itemView.trade_type.setTextColor(itemView.context.getColor(R.color.trade_blue_color))
      }
   }
}