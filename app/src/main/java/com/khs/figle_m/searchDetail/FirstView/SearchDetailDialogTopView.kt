package com.khs.figle_m.searchDetail.FirstView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.db.williamchart.extensions.getDrawable
import com.khs.data.nexon_api.response.DTO.MatchInfoDTO
import com.khs.data.nexon_api.response.DTO.PlayerDTO
import com.khs.data.nexon_api.response.MatchDetailResponse
import com.khs.figle_m.R
import com.khs.figle_m.utils.LogUtil
import com.khs.figle_m.databinding.CviewDetailTopViewBinding

class SearchDetailDialogTopView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private val TAG = javaClass.simpleName
    private var mBinding : CviewDetailTopViewBinding

    lateinit var mSearchAccessId: String
    var mBlockListMap: HashMap<Boolean, List<Int>> = hashMapOf()
    var mAssistListMap: HashMap<Boolean, List<Int>> = hashMapOf()

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = CviewDetailTopViewBinding.inflate(inflater, this, true)
    }

    fun updateUserView(isCoachMode: Boolean, searchAccessId: String, matchDetail: MatchDetailResponse) {
        mBinding.apply {
            groupPlayerInfo.visibility = View.GONE
            playerInfoView.visibility = View.GONE
            groupResult.visibility = View.VISIBLE
            mSearchAccessId = searchAccessId
            val matchInfo = matchDetail.matchInfo
            var opposingUserIndex = 1
            var myIndex = 0
            if (searchAccessId == matchInfo[0].accessId.lowercase()) {
                opposingUserIndex = 1
                myIndex = 0
            } else {
                opposingUserIndex = 0
                myIndex = 1
            }

            if (matchInfo.size <= 1) {
                LogUtil.vLog(LogUtil.TAG_UI, TAG,"경기 계정이 단일 계정으로 이상 데이터! $matchInfo")
                return
            }

            txtPlayMode.text = if (isCoachMode) { "감독모드" } else { "1 ON 1" }
            val myMatchInfo = matchInfo[myIndex]
            val opposingUserMatchInfo = matchInfo[opposingUserIndex]
            setupData(myMatchInfo,true)
            setupData(opposingUserMatchInfo,false)

            txtLeftNickName.text = myMatchInfo.nickname
            txtRightNickName.text = opposingUserMatchInfo.nickname


            txtLeftScore.text = if (myMatchInfo.shoot.goalTotal == myMatchInfo.shoot.goalTotalDisplay) {
                myMatchInfo.shoot.goalTotal.toString()
            } else {
                myMatchInfo.shoot.goalTotalDisplay.toString()
            }

            txtRightScore.text = if (opposingUserMatchInfo.shoot.goalTotal == opposingUserMatchInfo.shoot.goalTotalDisplay) {
                opposingUserMatchInfo.shoot.goalTotal.toString()
            } else {
                opposingUserMatchInfo.shoot.goalTotalDisplay.toString()
            }

            var myResult: String? = null
            var opposingUserResult: String? = null

            when (myMatchInfo.matchDetail.matchResult) {
                "승" -> {
                    if (myMatchInfo.shoot.goalTotal == myMatchInfo.shoot.goalTotalDisplay) {
                        myResult = "승"
                    } else {
                        myResult = "몰수승\n(${myMatchInfo.shoot.goalTotal} : ${opposingUserMatchInfo.shoot.goalTotal})"
                    }
                    opposingUserResult = "패"
                }
                "무" -> {
                    myResult = "무"
                    opposingUserResult = "무"
                }
                "패" -> {
                    if (opposingUserMatchInfo.shoot.goalTotal == opposingUserMatchInfo.shoot.goalTotalDisplay) {
                        myResult = "패"
                    } else {
                        myResult = "몰수패\n(${myMatchInfo.shoot.goalTotal} : ${opposingUserMatchInfo.shoot.goalTotal})"
                    }
                    opposingUserResult = "승"
                }
            }

            txtLeftResult.text = myResult
            txtRightResult.text = opposingUserResult

            val res = when (myMatchInfo.matchDetail.matchResult) {
                "승" -> {
                    context.resources.getColor(R.color.search_detail_dialog_top_win, null)
                }

                "패" -> {
                    context.resources.getColor(R.color.search_detail_dialog_top_lose, null)
                }

                else -> {
                    context.resources.getColor(R.color.search_detail_dialog_top_draw, null)
                }
            }
            rootLayout.setBackgroundColor(res)
        }
    }

    fun updatePlayerInfo(playerInfo: Pair<PlayerDTO, Boolean>) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"player : $playerInfo")
        mBinding.apply {
            rootLayout.background = getDrawable(R.color.empty_background)
            playerInfoView.visibility = View.VISIBLE
            groupPlayerInfo.visibility = View.VISIBLE
            groupResult.visibility = View.GONE
            playerInfoView.updateView(playerInfo.first, mAssistListMap[playerInfo.second]!!.sorted(), mBlockListMap[playerInfo.second]!!.sorted())
        }
    }

    private fun setupData(matchInfo: MatchInfoDTO, isLeft:Boolean) {
        val assistList = arrayListOf<Int>()
        val blockList = arrayListOf<Int>()
        for (player in matchInfo.player) {
                assistList.add(player.status.assist)
                blockList.add(player.status.block)
        }
        mAssistListMap[isLeft] = assistList
        mBlockListMap[isLeft] = blockList
    }
}