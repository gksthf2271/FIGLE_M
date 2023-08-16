package com.khs.figle_m.searchDetail.FirstView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.data.nexon_api.response.MatchDetailResponse
import com.khs.figle_m.searchDetail.SearchDetailDialogFragment
import com.khs.figle_m.utils.LogUtil
import com.khs.figle_m.utils.UserSortUtils
import com.khs.figle_m.databinding.CviewSearchDetailBinding

class SearchDetailDialogGameResultView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private val TAG = javaClass.simpleName
    private var mBinding : CviewSearchDetailBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = CviewSearchDetailBinding.inflate(inflater, this, true)
    }

    fun updateMatchInfo(matchInfo: MatchDetailResponse) {
        LogUtil.dLog(LogUtil.TAG_SEARCH, TAG,"updateMatchInfo(...) $matchInfo")
        mBinding.apply {
            val matchInfoPair = UserSortUtils.sortUserList(SearchDetailDialogFragment.getInstance().mSearchAccessId,matchInfo)
            cviewItem1.apply {
                setLeftText(matchInfoPair.first.matchDetail.possession.toString()  + "%")
                setTitleText("점유율")
                setRightText(matchInfoPair.second.matchDetail.possession.toString()  + "%")
            }

            cviewItem2.apply {
                setLeftText(matchInfoPair.first.matchDetail.foul.toString())
                setTitleText("파울")
                setRightText(matchInfoPair.second.matchDetail.foul.toString())
            }

            cviewItem3.apply {
                setLeftText(matchInfoPair.first.matchDetail.cornerKick.toString())
                setTitleText("코너킥")
                setRightText(matchInfoPair.second.matchDetail.cornerKick.toString())
            }

//        cardView.setLeftText("R : ${matchInfoPair.first.matchDetail.redCards}, Y : ${matchInfoPair.first.matchDetail.yellowCards}")
//        cardView.setTitleText("카드")
//        cardView.setRightText("R : ${matchInfoPair.second.matchDetail.redCards}, Y : ${matchInfoPair.second.matchDetail.yellowCards}")

            LogUtil.dLog(LogUtil.TAG_SEARCH, TAG,"TEST1, ${matchInfoPair.first.matchDetail.redCards}, ${matchInfoPair.first.matchDetail.yellowCards}")
            LogUtil.dLog(LogUtil.TAG_SEARCH, TAG,"TEST2, ${matchInfoPair.second.matchDetail.redCards}, ${matchInfoPair.second.matchDetail.yellowCards}")
            cviewItem4.apply {
                addLeftCard(matchInfoPair.first.matchDetail.redCards, matchInfoPair.first.matchDetail.yellowCards)
                setTitleText("카드")
                addRightCard(matchInfoPair.second.matchDetail.redCards, matchInfoPair.second.matchDetail.yellowCards)
            }

            var passSuccessRate:Double = matchInfoPair.first.pass.passSuccess.toDouble() / matchInfoPair.first.pass.passTry.toDouble() * 100
            if (passSuccessRate.isNaN()) passSuccessRate = 0.00
            cviewItem5.apply {
                setLeftText("${String.format("%.2f", passSuccessRate)}%\n${matchInfoPair.first.pass.passSuccess} / ${matchInfoPair.first.pass.passTry}")
                setTitleText("패스 성공률\n성공/시도")
                passSuccessRate = matchInfoPair.second.pass.passSuccess.toDouble() / matchInfoPair.second.pass.passTry.toDouble() * 100
                setRightText("${String.format("%.2f", passSuccessRate)}%\n${matchInfoPair.second.pass.passSuccess} / ${matchInfoPair.second.pass.passTry}")
            }

            var shootSuccessRate:Double = matchInfoPair.first.shoot.goalTotal.toDouble() / matchInfoPair.first.shoot.shootTotal.toDouble() * 100
            if (shootSuccessRate.isNaN()) shootSuccessRate = 0.00
            cviewItem6.apply {
                setLeftText("${String.format("%.2f",shootSuccessRate)}%\n${matchInfoPair.first.shoot.goalTotal} / ${matchInfoPair.first.shoot.effectiveShootTotal} / ${matchInfoPair.first.shoot.shootTotal}")
                setTitleText("슛 성공률\n성공/유효/시도")
                shootSuccessRate = matchInfoPair.second.shoot.goalTotal.toDouble() / matchInfoPair.second.shoot.shootTotal.toDouble() * 100
                setRightText("${String.format("%.2f", shootSuccessRate)}%\n${matchInfoPair.second.shoot.goalTotal} / ${matchInfoPair.second.shoot.effectiveShootTotal} / ${matchInfoPair.second.shoot.shootTotal}")
            }

            var defenceSuccessRate:Double = matchInfoPair.first.defence.blockSuccess.toDouble() / matchInfoPair.first.defence.blockTry.toDouble() * 100
            if (defenceSuccessRate.isNaN()) defenceSuccessRate = 0.00
            cviewItem7.apply {
                setLeftText("${String.format("%.2f",defenceSuccessRate)}%\n${matchInfoPair.first.defence.blockSuccess} / ${matchInfoPair.first.defence.blockTry}")
                setTitleText("수비(블락)\n성공/시도")
                defenceSuccessRate = matchInfoPair.second.defence.blockSuccess.toDouble() / matchInfoPair.second.defence.blockTry.toDouble() * 100
                setRightText("${String.format("%.2f", defenceSuccessRate)}%\n${matchInfoPair.second.defence.blockSuccess} / ${matchInfoPair.second.defence.blockTry}")
            }
        }
    }
}