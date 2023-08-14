package com.khs.figle_m.Common

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
import com.khs.figle_m.Utils.DrawUtils
import com.khs.figle_m.Utils.PositionEnum
import com.khs.figle_m.databinding.CviewCardBinding
import com.khs.figle_m.databinding.CviewPlayerItemViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CirclePlayerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    val TAG = this.javaClass.simpleName
    val DEBUG = BuildConfig.DEBUG
    lateinit var mBinding : CviewPlayerItemViewBinding

    fun initView(layoutResId: Int){
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = CviewPlayerItemViewBinding.inflate(inflater, this, true)
    }

    fun updateView(spId:String, spRating: Int, isMVP: Boolean, spGrade: Int, goalCount: Int, spPosition: Int, imageUrl: String?){
        updatePlayerName(spId)
        DrawUtils().drawSeasonIcon(context, mBinding.imgIcon, spId)
        if(spRating >= 0) updateSpRateColor(spRating, isMVP)
        if(spGrade >= 0) updateGradeColor(spGrade)
        if(goalCount >= 0) addGoalIcon(goalCount)
        if(spPosition >= 0) updateSpPosition(spPosition)

        if (imageUrl != null) DrawUtils().drawPlayerImage(mBinding.imgPlayer, imageUrl)
        else DrawUtils().drawPlayerImage(mBinding.imgPlayer, "")
    }

    fun updatePlayerName(spId: String){
        val playerDB = PlayerDataBase.getInstance(context)
        playerDB?.let {
            CoroutineScope(Dispatchers.IO).launch {
                val player : PlayerEntity? = it.playerDao().getPlayer(spId)
                player ?: return@launch
                CoroutineScope(Dispatchers.Main).launch {
                    mBinding.txtPlayerName.text = player.playerName
                }
            }
        }
    }

    fun updateSpRateColor(spRating: Int, isMVP:Boolean) {
        mBinding.txtRating.visibility = View.VISIBLE
        mBinding.txtRating.text = spRating.toString()
//        if (mMvpPlayer == null) {
//            if (spRating >= 8) {
//                txt_rating.background = context.getDrawable(R.drawable.rounded_player_team_mvp)
//            } else {
//                txt_rating.background = context.getDrawable(R.drawable.rounded_player)
//            }
//        } else {
            if (isMVP) {
                mBinding.txtRating.background = context.getDrawable(R.drawable.rounded_player_team_mvp)
            } else {
                mBinding.txtRating.background = context.getDrawable(R.drawable.rounded_player)
            }
//        }
    }

    fun updateGradeColor(spGrade : Int) {
        mBinding.txtPlayerSpGrade.visibility = View.VISIBLE
        mBinding.txtPlayerSpGrade.text = spGrade.toString()
        when(spGrade) {
            in 0..3 -> {
                mBinding.txtPlayerSpGrade.background = context.getDrawable(R.drawable.player_grade_bronze)
            }
            in 4..7 -> {
                mBinding.txtPlayerSpGrade.background = context.getDrawable(R.drawable.player_grade_silver)
            }
            in 8..10 -> {
                mBinding.txtPlayerSpGrade.background = context.getDrawable(R.drawable.player_grade_gold)
            }
        }
    }

    fun addGoalIcon(goalCount: Int) {
        mBinding.layoutGoal.visibility = View.VISIBLE
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding.layoutGoal.removeAllViews()
        if (goalCount == 0) {
            return
        }
        for(i in 1..goalCount) {
            val binding : CviewCardBinding = CviewCardBinding.inflate(inflater, mBinding.layoutGoal, false)
            binding.imgCard.apply {
                binding.imgCard.scaleType= ImageView.ScaleType.FIT_XY
                background = context.getDrawable(R.mipmap.icon_ball)
                layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    leftMargin = i * 20
                    leftToLeft = LayoutParams.PARENT_ID
                }
                layoutParams.height = 50
                layoutParams.width = 50
                mBinding.layoutGoal.addView(this)
            }
        }
    }

    private fun updateSpPosition(spPosition:Int){
        for (positionItem in PositionEnum.values()) {
            if (positionItem.spposition == spPosition) {
                mBinding.txtPlayerPosition.visibility = View.VISIBLE
                mBinding.txtPlayerPosition.text = positionItem.description
            }
        }
    }
}