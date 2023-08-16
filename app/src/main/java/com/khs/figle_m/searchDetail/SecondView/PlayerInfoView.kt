package com.khs.figle_m.searchDetail.SecondView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.data.nexon_api.response.DTO.PlayerDTO
import com.khs.figle_m.searchDetail.SearchDetailDialogFragment
import com.khs.figle_m.databinding.CviewPlayerInfoBinding


class PlayerInfoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private val TAG = javaClass.simpleName
    private var mBinding : CviewPlayerInfoBinding
    lateinit  var mPlayerDTO : PlayerDTO
    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = CviewPlayerInfoBinding.inflate(inflater, this, true)
        mBinding.btnDetail.setOnClickListener {
            SearchDetailDialogFragment.getInstance().showPlayerDetailFragment(mPlayerDTO)
        }
    }

    fun updateView(player: PlayerDTO, assistList: List<Int>, teamBlockList: List<Int>) {
//        var seasonId = player.spId.toString().substring(0,3)
//        if("224".equals(seasonId)) {
//            player.spId = player.spId.toString().replaceRange(0 .. 2,"234").toInt()
//            seasonId = "234"
//        }
//        mPlayerDTO = player
//        var totalAssistCount = 0
//        var totalBlockCount = 0
//        for (item in assistList) {
//            totalAssistCount += item
//        }
//        for (item in teamBlockList) {
//            totalBlockCount += item
//        }
//        val data: ArrayList<Double> = arrayListOf()
//        chart_view.setCount(5)
//
//        val passRate = if (player.status.passSuccess == 0 || player.status.passTry == 0) {
//            0.0
//        } else {
//            (player.status.passSuccess.toDouble() / player.status.passTry.toDouble()) * 100
//        }
//        val goalRate = if (player.status.shoot == 0) {
//            0.0
//        } else {
//            (player.status.effectiveShoot.toDouble() / player.status.shoot.toDouble()) * 100
//        }
//        val dribble = player.status.dribble
//        val block = if (player.status.block == 0 || totalBlockCount == 0) {
//            20.0
//        } else {
//            (player.status.block.toDouble() / totalBlockCount.toDouble()) * 100
//        }
//        val spRate = player.status.spRating
//        val assistRate = if (player.status.assist == 0 || totalAssistCount == 0) {
//            0.0
//        } else {
//            (player.status.assist.toDouble() / totalAssistCount.toDouble()) * 100
//        }
//
//
//        val playerDB = PlayerDataBase.getInstance(context)
//        playerDB?.let { playerDataBase ->
//            CoroutineScope(Dispatchers.IO).launch {
//                val player : PlayerEntity? = playerDataBase.playerDao().getPlayer(player.spId.toString())
//                CoroutineScope(Dispatchers.Main).launch {
//                    LogUtil.dLog(LogUtil.TAG_UI, TAG,"player : ${player?.playerName}")
//                    txt_name.text = player?.playerName ?: ""
//                }
//            }
//        }
//
//        txt_spRate.text = "평점 : " + spRate.toString()
//        txt_goalRate.text = "유효슈팅(성공률) : " + String.format("%.1f",goalRate) + "%"
//        txt_assistRate.text = "팀내 도움률 : " + String.format("%.1f",assistRate) + "%"
//        txt_passRate.text = "패스(성공률) : " + String.format("%.1f",passRate) + "%"
////        txt_blockRate.text = "블락 / 태클 : " + player.status.block + " / " + player.status.tackle
//
//        chart_view.setCoverColor(resources.getColor(R.color.chart_cover, null))
//        chart_view.setCoverAlpha(180)
//        chart_view.setLineColor(resources.getColor(R.color.chart_line, null))
//        chart_view.setPolygonColor(resources.getColor(R.color.chart_polygon, null))
//        chart_view.setTextSize(30)
//        chart_view.setTextColor(resources.getColor(R.color.search_detail_dialog_player_circle, null))
//        chart_view.changeTitles(arrayOf("평점", "유효슈팅", "도움", "패스", "수비"))
//        data.add((spRate * 10).toDouble())
//        data.add(String.format("%.2f", goalRate).toDouble())
//        data.add(String.format("%.2f", assistRate).toDouble())
//        data.add(String.format("%.2f", passRate).toDouble())
//        data.add(String.format("%.2f", block).toDouble())
//        chart_view.setData(data)
//        LogUtil.vLog(LogUtil.TAG_UI, TAG,"data : $data")
//        LogUtil.vLog(LogUtil.TAG_UI, TAG,"player : $player")
    }
}