package com.example.figle_m.SearchList.SearchDetailView.customView

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.figle_m.DB.PlayerDataBase
import com.example.figle_m.R
import com.example.figle_m.Response.DTO.PlayerDTO
import kotlinx.android.synthetic.main.cview_player_info.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PlayerInfoView : ConstraintLayout {
    val TAG = javaClass.name

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_player_info, this)
    }

    fun updateView(player: PlayerDTO, assistList: List<Int>, teamBlockList: List<Int>) {
        var totalAssistCount = 0
        var totalBlockCount = 0
        for (item in assistList) {
            totalAssistCount += item
        }
        for (item in teamBlockList) {
            totalBlockCount += item
        }
        val data: ArrayList<Double> = arrayListOf()
        chart_view.setCount(5)

        val passRate =
            (player.status.passSuccess.toDouble() / player.status.passTry.toDouble()) * 100
        val goalRate = if (player.status.shoot == 0) {
            0.0
        } else {
            (player.status.effectiveShoot.toDouble() / player.status.shoot.toDouble()) * 100
        }
        val dribble = player.status.dribble
        val block = if (player.status.block == 0 || totalBlockCount == 0) {
            20.0
        } else {
            (player.status.block.toDouble() / totalBlockCount.toDouble()) * 100
        }
        val spRate = player.status.spRating
        val assistRate = if (player.status.assist == 0 || totalAssistCount == 0) {
            0.0
        } else {
            (player.status.assist.toDouble() / totalAssistCount.toDouble()) * 100
        }


        val playerDB = PlayerDataBase.getInstance(context)
        playerDB.let {
            CoroutineScope(Dispatchers.IO).launch {
                val player = playerDB!!.playerDao().getPlayer(player.spId.toString())
                CoroutineScope(Dispatchers.Main).launch {
                    txt_name.text = player.playerName
                }
            }
        }

        txt_spRate.text = "평점 : " + spRate.toString()
        txt_goalRate.text = "유효슈팅 : " + String.format("%.2f",goalRate)
        txt_assistRate.text = "도움 : " + String.format("%.2f",assistRate)
        txt_passRate.text = "패스 : " + String.format("%.2f",passRate)
        txt_blockRate.text = "수비 : " + String.format("%.2f",block)


        chart_view.setCoverColor(resources.getColor(R.color.chart_cover, null))
        chart_view.setCoverAlpha(180)
        chart_view.setLineColor(resources.getColor(R.color.chart_line, null))
        chart_view.setPolygonColor(resources.getColor(R.color.chart_polygon, null))
        chart_view.setTextSize(30)
        chart_view.setTextColor(resources.getColor(R.color.search_detail_dialog_player_circle, null))
        chart_view.changeTitles(arrayOf("평점", "유효슈팅", "도움", "패스", "수비"))
        data.add((spRate * 10).toDouble())
        data.add(String.format("%.2f", goalRate).toDouble())
        data.add(String.format("%.2f", assistRate).toDouble())
        data.add(String.format("%.2f", passRate).toDouble())
        data.add(String.format("%.2f", block).toDouble())
        chart_view.setData(data)
        Log.v(TAG, "data : $data")
        Log.v(TAG, "player : $player")
    }
}