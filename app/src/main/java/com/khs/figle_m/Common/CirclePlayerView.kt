package com.khs.figle_m.Common

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.khs.figle_m.DB.PlayerDataBase
import com.khs.figle_m.R
import com.khs.figle_m.utils.DrawUtils
import com.khs.figle_m.utils.PositionEnum
import kotlinx.android.synthetic.main.cview_player_item_view.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CirclePlayerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    val TAG = this.javaClass.name
    val DEBUG = false

    fun initView(layoutResId: Int){
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(layoutResId, this)
    }

    fun updateView(spId:String, spRating: Int, isMVP: Boolean, spGrade: Int, goalCount: Int, spPosition: Int, imageUrl: String?){
        updatePlayerName(spId)
        DrawUtils().drawSeasonIcon(context, img_icon, spId)
        if(spRating >= 0) updateSpRateColor(spRating, isMVP)
        if(spGrade >= 0) updateGradeColor(spGrade)
        if(goalCount >= 0) addGoalIcon(goalCount)
        if(spPosition >= 0) updateSpPosition(spPosition)

        if (imageUrl != null) DrawUtils().drawPlayerImage(img_player, imageUrl)
        else DrawUtils().drawPlayerImage(img_player, "")
    }

    fun updatePlayerName(spId: String){
        val playerDB = PlayerDataBase.getInstance(context)
        playerDB.let {
            CoroutineScope(Dispatchers.IO).launch {
                val player = playerDB!!.playerDao().getPlayer(spId)
                player ?: return@launch
                CoroutineScope(Dispatchers.Main).launch {
                    txt_player_name.text = player.playerName
                }
            }
        }
    }

    fun updateSpRateColor(spRating: Int, isMVP:Boolean) {
        txt_rating.visibility = View.VISIBLE
        txt_rating.text = spRating.toString()
//        if (mMvpPlayer == null) {
//            if (spRating >= 8) {
//                txt_rating.background = context.getDrawable(R.drawable.rounded_player_team_mvp)
//            } else {
//                txt_rating.background = context.getDrawable(R.drawable.rounded_player)
//            }
//        } else {
            if (isMVP) {
                txt_rating.background = context.getDrawable(R.drawable.rounded_player_team_mvp)
            } else {
                txt_rating.background = context.getDrawable(R.drawable.rounded_player)
            }
//        }
    }

    fun updateGradeColor(spGrade : Int) {
        txt_player_spGrade.visibility = View.VISIBLE
        txt_player_spGrade.text = spGrade.toString()
        when(spGrade) {
            in 0..3 -> {
                txt_player_spGrade.background = context.getDrawable(R.drawable.player_grade_bronze)
            }
            in 4..7 -> {
                txt_player_spGrade.background = context.getDrawable(R.drawable.player_grade_silver)
            }
            in 8..10 -> {
                txt_player_spGrade.background = context.getDrawable(R.drawable.player_grade_gold)
            }
        }
    }

    fun addGoalIcon(goalCount: Int) {
        layout_goal.visibility = View.VISIBLE
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layout_goal.removeAllViews()
        if (goalCount == 0) {
            return
        }
        for(i in 1..goalCount) {
            val imageView: ImageView = inflater.inflate(R.layout.cview_card,layout_goal,false) as ImageView
            imageView.scaleType= ImageView.ScaleType.FIT_XY
            imageView.background = context.getDrawable(R.mipmap.icon_ball)

            var layoutParams= ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.leftMargin = i * 20
            layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
            imageView.layoutParams = layoutParams
            imageView.layoutParams.height = 50
            imageView.layoutParams.width = 50
            layout_goal.addView(imageView)
        }
    }

    fun updateSpPosition(spPosition:Int){
        for (positionItem in PositionEnum.values()) {
            if (positionItem.spposition == spPosition)
                txt_player_position.visibility = View.VISIBLE
                txt_player_position.text = positionItem.description
        }
    }
}