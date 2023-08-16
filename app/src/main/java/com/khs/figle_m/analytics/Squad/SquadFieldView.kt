package com.khs.figle_m.analytics.Squad

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.data.nexon_api.response.CustomDTO.PlayerListDTO
import com.khs.data.nexon_api.response.DTO.PlayerDTO
import com.khs.figle_m.utils.LogUtil
import com.khs.figle_m.utils.PositionEnum
import com.khs.figle_m.databinding.CviewFieldBinding

class SquadFieldView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    val TAG: String = javaClass.simpleName
    lateinit var mBinding : CviewFieldBinding
    init {
        initView(context)
    }

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = CviewFieldBinding.inflate(inflater, this, true)
    }

    fun updateMatchInfo(userNickname: String,
                        userId: String,
                        playerMap: HashMap<String, PlayerListDTO>,
                        itemClick: (Pair<PlayerDTO, Boolean>) -> Unit) {
        playerMap[userId]?.let { playerList ->
            mBinding.txtUserName.text = "$userNickname 스쿼드"
            makeSquad(playerList.playerList, itemClick)
        }
    }

    private fun makeSquad(playerDTOList : List<PlayerDTO>, itemClick: (Pair<PlayerDTO, Boolean>) -> Unit) {
        for (playerDTO in playerDTOList) {
            LogUtil.dLog(LogUtil.TAG_SEARCH, TAG, "makeSquad > player : ${playerDTO.spId} / ${playerDTO.spGrade}")
            when(playerDTO.spPosition) {
                PositionEnum.GK.spposition -> {
                    mBinding.positionGk.updatePlayerInfo(playerDTO, PositionEnum.GK) {
                        itemClick(it)
                    }
                    mBinding.positionGk.visibility = View.VISIBLE
                }
                PositionEnum.SW.spposition -> {
                    mBinding.positionSw.updatePlayerInfo(playerDTO, PositionEnum.SW) {
                        itemClick(it)
                    }
                    mBinding.positionSw.visibility = View.VISIBLE
                }
                PositionEnum.RWB.spposition -> {
                    mBinding.positionRwb.updatePlayerInfo(playerDTO, PositionEnum.RWB) {
                        itemClick(it)
                    }
                    mBinding.positionRwb.visibility = View.VISIBLE
                }
                PositionEnum.RB.spposition -> {
                    mBinding.positionRb.updatePlayerInfo(playerDTO, PositionEnum.RB) {
                        itemClick(it)
                    }
                    mBinding.positionRb.visibility = View.VISIBLE
                }
                PositionEnum.RCB.spposition -> {
                    mBinding.positionRcb.updatePlayerInfo(playerDTO, PositionEnum.RCB) {
                        itemClick(it)
                    }
                    mBinding.positionRcb.visibility = View.VISIBLE
                }
                PositionEnum.CB.spposition -> {
                    mBinding.positionCb.updatePlayerInfo(playerDTO, PositionEnum.CB) {
                        itemClick(it)
                    }
                    mBinding.positionCb.visibility = View.VISIBLE
                }
                PositionEnum.LCB.spposition -> {
                    mBinding.positionLcb.updatePlayerInfo(playerDTO, PositionEnum.LCB) {
                        itemClick(it)
                    }
                    mBinding.positionLcb.visibility = View.VISIBLE
                }
                PositionEnum.LB.spposition -> {
                    mBinding.positionLb.updatePlayerInfo(playerDTO, PositionEnum.LB) {
                        itemClick(it)
                    }
                    mBinding.positionLb.visibility = View.VISIBLE
                }
                PositionEnum.LWB.spposition -> {
                    mBinding.positionLwb.updatePlayerInfo(playerDTO, PositionEnum.LWB) {
                        itemClick(it)
                    }
                    mBinding.positionLwb.visibility = View.VISIBLE
                }
                PositionEnum.RDM.spposition -> {
                    mBinding.positionRdm.updatePlayerInfo(playerDTO, PositionEnum.RDM) {
                        itemClick(it)
                    }
                    mBinding.positionRdm.visibility = View.VISIBLE
                }
                PositionEnum.CDM.spposition -> {
                    mBinding.positionCdm.updatePlayerInfo(playerDTO, PositionEnum.CDM) {
                        itemClick(it)
                    }
                    mBinding.positionCdm.visibility = View.VISIBLE
                }
                PositionEnum.LDM.spposition -> {
                    mBinding.positionLdm.updatePlayerInfo(playerDTO, PositionEnum.LDM) {
                        itemClick(it)
                    }
                    mBinding.positionLdm.visibility = View.VISIBLE
                }
                PositionEnum.RM.spposition -> {
                    mBinding.positionRm.updatePlayerInfo(playerDTO, PositionEnum.RM) {
                        itemClick(it)
                    }
                    mBinding.positionRm.visibility = View.VISIBLE
                }
                PositionEnum.RCM.spposition -> {
                    mBinding.positionRcm.updatePlayerInfo(playerDTO, PositionEnum.RCM) {
                        itemClick(it)
                    }
                    mBinding.positionRcm.visibility = View.VISIBLE
                }
                PositionEnum.CM.spposition -> {
                    mBinding.positionCm.updatePlayerInfo(playerDTO, PositionEnum.CM) {
                        itemClick(it)
                    }
                    mBinding.positionCm.visibility = View.VISIBLE
                }
                PositionEnum.LCM.spposition -> {
                    mBinding.positionLcm.updatePlayerInfo(playerDTO, PositionEnum.LCM) {
                        itemClick(it)
                    }
                    mBinding.positionLcm.visibility = View.VISIBLE
                }
                PositionEnum.LM.spposition -> {
                    mBinding.positionLm.updatePlayerInfo(playerDTO, PositionEnum.LM) {
                        itemClick(it)
                    }
                    mBinding.positionLm.visibility = View.VISIBLE
                }
                PositionEnum.RAM.spposition -> {
                    mBinding.positionRam.updatePlayerInfo(playerDTO, PositionEnum.RAM) {
                        itemClick(it)
                    }
                    mBinding.positionRam.visibility = View.VISIBLE
                }
                PositionEnum.CAM.spposition -> {
                    mBinding.positionCam.updatePlayerInfo(playerDTO, PositionEnum.CAM) {
                        itemClick(it)
                    }
                    mBinding.positionCam.visibility = View.VISIBLE
                }
                PositionEnum.LAM.spposition -> {
                    mBinding.positionLam.updatePlayerInfo(playerDTO, PositionEnum.LAM) {
                        itemClick(it)
                    }
                    mBinding.positionLam.visibility = View.VISIBLE
                }
                PositionEnum.RF.spposition -> {
                    mBinding.positionRf.updatePlayerInfo(playerDTO, PositionEnum.RF) {
                        itemClick(it)
                    }
                    mBinding.positionRf.visibility = View.VISIBLE
                }
                PositionEnum.CF.spposition -> {
                    mBinding.positionCf.updatePlayerInfo(playerDTO, PositionEnum.CF) {
                        itemClick(it)
                    }
                    mBinding.positionCf.visibility = View.VISIBLE
                }
                PositionEnum.LF.spposition -> {
                    mBinding.positionLf.updatePlayerInfo(playerDTO, PositionEnum.LF) {
                        itemClick(it)
                    }
                    mBinding.positionLf.visibility = View.VISIBLE
                }
                PositionEnum.RW.spposition -> {
                    mBinding.positionRw.updatePlayerInfo(playerDTO, PositionEnum.RW) {
                        itemClick(it)
                    }
                    mBinding.positionRw.visibility = View.VISIBLE
                }
                PositionEnum.RS.spposition -> {
                    mBinding.positionRs.updatePlayerInfo(playerDTO, PositionEnum.RS) {
                        itemClick(it)
                    }
                    mBinding.positionRs.visibility = View.VISIBLE
                }
                PositionEnum.ST.spposition -> {
                    mBinding.positionSt.updatePlayerInfo(playerDTO, PositionEnum.ST) {
                        itemClick(it)
                    }
                    mBinding.positionSt.visibility = View.VISIBLE
                }
                PositionEnum.LS.spposition -> {
                    mBinding.positionLs.updatePlayerInfo(playerDTO, PositionEnum.LS) {
                        itemClick(it)
                    }
                    mBinding.positionLs.visibility = View.VISIBLE
                }
                PositionEnum.LW.spposition -> {
                    mBinding.positionLw.updatePlayerInfo(playerDTO, PositionEnum.LW) {
                        itemClick(it)
                    }
                    mBinding.positionLw.visibility = View.VISIBLE
                }
                else -> {

                }
            }
        }
    }
}