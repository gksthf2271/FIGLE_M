package com.khs.figle_m.SearchList.SearchHome

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.R
import com.khs.figle_m.Utils.LogUtil
import com.khs.figle_m.databinding.CviewMatchTypeViewBinding

class MatchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    val TAG = javaClass.simpleName
    lateinit var mBinding: CviewMatchTypeViewBinding
    init {
        initView(context)
    }

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = CviewMatchTypeViewBinding.inflate(inflater, this, true)
    }

    fun updateView(matchType: Int) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"updateView : ${matchType}")
        hideEmptyView()
        when(matchType) {
            DataManager.matchType.normalMatch.matchType -> {
                mBinding.imgIcon.setImageResource(R.drawable.analytics)
                mBinding.txtTitle.text = "1 ON 1\n전적 검색"
            }
            DataManager.matchType.coachMatch.matchType -> {
                mBinding.imgIcon.setImageResource(R.drawable.strategy)
                mBinding.txtTitle.text = "감독 모드\n전적 검색"
            }
        }
    }

    fun showEmptyView() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showEmptyView(...)")
        mBinding.groupView.visibility = View.GONE
        mBinding.emptyView.visibility = View.VISIBLE
    }

    fun hideEmptyView() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"hideEmptyView(...)")
        mBinding.emptyView.visibility = View.GONE
        mBinding.groupView.visibility = View.VISIBLE
    }
}
