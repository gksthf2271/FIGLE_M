package com.khs.figle_m.PlayerDetail

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.khs.figle_m.DB.PlayerDataBase
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.MainActivity
import com.khs.figle_m.R
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Response.DTO.RankerPlayerDTO
import com.khs.figle_m.SearchDetail.SearchDetailContract
import com.khs.figle_m.Utils.*
import kotlinx.android.synthetic.main.fragment_player_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PlayerDetailDialogFragment: DialogBaseFragment(), SearchDetailContract.View  {
    val TAG: String = javaClass.simpleName
    val DEBUG: Boolean = false
    open val TAG_PLAYER_DETAIL_DIALOG = "TAG_PLAYER_DETAIL_DIALOG"
    open val KEY_PLAYER_INFO = "KEY_PLAYER_INFO"
    open val KEY_RANKER_PLAYER_INFO = "KEY_RANKER_PLAYER_INFO"

    lateinit var mPlayerInfo : PlayerDTO

    companion object {
        @Volatile
        private var instance: PlayerDetailDialogFragment? = null

        @JvmStatic
        fun getInstance(): PlayerDetailDialogFragment =
            instance ?: synchronized(this) {
                instance
                    ?: PlayerDetailDialogFragment().also {
                        instance = it
                    }
            }
    }

    override fun initPresenter() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_player_detail, container, false)
        return v
    }

    override fun onResume() {
        super.onResume()
        if (!NetworkUtils().checkNetworkStatus(context!!)) {
            dismiss()
            (activity as MainActivity).showErrorPopup(DataManager().ERROR_NETWORK_DISCONNECTED, false)
            return
        }
        resizeDialog()
        setBackgroundColorDialog()
    }

    private fun resizeDialog(){
        context ?: return
        val size = DisplayUtils().getDisplaySize(context!!)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        val deviceeHeight = size.y
        params?.width = (deviceWidth * 0.9).toInt()
        params?.height = (deviceeHeight * 0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    private fun setBackgroundColorDialog() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onStart() {
        super.onStart()
        initData()
        btn_back.setOnClickListener {
            dismiss()
        }
    }

    private fun initData() {
        var playerDetailInfo: PlayerDTO? = null
        var rankerPlayerInfo: List<RankerPlayerDTO>? = null
        arguments.let {
            playerDetailInfo = arguments!!.get(KEY_PLAYER_INFO) as PlayerDTO
            rankerPlayerInfo = arguments!!.get(KEY_RANKER_PLAYER_INFO) as List<RankerPlayerDTO>
        }

        playerDetailInfo ?: return
        mPlayerInfo = playerDetailInfo!!

        if (rankerPlayerInfo !=null && !rankerPlayerInfo!!.isEmpty()) {
            chart_ranker.setData(playerDetailInfo,rankerPlayerInfo!!.get(0))
        }

        val seasonId = playerDetailInfo!!.spId.toString().substring(0, 3)
        var seasonName: String? = null
        for (item in SeasonEnum.values()) {
            if (item.seasonId.toString().equals(seasonId))
                seasonName = item.className
        }
        if (seasonName == null) {
            LogUtil.vLog(LogUtil.TAG_UI, TAG,"seasonName is null")
            return
        }

        updatePlayerImage(mPlayerInfo.imageUrl!!)

//                DataManager.getInstance().loadPlayerImage(playerDetailInfo!!.spId, {
//                    updatePlayerImage(playerDetailInfo!!, it)
//
//                }, {
//                    LogUtil.vLog(LogUtil.TAG_UI, TAG,"load Failed : $it")
//                    img_player.setImageResource(R.drawable.ic_launcher_foreground)
//                })

        updateIcon(context!!, playerDetailInfo!!)

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
                txt_player_spGrade.background = context!!.getDrawable(R.drawable.player_grade_bronze)
            }
            in 4..7 -> {
                txt_player_spGrade.background = context!!.getDrawable(R.drawable.player_grade_silver)
            }
            in 8..10 -> {
                txt_player_spGrade.background = context!!.getDrawable(R.drawable.player_grade_gold)
            }
        }

        val playerDB = PlayerDataBase.getInstance(context!!)
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
            if (positionItem.spposition.equals(playerDetailInfo!!.spPosition)) {
                txt_player_position.text = positionItem.description

                when(positionItem.spposition) {
                    0 -> {
                        txt_player_position.setTextColor(resources.getColor(R.color.gk_color,null))
                    }
                    in 1..8 -> {
                        txt_player_position.setTextColor(resources.getColor(R.color.defence_color,null))
                    }
                    in 9..19 -> {
                        txt_player_position.setTextColor(resources.getColor(R.color.midfielder_color,null))
                    }
                    in 20..27 -> {
                        txt_player_position.setTextColor(resources.getColor(R.color.forward_color,null))
                    }
                    else -> {
                        txt_player_position.setTextColor(resources.getColor(R.color.sub_color,null))
                    }
                }
            }
        }
    }

    private fun updatePlayerImage(url: String) {
        val imgView = view!!.findViewById<ImageView>(R.id.img_player)
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

    private fun updateIcon(context: Context, item:PlayerDTO) {
        val seasonDB = PlayerDataBase.getInstance(context)
        seasonDB.let {
            CoroutineScope(Dispatchers.IO).launch {
                var seasonId = item.spId.toString().substring(0,3)
                //Todo 224, 234 분리... 뭐가 맞는지 넥슨측확인 필요 // 답변완료 : 234가 맞음
                if ("224".equals(seasonId)) seasonId = "234"
                val seasonEntity = seasonDB!!.seasonDao().getSeason(seasonId)
                LogUtil.vLog(LogUtil.TAG_UI, TAG,"seasonEntity : ${seasonEntity.className}")
                seasonEntity.let {
                    val url = seasonEntity.seasonImg
                    CoroutineScope(Dispatchers.Main).launch {
                        LogUtil.dLog(LogUtil.TAG_UI, TAG,"TEST, saesonUrl : ${url}")
                        Glide.with(context)
                            .load(url)
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any,
                                    target: Target<Drawable>,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    LogUtil.dLog(LogUtil.TAG_UI, TAG,"Season, onLoadFailed(...) GlideException!!! " + e!!)
                                    img_player_icon.visibility = View.GONE
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable,
                                    model: Any,
                                    target: Target<Drawable>,
                                    dataSource: DataSource,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    img_player_icon.visibility = View.VISIBLE
                                    LogUtil.dLog(LogUtil.TAG_UI, TAG,"Season, onResourceReady(...) $url")
                                    return false
                                }
                            })
                            .into(img_player_icon)
                    }
                }
            }
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showPlayerImage(accessId: String, playerDTO: PlayerDTO, size: Int) {

    }

    override fun showPlayerDetailDialogFragment(
        playerDTO: PlayerDTO,
        rankerPlayerDTOList: List<RankerPlayerDTO>
    ) {
    }

    override fun showError(error: Int) {
        dismiss()
        (activity as MainActivity).showErrorPopup(error)
    }
}