package com.khs.figle_m.trade

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.khs.data.nexon_api.response.TradeResponse
import com.khs.figle_m.BuildConfig
import com.khs.figle_m.common.CirclePlayerView
import com.khs.figle_m.databinding.ItemTradeBuyBinding

class TradeRecyclerViewAdapter(
    private val context: Context,
    private val mPlayerList: List<TradeResponse>,
    val itemClick: (TradeResponse) -> Unit
) :
RecyclerView.Adapter<TradeViewHolder>() {
    private val TAG: String = javaClass.simpleName
    val DEBUG = BuildConfig.DEBUG

    inner class ViewHolderMaker{
        fun getViewHolder(type: Int, viewBinding: ViewBinding) : TradeViewHolder{
            return TradeBuyViewHolder(viewBinding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mPlayerList!![position].tradeType
    }

    /** TODO : 23.07.23
     *  Android Extension 삭제 후 ViewBinding 으로 변환작업 필요...
     *  RecyclerView Adapter 내 아이템들 어떻게 전환해야될지 고민해봐야됨.
    **/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradeViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding: ItemTradeBuyBinding = ItemTradeBuyBinding.inflate(inflater, parent, false)
        val playerView = CirclePlayerView(context)
        playerView.tag = playerView.TAG
        playerView.initView()

        viewBinding.buyLayoutTradePlayer.addView(playerView)
        viewHolder = ViewHolderMaker().getViewHolder(viewType, viewBinding)
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