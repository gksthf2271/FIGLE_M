package com.example.figle_m.SearchList.SearchDetailView.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.figle_m.R
import com.example.figle_m.Response.DTO.PlayerDTO
import com.example.figle_m.Response.MatchDetailResponse
import kotlinx.android.synthetic.main.cview_player_info.view.*


class PlayerInfoView : ConstraintLayout{
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_player_info, this)
    }

    fun setUpData(player: PlayerDTO) {
        chart_view.setCount(5)
        player.status.spRating

        player.status.assist

        player.status.block

        player.status.dribble

        player.status.shoot
        player.status.effectiveShoot
        player.status.goal

        player.status.passSuccess
        player.status.passTry

        player.status.tackle
        val data:ArrayList<Double> = arrayListOf()
        data.add(80.0)
        data.add(90.0)
        data.add(70.0)
        data.add(30.0)
        data.add(60.0)
        data.add(30.0)
        data.add(60.0)
        chart_view.setData(data)
        chart_view.changeTitles(arrayOf("Math", "English", "Chinese", "Physical", "Biological"))
    }
}