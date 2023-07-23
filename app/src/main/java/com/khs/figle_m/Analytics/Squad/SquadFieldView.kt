package com.khs.figle_m.Analytics.Squad

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.R
import com.khs.data.nexon_api.response.CustomDTO.PlayerListDTO
import com.khs.data.nexon_api.response.DTO.PlayerDTO
import com.khs.figle_m.Utils.LogUtil
import com.khs.figle_m.Utils.PositionEnum
import kotlinx.android.synthetic.main.cview_field.view.*

class SquadFieldView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    val TAG: String = javaClass.simpleName

    init {
        initView(context)
    }

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_field, this)
    }

    fun updateMatchInfo(userNickname: String,
                        userId: String,
                        playerMap: HashMap<String, PlayerListDTO>,
                        itemClick: (Pair<PlayerDTO, Boolean>) -> Unit) {
        playerMap[userId]?.let { playerList ->
            txt_userName.text = "$userNickname 스쿼드"
            makeSquad(playerList.playerList, itemClick)
        }
    }

    private fun makeSquad(playerDTOList : List<PlayerDTO>, itemClick: (Pair<PlayerDTO, Boolean>) -> Unit) {
        for (playerDTO in playerDTOList) {
            LogUtil.dLog(LogUtil.TAG_SEARCH, TAG, "makeSquad > player : ${playerDTO.spId} / ${playerDTO.spGrade}")
            when(playerDTO.spPosition) {
                PositionEnum.GK.spposition -> {
                    position_gk.updatePlayerInfo(playerDTO, PositionEnum.GK) {
                        itemClick(it)
                    }
                    position_gk.visibility = View.VISIBLE
                }
                PositionEnum.SW.spposition -> {
                    position_sw.updatePlayerInfo(playerDTO, PositionEnum.SW) {
                        itemClick(it)
                    }
                    position_sw.visibility = View.VISIBLE
                }
                PositionEnum.RWB.spposition -> {
                    position_rwb.updatePlayerInfo(playerDTO, PositionEnum.RWB) {
                        itemClick(it)
                    }
                    position_rwb.visibility = View.VISIBLE
                }
                PositionEnum.RB.spposition -> {
                    position_rb.updatePlayerInfo(playerDTO, PositionEnum.RB) {
                        itemClick(it)
                    }
                    position_rb.visibility = View.VISIBLE
                }
                PositionEnum.RCB.spposition -> {
                    position_rcb.updatePlayerInfo(playerDTO, PositionEnum.RCB) {
                        itemClick(it)
                    }
                    position_rcb.visibility = View.VISIBLE
                }
                PositionEnum.CB.spposition -> {
                    position_cb.updatePlayerInfo(playerDTO, PositionEnum.CB) {
                        itemClick(it)
                    }
                    position_cb.visibility = View.VISIBLE
                }
                PositionEnum.LCB.spposition -> {
                    position_lcb.updatePlayerInfo(playerDTO, PositionEnum.LCB) {
                        itemClick(it)
                    }
                    position_lcb.visibility = View.VISIBLE
                }
                PositionEnum.LB.spposition -> {
                    position_lb.updatePlayerInfo(playerDTO, PositionEnum.LB) {
                        itemClick(it)
                    }
                    position_lb.visibility = View.VISIBLE
                }
                PositionEnum.LWB.spposition -> {
                    position_lwb.updatePlayerInfo(playerDTO, PositionEnum.LWB) {
                        itemClick(it)
                    }
                    position_lwb.visibility = View.VISIBLE
                }
                PositionEnum.RDM.spposition -> {
                    position_rdm.updatePlayerInfo(playerDTO, PositionEnum.RDM) {
                        itemClick(it)
                    }
                    position_rdm.visibility = View.VISIBLE
                }
                PositionEnum.CDM.spposition -> {
                    position_cdm.updatePlayerInfo(playerDTO, PositionEnum.CDM) {
                        itemClick(it)
                    }
                    position_cdm.visibility = View.VISIBLE
                }
                PositionEnum.LDM.spposition -> {
                    position_ldm.updatePlayerInfo(playerDTO, PositionEnum.LDM) {
                        itemClick(it)
                    }
                    position_ldm.visibility = View.VISIBLE
                }
                PositionEnum.RM.spposition -> {
                    position_rm.updatePlayerInfo(playerDTO, PositionEnum.RM) {
                        itemClick(it)
                    }
                    position_rm.visibility = View.VISIBLE
                }
                PositionEnum.RCM.spposition -> {
                    position_rcm.updatePlayerInfo(playerDTO, PositionEnum.RCM) {
                        itemClick(it)
                    }
                    position_rcm.visibility = View.VISIBLE
                }
                PositionEnum.CM.spposition -> {
                    position_cm.updatePlayerInfo(playerDTO, PositionEnum.CM) {
                        itemClick(it)
                    }
                    position_cm.visibility = View.VISIBLE
                }
                PositionEnum.LCM.spposition -> {
                    position_lcm.updatePlayerInfo(playerDTO, PositionEnum.LCM) {
                        itemClick(it)
                    }
                    position_lcm.visibility = View.VISIBLE
                }
                PositionEnum.LM.spposition -> {
                    position_lm.updatePlayerInfo(playerDTO, PositionEnum.LM) {
                        itemClick(it)
                    }
                    position_lm.visibility = View.VISIBLE
                }
                PositionEnum.RAM.spposition -> {
                    position_ram.updatePlayerInfo(playerDTO, PositionEnum.RAM) {
                        itemClick(it)
                    }
                    position_ram.visibility = View.VISIBLE
                }
                PositionEnum.CAM.spposition -> {
                    position_cam.updatePlayerInfo(playerDTO, PositionEnum.CAM) {
                        itemClick(it)
                    }
                    position_cam.visibility = View.VISIBLE
                }
                PositionEnum.LAM.spposition -> {
                    position_lam.updatePlayerInfo(playerDTO, PositionEnum.LAM) {
                        itemClick(it)
                    }
                    position_lam.visibility = View.VISIBLE
                }
                PositionEnum.RF.spposition -> {
                    position_rf.updatePlayerInfo(playerDTO, PositionEnum.RF) {
                        itemClick(it)
                    }
                    position_rf.visibility = View.VISIBLE
                }
                PositionEnum.CF.spposition -> {
                    position_cf.updatePlayerInfo(playerDTO, PositionEnum.CF) {
                        itemClick(it)
                    }
                    position_cf.visibility = View.VISIBLE
                }
                PositionEnum.LF.spposition -> {
                    position_lf.updatePlayerInfo(playerDTO, PositionEnum.LF) {
                        itemClick(it)
                    }
                    position_lf.visibility = View.VISIBLE
                }
                PositionEnum.RW.spposition -> {
                    position_rw.updatePlayerInfo(playerDTO, PositionEnum.RW) {
                        itemClick(it)
                    }
                    position_rw.visibility = View.VISIBLE
                }
                PositionEnum.RS.spposition -> {
                    position_rs.updatePlayerInfo(playerDTO, PositionEnum.RS) {
                        itemClick(it)
                    }
                    position_rs.visibility = View.VISIBLE
                }
                PositionEnum.ST.spposition -> {
                    position_st.updatePlayerInfo(playerDTO, PositionEnum.ST) {
                        itemClick(it)
                    }
                    position_st.visibility = View.VISIBLE
                }
                PositionEnum.LS.spposition -> {
                    position_ls.updatePlayerInfo(playerDTO, PositionEnum.LS) {
                        itemClick(it)
                    }
                    position_ls.visibility = View.VISIBLE
                }
                PositionEnum.LW.spposition -> {
                    position_lw.updatePlayerInfo(playerDTO, PositionEnum.LW) {
                        itemClick(it)
                    }
                    position_lw.visibility = View.VISIBLE
                }
                else -> {

                }
            }
        }
    }
}