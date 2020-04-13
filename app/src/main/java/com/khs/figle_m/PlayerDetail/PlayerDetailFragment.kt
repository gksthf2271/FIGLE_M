package com.khs.figle_m.PlayerDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.khs.figle_m.DB.PlayerDataBase
import com.khs.figle_m.R
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.utils.PositionEnum
import kotlinx.android.synthetic.main.fragment_player_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerDetailFragment : DialogFragment() {
    val TAG: String = javaClass.name
    val DEBUG: Boolean = false
    open val TAG_PLAYER_DETAIL_DIALOG = "TAG_PLAYER_DETAIL_DIALOG"
    open val KEY_PLAYER_INFO = "KEY_PLAYER_INFO"

    companion object {
        @Volatile
        private var instance: PlayerDetailFragment? = null

        @JvmStatic
        fun getInstance(): PlayerDetailFragment =
            instance ?: synchronized(this) {
                instance
                    ?: PlayerDetailFragment().also {
                        instance = it
                    }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_player_detail, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    fun initView() {
        var playerDetailInfo: PlayerDTO? = null
        arguments.let {
            playerDetailInfo = arguments!!.get(KEY_PLAYER_INFO) as PlayerDTO
        }

        context ?: return
        playerDetailInfo ?: return

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
            if (positionItem.spposition.equals(playerDetailInfo!!.spPosition))
                txt_player_position.text = positionItem.description
        }
    }
}