package com.khs.figle_m.playerDetail

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.khs.data.database.PlayerDataBase
import com.khs.data.database.entity.PlayerEntity
import com.khs.data.nexon_api.response.DTO.PlayerDTO
import com.khs.data.nexon_api.response.DTO.RankerPlayerDTO
import com.khs.figle_m.data.DataManager
import com.khs.figle_m.MainActivity
import com.khs.figle_m.R
import com.khs.figle_m.searchDetail.SearchDetailContract
import com.khs.figle_m.utils.DisplayUtils
import com.khs.figle_m.utils.LogUtil
import com.khs.figle_m.utils.NetworkUtils
import com.khs.figle_m.utils.PositionEnum
import com.khs.figle_m.databinding.FragmentPlayerDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PlayerDetailDialogFragment: DialogBaseFragment(), SearchDetailContract.View  {
    val TAG: String = javaClass.simpleName
    val DEBUG: Boolean = false
    val TAG_PLAYER_DETAIL_DIALOG = "TAG_PLAYER_DETAIL_DIALOG"
    val KEY_PLAYER_INFO = "KEY_PLAYER_INFO"
    val KEY_RANKER_PLAYER_INFO = "KEY_RANKER_PLAYER_INFO"

    lateinit var mPlayerInfo : PlayerDTO
    lateinit var mBinding : FragmentPlayerDetailBinding

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
        mBinding = FragmentPlayerDetailBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        try {
            if (!NetworkUtils().checkNetworkStatus(requireContext())) {
                dismiss()
                (activity as MainActivity).showErrorPopup(DataManager.ERROR_NETWORK_DISCONNECTED, false)
                return
            }
        } catch (_: Exception) { }
        resizeDialog()
        setBackgroundColorDialog()
    }

    private fun resizeDialog(){
        try {
            val size = DisplayUtils.getDisplaySize(requireContext())
            val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
            val deviceWidth = size.x
            val deviceHeight = size.y
            params?.width = (deviceWidth * 0.9).toInt()
            params?.height = (deviceHeight * 0.9).toInt()
            dialog?.window?.attributes = params as WindowManager.LayoutParams
        } catch (_: Exception) {

        }
    }

    private fun setBackgroundColorDialog() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onStart() {
        super.onStart()
        initData()
        mBinding.btnBack.setOnClickListener {
            dismiss()
        }
    }

    private fun initData() {
        var playerDetailInfo: PlayerDTO? = null
        var rankerPlayerInfo: List<RankerPlayerDTO>? = null
        arguments?.let {
            playerDetailInfo = it.get(KEY_PLAYER_INFO) as PlayerDTO
            rankerPlayerInfo = it.get(KEY_RANKER_PLAYER_INFO) as List<RankerPlayerDTO>
        }

        playerDetailInfo ?: return
        mPlayerInfo = playerDetailInfo!!

        if (rankerPlayerInfo != null && rankerPlayerInfo!!.isNotEmpty()) {
            mBinding.chartRanker.setData(playerDetailInfo, rankerPlayerInfo!![0])
        }

        updatePlayerImage(mPlayerInfo.imageUrl ?: "0")

        try {
            updateIcon(requireContext(), playerDetailInfo!!)
        } catch (_:Exception) {}


        mBinding.playerInfoView1.setTitleList(listOf("슛","유효 슛","어시스트","득점"))
        mBinding.playerInfoView1.setDataList(
            listOf(
                playerDetailInfo!!.status.shoot.toString(),
                playerDetailInfo!!.status.effectiveShoot.toString(),
                playerDetailInfo!!.status.assist.toString(),
                playerDetailInfo!!.status.goal.toString()
            )
        )

        mBinding.playerInfoView2.setTitleList(listOf("패스 시도","패스 성공","블락 성공","태클 성공"))
        mBinding.playerInfoView2.setDataList(listOf(
            playerDetailInfo!!.status.passTry.toString(),
            playerDetailInfo!!.status.passSuccess.toString(),
            playerDetailInfo!!.status.block.toString(),
            playerDetailInfo!!.status.tackle.toString()
        ))

        mBinding.txtPlayerSpGrade.text = playerDetailInfo!!.spGrade.toString()
        try {
            when(playerDetailInfo!!.spGrade) {
                in 0..3 -> {
                    mBinding.txtPlayerSpGrade.background = requireContext().getDrawable(R.drawable.player_grade_bronze)
                }
                in 4..7 -> {
                    mBinding.txtPlayerSpGrade.background = requireContext().getDrawable(R.drawable.player_grade_silver)
                }
                in 8..10 -> {
                    mBinding.txtPlayerSpGrade.background = requireContext().getDrawable(R.drawable.player_grade_gold)
                }
            }
        } catch(_:Exception) {}

        val playerDB = PlayerDataBase.getInstance(requireContext())
        playerDB?.let {
            CoroutineScope(Dispatchers.IO).launch {
                val player : PlayerEntity? = it.playerDao().getPlayer(playerDetailInfo!!.spId.toString())
                CoroutineScope(Dispatchers.Main).launch {
                    mBinding.txtPlayerName.text = player?.playerName ?: ""
                }
            }
        }

        mBinding.txtPlayerRating.text = playerDetailInfo!!.status.spRating.toString()
        for (positionItem in PositionEnum.values()) {
            if (positionItem.spposition == playerDetailInfo!!.spPosition) {
                mBinding.txtPlayerPosition.text = positionItem.description

                when(positionItem.spposition) {
                    0 -> {
                        mBinding.txtPlayerPosition.setTextColor(resources.getColor(R.color.gk_color,null))
                    }
                    in 1..8 -> {
                        mBinding.txtPlayerPosition.setTextColor(resources.getColor(R.color.defence_color,null))
                    }
                    in 9..19 -> {
                        mBinding.txtPlayerPosition.setTextColor(resources.getColor(R.color.midfielder_color,null))
                    }
                    in 20..27 -> {
                        mBinding.txtPlayerPosition.setTextColor(resources.getColor(R.color.forward_color,null))
                    }
                    else -> {
                        mBinding.txtPlayerPosition.setTextColor(resources.getColor(R.color.sub_color,null))
                    }
                }
            }
        }
    }

    private fun updatePlayerImage(url: String) {
        try {
            if (url == "0") {
                Glide.with(requireContext())
                    .load(R.drawable.person_icon)
                    .into(mBinding.imgPlayer)
                return
            }
            Glide.with(requireContext())
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
                .into(mBinding.imgPlayer)
        } catch (_:Exception) { }
    }

    private fun updateIcon(context: Context, item: PlayerDTO) {
        val seasonDB = PlayerDataBase.getInstance(context)
        seasonDB?.let {
            CoroutineScope(Dispatchers.IO).launch {
                var seasonId = item.spId.toString().substring(0,3)
                //Todo 224, 234 분리... 뭐가 맞는지 넥슨측확인 필요 // 답변완료 : 234가 맞음
                if ("224" == seasonId) seasonId = "234"
                val seasonEntity = it.seasonDao().getSeason(seasonId)
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
                                    mBinding.imgPlayerIcon.visibility = View.GONE
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable,
                                    model: Any,
                                    target: Target<Drawable>,
                                    dataSource: DataSource,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    mBinding.imgPlayerIcon.visibility = View.VISIBLE
                                    LogUtil.dLog(LogUtil.TAG_UI, TAG,"Season, onResourceReady(...) $url")
                                    return false
                                }
                            })
                            .into(mBinding.imgPlayerIcon)
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