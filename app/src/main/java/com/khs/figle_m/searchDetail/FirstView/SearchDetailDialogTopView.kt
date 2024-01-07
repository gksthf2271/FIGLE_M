package com.khs.figle_m.searchDetail.FirstView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.data.nexon_api.response.DTO.MatchInfoDTO
import com.khs.data.nexon_api.response.DTO.PlayerDTO
import com.khs.data.nexon_api.response.MatchDetailResponse
import com.khs.figle_m.R
import com.khs.figle_m.searchDetail.SecondView.PlayerInfoView
import com.khs.figle_m.utils.LogUtil

class SearchDetailDialogTopView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    val TAG = javaClass.simpleName

    lateinit var mRootLayout: ConstraintLayout
    lateinit var mTxtLeftNickName: TextView
    lateinit var mTxtRightNickName: TextView
    lateinit var mTxtLeftScore: TextView
    lateinit var mTxtRightScore: TextView
    lateinit var mTxtLeftResult: TextView
    lateinit var mTxtRightResult: TextView
    lateinit var mGroupPlayerInfo: ConstraintLayout
    lateinit var mPlayerInfo: PlayerInfoView
    lateinit var mGroupResult: ConstraintLayout
    lateinit var mTxtPlayMode: TextView
    lateinit var mSearchAccessId: String

    var mBlockListMap: HashMap<Boolean, List<Int>> = hashMapOf()
    var mAssistListMap: HashMap<Boolean, List<Int>> = hashMapOf()

    init {
        initView(context)
    }

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_detail_top_view, this)
        mRootLayout = findViewById(R.id.root_layout)
        mTxtRightNickName = findViewById(R.id.txt_right_nickName)
        mTxtLeftNickName = findViewById(R.id.txt_left_nickName)
        mTxtLeftScore = findViewById(R.id.txt_left_score)
        mTxtRightScore = findViewById(R.id.txt_right_score)
        mTxtLeftResult = findViewById(R.id.txt_left_result)
        mTxtRightResult = findViewById(R.id.txt_right_result)
        mGroupPlayerInfo = findViewById(R.id.group_playerInfo)
        mPlayerInfo = findViewById(R.id.player_info_view)
        mGroupResult = findViewById(R.id.group_result)
        mTxtPlayMode = findViewById(R.id.txt_play_mode)
    }

    fun updateUserView(isCoachMode: Boolean, searchAccessId: String, matchDetail: MatchDetailResponse) {
        mGroupPlayerInfo.visibility = View.GONE
        mPlayerInfo.visibility = View.GONE
        mGroupResult.visibility = View.VISIBLE
        mSearchAccessId = searchAccessId
        val matchInfo = matchDetail.matchInfo!!
        var opposingUserIndex = 1
        var myIndex = 0
        if (searchAccessId.equals(matchInfo[0].ouid.toLowerCase())) {
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

        if (isCoachMode) {
            mTxtPlayMode.text = "감독모드"
        } else {
            mTxtPlayMode.text = "1 ON 1"
        }
        val myMatchInfo = matchInfo[myIndex]
        val opposingUserMatchInfo = matchInfo[opposingUserIndex]
        setupData(myMatchInfo,true)
        setupData(opposingUserMatchInfo,false)

        mTxtLeftNickName.text = myMatchInfo.nickname
        mTxtRightNickName.text = opposingUserMatchInfo.nickname


        if (myMatchInfo.shoot.goalTotal == myMatchInfo.shoot.goalTotalDisplay) {
            mTxtLeftScore.text = myMatchInfo.shoot.goalTotal.toString()
        } else {
            mTxtLeftScore.text = myMatchInfo.shoot.goalTotalDisplay.toString()
        }

        if (opposingUserMatchInfo.shoot.goalTotal == opposingUserMatchInfo.shoot.goalTotalDisplay) {
            mTxtRightScore.text = opposingUserMatchInfo.shoot.goalTotal.toString()
        } else {
            mTxtRightScore.text = opposingUserMatchInfo.shoot.goalTotalDisplay.toString()
        }
//        mTxtLeftScore.text = myMatchInfo.shoot.goalTotal.toString()
//        mTxtRightScore.text = opposingUserMatchInfo.shoot.goalTotal.toString()

        var myResult: String? = null
        var opposingUserResult: String? = null

//        when (myMatchInfo.matchDetail!!.matchResult) {
//            "승" -> {
//                myResult = "WIN"
//                opposingUserResult = "LOSE"
//            }
//            "무" -> {
//                myResult = "DRAW"
//                opposingUserResult = "DRAW"
//            }
//            "패" -> {
//                myResult = "LOSE"
//                opposingUserResult = "WIN"
//            }
//        }

        when (myMatchInfo.matchDetail!!.matchResult) {
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

        mTxtLeftResult.text = myResult
        mTxtRightResult.text = opposingUserResult

        var res = 0
        when (myMatchInfo.matchDetail!!.matchResult) {
            "승" -> {
                res = context.resources.getColor(R.color.search_detail_dialog_top_win, null)
            }
            "패" -> {
                res = context.resources.getColor(R.color.search_detail_dialog_top_lose, null)
            }
            else -> {
                res = context.resources.getColor(R.color.search_detail_dialog_top_draw, null)
            }
        }
        mRootLayout.setBackgroundColor(res)
    }

    fun updatePlayerInfo(playerInfo: Pair<PlayerDTO, Boolean>) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"player : $playerInfo")
        mRootLayout.background = resources.getDrawable(R.color.empty_background,null)
        mPlayerInfo.visibility = View.VISIBLE
        mGroupPlayerInfo.visibility = View.VISIBLE
        mGroupResult.visibility = View.GONE
        mAssistListMap ?: return
        mBlockListMap ?: return
        mPlayerInfo.updateView(playerInfo.first, mAssistListMap.get(playerInfo.second)!!.sorted(), mBlockListMap.get(playerInfo.second)!!.sorted())
    }

    fun setupData(matchInfo: MatchInfoDTO, isLeft:Boolean) {
        val assistList = arrayListOf<Int>()
        val blockList = arrayListOf<Int>()
        for (player in matchInfo.player) {
                assistList.add(player.status.assist)
                blockList.add(player.status.block)
        }
        mAssistListMap.put(isLeft,assistList)
        mBlockListMap.put(isLeft,blockList)
    }
}