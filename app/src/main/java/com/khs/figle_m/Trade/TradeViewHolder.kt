package com.khs.figle_m.Trade

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.khs.data.nexon_api.response.TradeResponse
import com.khs.figle_m.Common.CirclePlayerView
import com.khs.figle_m.R
import com.khs.figle_m.Utils.StringUtils
import com.khs.figle_m.databinding.ItemTradeBuyBinding

abstract class TradeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
   abstract fun bind(item: TradeResponse)
}
//
//open class TradeSellViewHolder(private val listItemView: View) : TradeViewHolder(listItemView) {
//   override fun bind(item: TradeResponse) {
//      listItemView.sellTxtDate.text = item.tradeDate.replace("T","\n")
//      val circlePlayerView = listItemView.sellLayoutTradePlayer.findViewWithTag<CirclePlayerView>("CirclePlayerView")
//      circlePlayerView.updateView(item.spid.toString(), -1, false, item.grade.toInt(), -1, -1, item.imageResUrl)
//   }
//}

open class TradeBuyViewHolder(private val itemViewBinding: ViewBinding) : TradeViewHolder(itemViewBinding.root) {
   override fun bind(item: TradeResponse) {
      if (itemViewBinding is ItemTradeBuyBinding) {
         itemViewBinding.buyTxtDate.text = com.khs.figle_m.Utils.DateUtils().formatTimeString(item.tradeDateMs)
         val circlePlayerView = itemViewBinding.buyLayoutTradePlayer.findViewWithTag<CirclePlayerView>("CirclePlayerView")
         circlePlayerView.updateView(item.spid.toString(), -1, false, item.grade.toInt(), -1, -1, item.imageResUrl)
         circlePlayerView.txtPlayerValue.text = StringUtils().parseValue(item.value) + " BP"
         if (item.tradeType == TradeHomeFragment.TradeType.sell.ordinal) {
            itemViewBinding.tradeType.text = "SELL"
            itemViewBinding.tradeType.setTextColor(itemView.context.getColor(R.color.trade_red_color))
         } else {
            itemViewBinding.tradeType.text = "BUY"
            itemViewBinding.tradeType.setTextColor(itemView.context.getColor(R.color.trade_blue_color))
         }
      }
   }
}