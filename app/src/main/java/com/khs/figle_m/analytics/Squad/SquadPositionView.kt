package com.khs.figle_m.analytics.Squad

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.khs.data.database.PlayerDataBase
import com.khs.data.database.entity.PlayerEntity
import com.khs.data.nexon_api.response.DTO.PlayerDTO
import com.khs.figle_m.R
import com.khs.figle_m.utils.LogUtil
import com.khs.figle_m.utils.PositionEnum
import com.khs.figle_m.databinding.CviewAnalyticsPositionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SquadPositionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    val TAG: String = javaClass.simpleName
    lateinit var mBinding : CviewAnalyticsPositionBinding
    init {
        initView(context)
    }

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = CviewAnalyticsPositionBinding.inflate(inflater, this, false)
    }

    fun updatePlayerInfo(playerDTO: PlayerDTO, positionEnum: PositionEnum, itemClick: (Pair<PlayerDTO, Boolean>) -> Unit) {
        this.setOnClickListener { itemClick(
            Pair(playerDTO, true)
        ) }
        mBinding.txtPlayerPosition.text = positionEnum.description
        mBinding.txtPlayerPosition.setBackgroundColor(ContextCompat.getColor(context, positionEnum.pointColor))
        updatePlayerName(playerDTO.spId)
        updatePlayerImage(playerDTO.imageUrl ?: "0")
        updatePlayerGrade(playerDTO.spGrade)
        updateSeason(playerDTO.spId)
    }

    private fun updatePlayerImage(url: String) {
        val imgView = findViewById<ImageView>(R.id.img_player)
        imgView ?: return

        if (url == "0") {
            Glide.with(context!!)
                .load(R.drawable.person_icon)
                .into(imgView)
            return
        }
        Glide.with(context!!)
            .load(Uri.parse(url))
            .placeholder(R.drawable.person_icon)
            .error(R.drawable.person_icon)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    LogUtil.dLog(LogUtil.TAG_UI, TAG,"onLoadFailed(...) GlideException!!! " + e!!)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    LogUtil.dLog(LogUtil.TAG_UI, TAG,"onResourceReady(...) $url")
                    return false
                }
            })
            .into(imgView)
    }

    private fun updatePlayerName(spId: Int) {
        val playerDB = PlayerDataBase.getInstance(context)
        playerDB?.let { playerDataBase ->
            CoroutineScope(Dispatchers.IO).launch {
                val player : PlayerEntity? = playerDataBase.playerDao().getPlayer(spId.toString())
                player ?: return@launch
                CoroutineScope(Dispatchers.Main).launch {
                    mBinding.txtPlayerName.text = player.playerName
                }
            }
        }
    }

    private fun updatePlayerGrade(spGrade : Int) {
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

    private fun updateSeason(spId: Int) {
        val seasonDB = PlayerDataBase.getInstance(context)
        seasonDB?.let {
            CoroutineScope(Dispatchers.IO).launch {
                var seasonId = spId.toString().substring(0,3)
                if ("224" == seasonId) seasonId = "234"
                val seasonEntity = it.seasonDao().getSeason(seasonId)
                LogUtil.dLog(LogUtil.TAG_SEARCH, TAG,"updateSeason > seasonEntity, seasonId : ${seasonEntity.seasonId} , className : ${seasonEntity.className} , saesonUrl : ${seasonEntity.seasonImg}  ")
                seasonEntity.let {
                    val url = seasonEntity.seasonImg
                    CoroutineScope(Dispatchers.Main).launch {
                        LogUtil.dLog(LogUtil.TAG_SEARCH, TAG,"updateSeason > saesonUrl : $url")
                        Glide.with(context)
                            .load(url)
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any,
                                    target: Target<Drawable>,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    LogUtil.dLog(LogUtil.TAG_SEARCH, TAG,"updateSeason > Season, onLoadFailed(...) GlideException!!! " + e!!)
                                    mBinding.analyticsImgIcon.visibility = View.GONE
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable,
                                    model: Any,
                                    target: Target<Drawable>,
                                    dataSource: DataSource,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    mBinding.analyticsImgIcon.visibility = View.VISIBLE
                                    LogUtil.dLog(LogUtil.TAG_SEARCH, TAG,"updateSeason > Season, onResourceReady(...) $url")
                                    return false
                                }
                            })
                            .into(mBinding.analyticsImgIcon)
                    }
                }
            }
        }
    }
}