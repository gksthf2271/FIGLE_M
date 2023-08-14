package com.khs.figle_m.Analytics

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.khs.data.database.PlayerDataBase
import com.khs.data.database.entity.PlayerEntity
import com.khs.figle_m.PlayerDetail.PlayerDetailInfoView
import com.khs.figle_m.Utils.DrawUtils
import com.khs.figle_m.Utils.PositionEnum
import com.khs.figle_m.databinding.ItemAnalyticsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class AnalyticsViewHolder(itemView: View, itemClick: (AnalyticsPlayer) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: AnalyticsPlayer)
}

open class AnalyticsGradeViewHolder(private val mBinding: ItemAnalyticsBinding, private val mItemClick: (AnalyticsPlayer) -> Unit) :
    AnalyticsViewHolder(mBinding.root, mItemClick) {
    var mPlayerDetailInfoView: PlayerDetailInfoView = mBinding.playerInfoView

    override fun bind(item: AnalyticsPlayer) {
        resizeView()
        val totalData = item.totalData

        DrawUtils().drawSeasonIcon(mBinding.root.context, mBinding.analyticsImgIcon, item.spId.toString())
        DrawUtils().drawPlayerImage(mBinding.analyticsImgPlayer, item.imageResUrl)
        val positionSet = mutableSetOf<String>()


        for (playerDTO in item.playerDataList) {
            for (position in PositionEnum.values()) {
                if (playerDTO.spPosition == position.spposition) {
                    positionSet.add(position.description)
                }
            }
        }

        mBinding.txtAvgRating.text =
            String.format("%.2f", (item.totalData.totalSpRating / (item.playerDataList.size)))
        positionSet.let { mBinding.txtPlayerPosition.text = positionSet.toString() }
        mBinding.txtRating.text = (adapterPosition + 1).toString()
        mBinding.txtPlayerName.apply {
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
            else -> {}
        }
    }

    private fun resizeView() {
        mPlayerDetailInfoView.setValueTextSize(25f)
        mPlayerDetailInfoView.setTitleTextSize(9f)
    }
}


open class AnalyticsGoalViewHolder(itemView: View, itemClick: (AnalyticsPlayer) -> Unit) :
    AnalyticsViewHolder(itemView, itemClick) {
    override fun bind(item: AnalyticsPlayer) {

    }
}