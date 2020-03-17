package com.example.figle_m.SearchList.SearchDetailView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.figle_m.R
import com.example.figle_m.Response.MatchDetailResponse

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

    fun updateView(searchAccessId: String, matchDetail: MatchDetailResponse) {
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
        val myMatchInfo = matchInfo[myIndex]
        val opposingUserMatchInfo = matchInfo[opposingUserIndex]

        mTxtLeftNickName.text = myMatchInfo.nickname
        mTxtRightNickName.text = opposingUserMatchInfo.nickname

        mTxtLeftScore.text = myMatchInfo.shoot.goalTotal.toString()
        mTxtRightScore.text = opposingUserMatchInfo.shoot.goalTotal.toString()

        var myResult: String? = null
        var opposingUserResult: String? = null

        when (myMatchInfo.matchDetail!!.matchResult) {
            "승" -> {
                myResult = "WIN"
                opposingUserResult = "LOSE"
            }
            "무" -> {
                myResult = "DRAW"
                opposingUserResult = "DRAW"
            }
            "패" -> {
                myResult = "LOSE"
                opposingUserResult = "WIN"
            }
        }
        mTxtLeftResult.text = myResult
        mTxtRightResult.text = opposingUserResult

        var res = 0
        when (myMatchInfo.matchDetail!!.matchResult) {
            "승" -> {
                res = context.resources.getColor(R.color.search_list_win, null)
            }
            "패" -> {
                res = context.resources.getColor(R.color.search_list_lose, null)
            }
            else -> {
                res = context.resources.getColor(R.color.search_list_draw, null)
            }
        }
        mRootLayout.setBackgroundColor(res)
    }
}