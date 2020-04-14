package com.khs.figle_m.PlayerDetail

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.khs.figle_m.Base.BaseActivity
import com.khs.figle_m.DB.PlayerDataBase
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.R
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.utils.PositionEnum
import kotlinx.android.synthetic.main.fragment_player_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl

class PlayerDetailActivity: BaseActivity() {
    override fun initPresenter() {

    }

    val TAG: String = javaClass.name
    val DEBUG: Boolean = false
    open val TAG_PLAYER_DETAIL_DIALOG = "TAG_PLAYER_DETAIL_DIALOG"
    open val KEY_PLAYER_INFO = "KEY_PLAYER_INFO"

    companion object {
        @Volatile
        private var instance: PlayerDetailActivity? = null

        @JvmStatic
        fun getInstance(): PlayerDetailActivity =
            instance ?: synchronized(this) {
                instance
                    ?: PlayerDetailActivity().also {
                        instance = it
                    }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_player_detail)
    }

    override fun onStart() {
        super.onStart()
        initData()
        btn_back.setOnClickListener {
            finish()
        }
    }

    fun initData() {
        var playerDetailInfo: PlayerDTO? = null
        intent.let {
            playerDetailInfo = intent.getParcelableExtra(KEY_PLAYER_INFO) as PlayerDTO
        }

        playerDetailInfo ?: return

        runBlocking {
            launch {
                DataManager.getInstance().loadPlayerImage(playerDetailInfo!!.spId, {
                    updatePlayerImage(it)

                }, {
                    Log.v(TAG,"load Failed : $it")
                    img_player.setImageResource(R.drawable.ic_launcher_foreground)
                })
            }
        }

        player_info_view1.setTitleList(listOf("슛","유효 슛","어시스트","득점"))
        player_info_view1.setDataList(
            listOf(
                playerDetailInfo!!.status.shoot.toString(),
                playerDetailInfo!!.status.effectiveShoot.toString(),
                playerDetailInfo!!.status.assist.toString(),
                playerDetailInfo!!.status.goal.toString()
            )
        )

        player_info_view2.setTitleList(listOf("패스 시도","패스 성공","블락 성공","태클 성공"))
        player_info_view2.setDataList(listOf(
            playerDetailInfo!!.status.passTry.toString(),
            playerDetailInfo!!.status.passSuccess.toString(),
            playerDetailInfo!!.status.block.toString(),
            playerDetailInfo!!.status.tackle.toString()
        ))

        txt_player_spGrade.text = playerDetailInfo!!.spGrade.toString()
        when(playerDetailInfo!!.spGrade) {
            in 0..3 -> {
                txt_player_spGrade.background = applicationContext.getDrawable(R.drawable.player_grade_bronze)
            }
            in 4..7 -> {
                txt_player_spGrade.background = applicationContext.getDrawable(R.drawable.player_grade_silver)
            }
            in 8..10 -> {
                txt_player_spGrade.background = applicationContext.getDrawable(R.drawable.player_grade_gold)
            }
        }

        val playerDB = PlayerDataBase.getInstance(applicationContext)
        playerDB.let {
            CoroutineScope(Dispatchers.IO).launch {
                val player = playerDB!!.playerDao().getPlayer(playerDetailInfo!!.spId.toString())
                CoroutineScope(Dispatchers.Main).launch {
                    txt_player_name.text = player.playerName
                }
            }
        }

        txt_player_rating.text = playerDetailInfo!!.status.spRating.toString()
        for (positionItem in PositionEnum.values()) {
            if (positionItem.spposition.equals(playerDetailInfo!!.spPosition))
                txt_player_position.text = positionItem.description
        }
    }

    fun updatePlayerImage(url: HttpUrl) {
        val imgView = findViewById<ImageView>(R.id.img_player)
        imgView ?: return
        Log.v(TAG,"updatePlayerImage(...) uri : ${url}")
        Glide.with(applicationContext)
            .load(Uri.parse(url.toString()))
            .placeholder(R.drawable.person_icon)
            .error(R.drawable.person_icon)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d(TAG, "onLoadFailed(...) GlideException!!! " + e!!)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d(TAG, "onResourceReady(...) $url")
                    return false
                }
            })
            .into(imgView)
    }
}