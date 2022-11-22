package com.khs.figle_m.Analytics

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.khs.figle_m.DB.PlayerDataBase
import com.khs.figle_m.DB.PlayerEntity
import com.khs.figle_m.PlayerDetail.PlayerDetailInfoView
import com.khs.figle_m.Utils.DrawUtils
import com.khs.figle_m.Utils.PositionEnum
import kotlinx.android.synthetic.main.item_analytics.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open abstract class AnalyticsViewHolder(itemView: View, itemClick: (AnalyticsPlayer) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: AnalyticsPlayer)
}

open class AnalyticsGradeViewHolder(itemView: View, itemClick: (AnalyticsPlayer) -> Unit) :
    AnalyticsViewHolder(itemView, itemClick) {
    var mItemView: View
    var mPlayerDetailInfoView: PlayerDetailInfoView
    var mItemCLick : (AnalyticsPlayer) -> Unit

    init {
        mItemView = itemView
        mPlayerDetailInfoView = mItemView.player_info_view
        mItemCLick = itemClick
    }

    override fun bind(item: AnalyticsPlayer) {
        resizeView()
        var totalData = item.totalData

        DrawUtils().drawSeasonIcon(
            mItemView.context,
            mItemView.analytics_img_icon,
            item.spId.toString()
        )
        DrawUtils().drawPlayerImage(mItemView.analytics_img_player, item.imageResUrl)
        var positionSet = mutableSetOf<String>()


        for (playerDTO in item.playerDataList) {
            for (position in PositionEnum.values()) {
                if (playerDTO.spPosition == position.spposition) {
                    positionSet.add(position.description)
                }
            }
        }

        mItemView.txt_avgRating.text =
            String.format("%.2f", (item.totalData.totalSpRating / (item.playerDataList.size)))
        positionSet.let { mItemView.txt_player_position.text = positionSet.toString() }
        mItemView.txt_rating.text = (adapterPosition + 1).toString()
        mItemView.txt_player_name.apply {
            val playerDB = PlayerDataBase.getInstance(context)
            playerDB?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    val player: PlayerEntity? = it.playerDao().getPlayer(item.spId.toString())
                    player ?: return@launch
                    CoroutineScope(Dispatchers.Main).launch {
                        this@apply.text = player.playerName
                    }
                }
            }
        }

        when (item.position) {
            ParentPositionEnum.F -> {
                mPlayerDetailInfoView.setTitleList(listOf("슛", "유효 슛", "어시스트", "득점"))
                mPlayerDetailInfoView.setDataList(
                    listOf(
                        totalData.totalShoot.toString(),
                        totalData.totalEffectiveShoot.toString(),
                        totalData.totalAssist.toString(),
                        totalData.totalGoal.toString()
                    )
                )
            }
            ParentPositionEnum.M -> {
                mPlayerDetailInfoView.setTitleList(listOf("패스 시도", "패스 성공", "어시스트", "득점"))
                mPlayerDetailInfoView.setDataList(
                    listOf(
                        totalData.totalPassTry.toString(),
                        totalData.totalPassSuccess.toString(),
                        totalData.totalAssist.toString(),
                        totalData.totalGoal.toString()
                    )
                )
            }
            ParentPositionEnum.D -> {
                mPlayerDetailInfoView.setTitleList(listOf("블락 성공", "태클 성공", "패스 시도", "패스 성공"))
                mPlayerDetailInfoView.setDataList(
                    listOf(
                        totalData.totalBlock.toString(),
                        totalData.totalTackle.toString(),
                        totalData.totalPassTry.toString(),
                        totalData.totalPassSuccess.toString()
                    )
                )
            }
            ParentPositionEnum.GK -> {
                mPlayerDetailInfoView.setTitleList(listOf("블락 성공", "태클 성공", "패스 시도", "패스 성공"))
                mPlayerDetailInfoView.setDataList(
                    listOf(
                        totalData.totalBlock.toString(),
                        totalData.totalTackle.toString(),
                        totalData.totalPassTry.toString(),
                        totalData.totalPassSuccess.toString()
                    )
                )
            }
        }
    }

    fun resizeView() {
        mPlayerDetailInfoView.setValueTextSize(25f)
        mPlayerDetailInfoView.setTitleTextSize(9f)
    }
}


open class AnalyticsGoalViewHolder(itemView: View, itemClick: (AnalyticsPlayer) -> Unit) :
    AnalyticsViewHolder(itemView, itemClick) {
    override fun bind(item: AnalyticsPlayer) {

    }
}