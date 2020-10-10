package com.khs.figle_m.Analytics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khs.figle_m.DB.PlayerDataBase
import com.khs.figle_m.PlayerDetail.PlayerDetailInfoView
import com.khs.figle_m.R
import com.khs.figle_m.utils.DrawUtils
import com.khs.figle_m.utils.PositionEnum
import kotlinx.android.synthetic.main.item_analytics.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnalyticsRecyclerViewAdapter(context: Context, rowType: AnalyticsFragment.ROW_TYPE , playerList : List<AnalyticsPlayer>, val itemClick : (AnalyticsPlayer) -> Unit)
    : RecyclerView.Adapter<AnalyticsRecyclerViewAdapter.ViewHolder>() {
    private val TAG: String = javaClass.name
    val DEBUG = false
    val mContext: Context
    val mPlayerInfoList: List<AnalyticsPlayer>?
    val mRowType: AnalyticsFragment.ROW_TYPE

    init {
        mContext = context
        mPlayerInfoList = playerList
        mRowType = rowType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_analytics, parent, false)
        viewHolder = ViewHolder(view, itemClick)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mPlayerInfoList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mPlayerInfoList!!.get(position),mContext)
    }

    inner class ViewHolder(itemView: View, itemClick: (AnalyticsPlayer) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val TAG: String = javaClass.name
        var mItemView: View
        var mPlayerDetailInfoView : PlayerDetailInfoView
        init {
            mItemView = itemView
            mPlayerDetailInfoView = mItemView.player_info_view
        }

        fun bind(item: AnalyticsPlayer, context: Context) {
            setTextSize()

            DrawUtils().drawSeasonIcon(context, mItemView.analytics_img_icon, item.spId.toString())
            DrawUtils().drawPlayerImage(mItemView.analytics_img_player, item.imageResUrl)
            var positionSet = mutableSetOf<String>()


            for (playerDTO in item.playerDataList) {
                for (position in PositionEnum.values()) {
                    if (playerDTO.spPosition == position.spposition) {
                        positionSet.add(position.description)
                    }
                }
            }

            positionSet.let { mItemView.txt_player_position.text = positionSet.toString() }
            showRatingView(context, item)
            mItemView.txt_player_name.apply {
                val playerDB = PlayerDataBase.getInstance(context)
                playerDB.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        val player = playerDB!!.playerDao().getPlayer(item.spId.toString())
                        player ?: return@launch
                        CoroutineScope(Dispatchers.Main).launch {
                            this@apply.text = player.playerName
                        }
                    }
                }
            }

            initInfoView(item)

            mItemView.setOnClickListener() {
                itemClick(item)
            }
        }

        fun showRatingView(context: Context, item : AnalyticsPlayer){
            var index = mPlayerInfoList!!.indexOf(item) + 1
            if (index == 1) {
                mItemView.txt_rating.background = context.getDrawable(R.drawable.rounded_player_team_mvp)
            } else {
                mItemView.txt_rating.background = context.getDrawable(R.drawable.rounded_player)
            }
            mItemView.txt_rating.text = mPlayerInfoList.let { (index).toString()}
        }

        fun initInfoView(item : AnalyticsPlayer) {
            var totalData = item.totalData
            when(mRowType){
                AnalyticsFragment.ROW_TYPE.ASSIST -> {
                    mItemView.txt_avgRating.visibility = View.GONE
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
                    mItemView.txt_avgRating.visibility = View.GONE
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
                    mItemView.txt_avgRating.text = String.format("%.2f", (item.totalData.totalSpRating/(item.playerDataList.size)))
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
                    }
                }
            }
        }

        fun setTextSize() {
            mPlayerDetailInfoView.setValueTextSize(23f)
            mPlayerDetailInfoView.setTitleTextSize(9f)
        }
    }
}