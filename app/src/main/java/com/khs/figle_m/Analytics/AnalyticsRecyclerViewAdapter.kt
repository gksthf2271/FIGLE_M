package com.khs.figle_m.Analytics

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khs.figle_m.DB.PlayerDataBase
import com.khs.figle_m.PlayerDetail.PlayerDetailInfoView
import com.khs.figle_m.R
import com.khs.figle_m.utils.DrawUtils
import kotlinx.android.synthetic.main.cview_player_detail_bottom.view.*
import kotlinx.android.synthetic.main.item_analytics.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnalyticsRecyclerViewAdapter(context: Context, playerList : List<AnalyticsPlayer>, val itemClick : (AnalyticsPlayer) -> Unit)
    : RecyclerView.Adapter<AnalyticsRecyclerViewAdapter.ViewHolder>() {
    private val TAG: String = javaClass.name
    val DEBUG = false
    val mContext: Context
    val mPlayerInfoList: List<AnalyticsPlayer>?

    init {
        mContext = context
        mPlayerInfoList = playerList
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
            resizeView()
            var totalData = item.totalData

            DrawUtils().drawSeasonIcon(context, mItemView.analytics_img_icon, item.spId.toString())
            DrawUtils().drawPlayerImage(mItemView.analytics_img_player, item.imageResUrl)

            mItemView.txt_rating.text = mPlayerInfoList.let { (mPlayerInfoList!!.indexOf(item) + 1).toString()}
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

            when(item.position) {
                ParentPositionEnum.F -> {
                    mPlayerDetailInfoView.setTitleList(listOf("슛","유효 슛","어시스트","득점"))
                    mPlayerDetailInfoView.setDataList(listOf(totalData.totalShoot.toString(), totalData.totalEffectiveShoot.toString(), totalData.totalAssist.toString(), totalData.totalGoal.toString()))
                }
                ParentPositionEnum.M -> {
                    mPlayerDetailInfoView.setTitleList(listOf("패스 시도","패스 성공","어시스트","득점"))
                    mPlayerDetailInfoView.setDataList(listOf(totalData.totalPassTry.toString(), totalData.totalPassSuccess.toString(), totalData.totalAssist.toString(), totalData.totalGoal.toString()))
                }
                ParentPositionEnum.D -> {
                    mPlayerDetailInfoView.setTitleList(listOf("블락 성공","태클 성공","패스 시도","패스 성공"))
                    mPlayerDetailInfoView.setDataList(listOf(totalData.totalBlock.toString(), totalData.totalTackle.toString(), totalData.totalPassTry.toString(), totalData.totalPassSuccess.toString()))
                }
                ParentPositionEnum.GK -> {
                    mPlayerDetailInfoView.setTitleList(listOf("블락 성공","태클 성공","패스 시도","패스 성공"))
                    mPlayerDetailInfoView.setDataList(listOf(totalData.totalBlock.toString(), totalData.totalTackle.toString(), totalData.totalPassTry.toString(), totalData.totalPassSuccess.toString()))
                }
            }
            mItemView.setOnClickListener() {
                itemClick(item)
            }
        }

        fun resizeView() {
            mPlayerDetailInfoView.txt_title_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9f)
            mPlayerDetailInfoView.txt_title_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9f)
            mPlayerDetailInfoView.txt_title_3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9f)
            mPlayerDetailInfoView.txt_title_4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9f)
        }
    }
}