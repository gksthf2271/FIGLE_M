package com.khs.figle_m.SearchDetail.FirstView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.BuildConfig
import com.khs.figle_m.R
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.SearchDetail.SearchDetailDialogFragment
import com.khs.figle_m.Utils.LogUtil
import com.khs.figle_m.Utils.UserSortUtils

class SearchDetailDialogGameResultView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    val TAG = javaClass.simpleName
    var mView: View? = null

    init {
        initView(context)
    }

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.cview_search_detail, this)
        mView = v
    }

    fun updateMatchInfo(matchInfo:MatchDetailResponse) {
        LogUtil.dLog(LogUtil.TAG_SEARCH, TAG,"updateMatchInfo(...) $matchInfo")
        val matchInfoPair = UserSortUtils().sortUserList(SearchDetailDialogFragment.getInstance().mSearchAccessId,matchInfo)
        val possessionView = findViewById<SearchDetailItemView>(R.id.cview_item1)
        val foulView = findViewById<SearchDetailItemView>(R.id.cview_item2)
        val cornerKickView = findViewById<SearchDetailItemView>(R.id.cview_item3)
        val cardView = findViewById<SearchDetailItemRedOrYellowCardView>(R.id.cview_item4)
        val passView = findViewById<SearchDetailItemView>(R.id.cview_item5)
        val goalView = findViewById<SearchDetailItemView>(R.id.cview_item6)
        val defenceView = findViewById<SearchDetailItemView>(R.id.cview_item7)
        possessionView.setLeftText(matchInfoPair.first.matchDetail.possession.toString()  + "%")
        possessionView.setTitleText("점유율")
        possessionView.setRightText(matchInfoPair.second.matchDetail.possession.toString()  + "%")

        foulView.setLeftText(matchInfoPair.first.matchDetail.foul.toString())
        foulView.setTitleText("파울")
        foulView.setRightText(matchInfoPair.second.matchDetail.foul.toString())

        cornerKickView.setLeftText(matchInfoPair.first.matchDetail.cornerKick.toString())
        cornerKickView.setTitleText("코너킥")
        cornerKickView.setRightText(matchInfoPair.second.matchDetail.cornerKick.toString())

//        cardView.setLeftText("R : ${matchInfoPair.first.matchDetail.redCards}, Y : ${matchInfoPair.first.matchDetail.yellowCards}")
//        cardView.setTitleText("카드")
//        cardView.setRightText("R : ${matchInfoPair.second.matchDetail.redCards}, Y : ${matchInfoPair.second.matchDetail.yellowCards}")

        LogUtil.dLog(LogUtil.TAG_SEARCH, TAG,"TEST1, ${matchInfoPair.first.matchDetail.redCards}, ${matchInfoPair.first.matchDetail.yellowCards}")
        LogUtil.dLog(LogUtil.TAG_SEARCH, TAG,"TEST2, ${matchInfoPair.second.matchDetail.redCards}, ${matchInfoPair.second.matchDetail.yellowCards}")
        cardView.addLeftCard(matchInfoPair.first.matchDetail.redCards, matchInfoPair.first.matchDetail.yellowCards)
        cardView.setTitleText("카드")
        cardView.addRightCard(matchInfoPair.second.matchDetail.redCards, matchInfoPair.second.matchDetail.yellowCards)

        var passSuccessRate:Double = matchInfoPair.first.pass.passSuccess.toDouble() / matchInfoPair.first.pass.passTry.toDouble() * 100
        if (passSuccessRate == null || passSuccessRate.isNaN()) passSuccessRate = 0.00
        passView.setLeftText("${String.format("%.2f", passSuccessRate)}%\n${matchInfoPair.first.pass.passSuccess} / ${matchInfoPair.first.pass.passTry}")
        passView.setTitleText("패스 성공률\n성공/시도")
        passSuccessRate = matchInfoPair.second.pass.passSuccess.toDouble() / matchInfoPair.second.pass.passTry.toDouble() * 100
        passView.setRightText("${String.format("%.2f", passSuccessRate)}%\n${matchInfoPair.second.pass.passSuccess} / ${matchInfoPair.second.pass.passTry}")

        var shootSuccessRate:Double = matchInfoPair.first.shoot.goalTotal.toDouble() / matchInfoPair.first.shoot.shootTotal.toDouble() * 100
        if (shootSuccessRate == null || shootSuccessRate.isNaN()) shootSuccessRate = 0.00
        goalView.setLeftText("${String.format("%.2f",shootSuccessRate)}%\n${matchInfoPair.first.shoot.goalTotal} / ${matchInfoPair.first.shoot.effectiveShootTotal} / ${matchInfoPair.first.shoot.shootTotal}")
        goalView.setTitleText("슛 성공률\n성공/유효/시도")
        shootSuccessRate = matchInfoPair.second.shoot.goalTotal.toDouble() / matchInfoPair.second.shoot.shootTotal.toDouble() * 100
        goalView.setRightText("${String.format("%.2f", shootSuccessRate)}%\n${matchInfoPair.second.shoot.goalTotal} / ${matchInfoPair.second.shoot.effectiveShootTotal} / ${matchInfoPair.second.shoot.shootTotal}")

        var defenceSuccessRate:Double = matchInfoPair.first.defence.blockSuccess.toDouble() / matchInfoPair.first.defence.blockTry.toDouble() * 100
        if (defenceSuccessRate == null || defenceSuccessRate.isNaN()) defenceSuccessRate = 0.00
        defenceView.setLeftText("${String.format("%.2f",defenceSuccessRate)}%\n${matchInfoPair.first.defence.blockSuccess} / ${matchInfoPair.first.defence.blockTry}")
        defenceView.setTitleText("수비(블락)\n성공/시도")
        defenceSuccessRate = matchInfoPair.second.defence.blockSuccess.toDouble() / matchInfoPair.second.defence.blockTry.toDouble() * 100
        defenceView.setRightText("${String.format("%.2f", defenceSuccessRate)}%\n${matchInfoPair.second.defence.blockSuccess} / ${matchInfoPair.second.defence.blockTry}")
    }
}