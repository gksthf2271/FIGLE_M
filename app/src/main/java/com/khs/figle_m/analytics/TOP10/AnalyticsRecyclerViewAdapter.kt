package com.khs.figle_m.analytics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khs.figle_m.BuildConfig
import com.khs.data.database.PlayerDataBase
import com.khs.data.database.entity.PlayerEntity
import com.khs.figle_m.playerDetail.PlayerDetailInfoView
import com.khs.figle_m.R
import com.khs.figle_m.utils.DrawUtils
import com.khs.figle_m.utils.PositionEnum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.khs.figle_m.databinding.ItemAnalyticsBinding

class AnalyticsRecyclerViewAdapter(context: Context, rowType: AnalyticsFragment.ROW_TYPE, val mPlayerInfoList : List<AnalyticsPlayer>, val itemClick : (AnalyticsPlayer) -> Unit)
    : RecyclerView.Adapter<AnalyticsRecyclerViewAdapter.ViewHolder>() {
    private val TAG: String = javaClass.simpleName
    val DEBUG = BuildConfig.DEBUG
    val mContext: Context
    val mRowType: AnalyticsFragment.ROW_TYPE
    lateinit var mBinding : ItemAnalyticsBinding

    init {
        mContext = context
        mRowType = rowType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        mBinding = ItemAnalyticsBinding.inflate(inflater, parent, true)
        viewHolder = ViewHolder(mBinding, itemClick)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mPlayerInfoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mPlayerInfoList[position],mContext)
    }

    inner class ViewHolder(val mBinding: ItemAnalyticsBinding, itemClick: (AnalyticsPlayer) -> Unit) : RecyclerView.ViewHolder(mBinding.root) {
        val TAG: String = javaClass.simpleName
        var mPlayerDetailInfoView : PlayerDetailInfoView = mBinding.playerInfoView

        fun bind(item: AnalyticsPlayer, context: Context) {
            setTextSize()

            DrawUtils().drawSeasonIcon(context, mBinding.analyticsImgIcon, item.spId.toString())
            DrawUtils().drawPlayerImage(mBinding.analyticsImgPlayer, item.imageResUrl)
            val positionSet = mutableSetOf<String>()


            for (playerDTO in item.playerDataList) {
                for (position in PositionEnum.values()) {
                    if (playerDTO.spPosition == position.spposition) {
                        positionSet.add(position.description)
                    }
                }
            }

            positionSet.let { mBinding.txtPlayerPosition.text = it.toString() }
            showRatingView(context, item)
            mBinding.txtPlayerName.apply {
                val playerDB = PlayerDataBase.getInstance(context)
                playerDB?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        val player : PlayerEntity? = it.playerDao().getPlayer(item.spId.toString())
                        player ?: return@launch
                        CoroutineScope(Dispatchers.Main).launch {
                            this@apply.text = player.playerName
                        }
                    }
                }
            }

            initInfoView(item)

            mBinding.root.setOnClickListener() {
                itemClick(item)
            }
        }

        private fun showRatingView(context: Context, item : AnalyticsPlayer){
            val index = mPlayerInfoList.indexOf(item) + 1
            if (index == 1) {
                context.getDrawable(R.drawable.rounded_player_team_mvp).let {
                    mBinding.txtRating.background = it
                }
            } else {
                context.getDrawable(R.drawable.rounded_player)?.let {
                    mBinding.txtRating.background = it
                }
            }
            mBinding.txtRating.text = mPlayerInfoList.let { (index).toString()}
        }

        private fun initInfoView(item : AnalyticsPlayer) {
            val totalData = item.totalData
            when(mRowType){
                AnalyticsFragment.ROW_TYPE.ASSIST -> {
                    mBinding.txtAvgRating.visibility = View.GONE
                    mPlayerDetailInfoView.setTitleList(listOf("도움", "패스 시도", "패스 성공 ", "평점"))
                    mPlayerDetailInfoView.setDataList(
                        listOf(
                            totalData.totalAssist.toString(),
                            totalData.totalPassTry.toString(),
                            totalData.totalPassSuccess.toString(),
                            String.format("%.2f", (item.totalData.totalSpRating/(item.playerDataList.size)))
                        )
                    )
                }
                AnalyticsFragment.ROW_TYPE.GOAL -> {
                    mBinding.txtAvgRating.visibility = View.GONE
                    mPlayerDetailInfoView.setTitleList(listOf("득점", "슛", "유효 슛", "평점"))
                    mPlayerDetailInfoView.setDataList(
                        listOf(
                            totalData.totalGoal.toString(),
                            totalData.totalShoot.toString(),
                            totalData.totalEffectiveShoot.toString(),
                            String.format("%.2f", (item.totalData.totalSpRating/(item.playerDataList.size)))
                        )
                    )
                }
                AnalyticsFragment.ROW_TYPE.MATCH_RATING -> {
                    mBinding.txtAvgRating.text = String.format("%.2f", (item.totalData.totalSpRating/(item.playerDataList.size)))
                    when (item.position) {
                        ParentPositionEnum.F -> {
                            mPlayerDetailInfoView.setTitleList(listOf("슛", "유효 슛", "도움", "득점"))
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
                            mPlayerDetailInfoView.setTitleList(listOf("패스 시도", "패스 성공", "도움", "득점"))
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
                            mPlayerDetailInfoView.setTitleList(
                                listOf(
                                    "블락 성공",
                                    "태클 성공",
                                    "패스 시도",
                                    "패스 성공"
                                )
                            )
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
                            mPlayerDetailInfoView.setTitleList(
                                listOf(
                                    "블락 성공",
                                    "태클 성공",
                                    "패스 시도",
                                    "패스 성공"
                                )
                            )
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
            }
        }

        private fun setTextSize() {
            mPlayerDetailInfoView.setValueTextSize(23f)
            mPlayerDetailInfoView.setTitleTextSize(9f)
        }
    }
}