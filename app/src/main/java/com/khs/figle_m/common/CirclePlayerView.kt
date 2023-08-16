package com.khs.figle_m.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.data.database.PlayerDataBase
import com.khs.data.database.entity.PlayerEntity
import com.khs.figle_m.BuildConfig
import com.khs.figle_m.R
import com.khs.figle_m.utils.DrawUtils
import com.khs.figle_m.utils.PositionEnum
import com.khs.figle_m.databinding.CviewCardBinding
import com.khs.figle_m.databinding.CviewTradePlayerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CirclePlayerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    val TAG = this.javaClass.simpleName
    val DEBUG = BuildConfig.DEBUG
    lateinit var mTradePlayerBinding : CviewTradePlayerBinding

    fun initView(){
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mTradePlayerBinding = CviewTradePlayerBinding.inflate(inflater, this, true)
    }

    fun updateView(spId:String, spRating: Int, isMVP: Boolean, spGrade: Int, goalCount: Int, spPosition: Int, imageUrl: String?){
        updatePlayerName(spId)
        DrawUtils().drawSeasonIcon(context, mTradePlayerBinding.imgIcon, spId)
        if(spRating >= 0) updateSpRateColor(spRating, isMVP)
        if(spGrade >= 0) updateGradeColor(spGrade)
        if(goalCount >= 0) addGoalIcon(goalCount)
        if(spPosition >= 0) updateSpPosition(spPosition)

        if (imageUrl != null) DrawUtils().drawPlayerImage(mTradePlayerBinding.imgPlayer, imageUrl)
        else DrawUtils().drawPlayerImage(mTradePlayerBinding.imgPlayer, "")
    }

    fun updatePlayerName(spId: String){
        val playerDB = PlayerDataBase.getInstance(context)
        playerDB?.let {
            CoroutineScope(Dispatchers.IO).launch {
                val player : PlayerEntity? = it.playerDao().getPlayer(spId)
                player ?: return@launch
                CoroutineScope(Dispatchers.Main).launch {
                    mTradePlayerBinding.txtPlayerName.text = player.playerName
                }
            }
        }
    }

    fun updateSpRateColor(spRating: Int, isMVP:Boolean) {
        mTradePlayerBinding.txtRating.visibility = View.VISIBLE
        mTradePlayerBinding.txtRating.setText(spRating.toString())
//        if (mMvpPlayer == null) {
//            if (spRating >= 8) {
//                txt_rating.background = context.getDrawable(R.drawable.rounded_player_team_mvp)
//            } else {
//                txt_rating.background = context.getDrawable(R.drawable.rounded_player)
//            }
//        } else {
            if (isMVP) {
                mTradePlayerBinding.txtRating.background = context.getDrawable(R.drawable.rounded_player_team_mvp)
            } else {
                mTradePlayerBinding.txtRating.background = context.getDrawable(R.drawable.rounded_player)
            }
//        }
    }

    fun updateGradeColor(spGrade : Int) {
        mTradePlayerBinding.txtPlayerSpGrade.visibility = View.VISIBLE
        mTradePlayerBinding.txtPlayerSpGrade.text = spGrade.toString()
        when(spGrade) {
            in 0..3 -> {
                mTradePlayerBinding.txtPlayerSpGrade.background = context.getDrawable(R.drawable.player_grade_bronze)
            }
            in 4..7 -> {
                mTradePlayerBinding.txtPlayerSpGrade.background = context.getDrawable(R.drawable.player_grade_silver)
            }
            in 8..10 -> {
                mTradePlayerBinding.txtPlayerSpGrade.background = context.getDrawable(R.drawable.player_grade_gold)
            }
        }
    }

    fun addGoalIcon(goalCount: Int) {
        mTradePlayerBinding.layoutGoal.visibility = View.VISIBLE
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mTradePlayerBinding.layoutGoal.removeAllViews()
        if (goalCount == 0) {
            return
        }
        for(i in 1..goalCount) {
            val binding : CviewCardBinding = CviewCardBinding.inflate(inflater, mTradePlayerBinding.layoutGoal, false)
            binding.imgCard.apply {
                binding.imgCard.scaleType= ImageView.ScaleType.FIT_XY
                background = context.getDrawable(R.mipmap.icon_ball)
                layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    leftMargin = i * 20
                    leftToLeft = LayoutParams.PARENT_ID
                }
                layoutParams.height = 50
                layoutParams.width = 50
                mTradePlayerBinding.layoutGoal.addView(this)
            }
        }
    }

    private fun updateSpPosition(spPosition:Int){
        for (positionItem in PositionEnum.values()) {
            if (positionItem.spposition == spPosition) {
                mTradePlayerBinding.txtPlayerPosition.visibility = View.VISIBLE
                mTradePlayerBinding.txtPlayerPosition.text = positionItem.description
            }
        }
    }
}