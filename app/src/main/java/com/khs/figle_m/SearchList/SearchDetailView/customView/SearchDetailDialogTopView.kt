package com.khs.figle_m.SearchList.SearchDetailView.customView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.R
import com.khs.figle_m.Response.DTO.MatchInfoDTO
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Response.MatchDetailResponse
import kotlinx.android.synthetic.main.cview_detail_top_view.view.*

class SearchDetailDialogTopView : ConstraintLayout {
    val TAG = javaClass.name
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    lateinit var mRootLayout: ConstraintLayout
    lateinit var mTxtLeftNickName: TextView
    lateinit var mTxtRightNickName: TextView
    lateinit var mTxtLeftScore: TextView
    lateinit var mTxtRightScore: TextView
    lateinit var mTxtLeftResult: TextView
    lateinit var mTxtRightResult: TextView
    lateinit var mSearchAccessId: String
    var mBlockListMap: HashMap<Boolean, List<Int>> = hashMapOf()
    var mAssistListMap: HashMap<Boolean, List<Int>> = hashMapOf()

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
    }

    fun updateUserView(isCoachMode: Boolean, searchAccessId: String, matchDetail: MatchDetailResponse) {
        group_playerInfo.visibility = View.GONE
        player_info_view.visibility = View.GONE
        group_result.visibility = View.VISIBLE
        mSearchAccessId = searchAccessId
        val matchInfo = matchDetail.matchInfo!!
        var opposingUserIndex = 1
        var myIndex = 0
        if (searchAccessId.equals(matchInfo[0].accessId.toLowerCase())) {
            opposingUserIndex = 1
            myIndex = 0
        } else {
            opposingUserIndex = 0
            myIndex = 1
        }

        if (matchInfo.size <= 1) {
            Log.v(TAG, "경기 계정이 단일 계정으로 이상 데이터! $matchInfo")
            return
        }

        if (isCoachMode) {
            txt_play_mode.text = "감독모드"
        } else {
            txt_play_mode.text = "1 ON 1"
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
        Log.v(TAG,"player : $playerInfo")
        mRootLayout.background = resources.getDrawable(R.color.empty_background,null)
        player_info_view.visibility = View.VISIBLE
        group_playerInfo.visibility = View.VISIBLE
        group_result.visibility = View.GONE
        mAssistListMap ?: return
        mBlockListMap ?: return
        player_info_view.updateView(playerInfo.first, mAssistListMap.get(playerInfo.second)!!.sorted(), mBlockListMap.get(playerInfo.second)!!.sorted())
    }

    fun setupData(matchInfo: MatchInfoDTO, isLeft:Boolean) {
        var assistList = arrayListOf<Int>()
        var blockList = arrayListOf<Int>()
        for (player in matchInfo.player) {
                assistList.add(player.status.assist)
                blockList.add(player.status.block)
        }
        mAssistListMap.put(isLeft,assistList)
        mBlockListMap.put(isLeft,blockList)
    }
}