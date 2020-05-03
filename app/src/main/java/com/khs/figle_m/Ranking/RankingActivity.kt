package com.khs.figle_m.Ranking

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import com.khs.figle_m.Base.BaseActivity
import com.khs.figle_m.R
import kotlinx.android.synthetic.main.activity_ranking.*

class RankingActivity : BaseActivity(), RankingContract.View, Handler.Callback {
    val TAG: String = javaClass.name
    lateinit var mRankingPresenter: RankingPresenter
    private val mHandler:Handler = Handler(this)
    override fun initPresenter() {
        mRankingPresenter = RankingPresenter()
    }

    companion object {
        @Volatile
        private var instance: RankingActivity? = null

        @JvmStatic
        fun getInstance(): RankingActivity =
            instance ?: synchronized(this) {
                instance
                    ?: RankingActivity().also {
                        instance = it
                    }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
    }

    override fun onStart() {
        super.onStart()
        mRankingPresenter.takeView(this)
        mRankingPresenter.getRankingList(this, 1)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG,"onDestory(...)")
        mRankingPresenter!!.dropView()
    }

    override fun handleMessage(msg: Message): Boolean {
        mHandler.removeMessages(msg.what)
        return false
    }

    override fun showLoading() {
        Log.v(TAG,"showLoading(...)")
        avi_loading.visibility = View.VISIBLE
        view_no1.visibility = View.GONE
        layout_recyclerview.visibility = View.GONE
        avi_loading.show(true)
    }

    override fun hideLoading() {
        Log.v(TAG,"hideLoading(...)")
        avi_loading.hide()
        avi_loading.visibility = View.GONE
        view_no1.visibility = View.VISIBLE
        layout_recyclerview.visibility = View.VISIBLE
    }

    override fun showRanking(rankerList : List<Ranker>) {

    }

    override fun showNetworkError() {
        TODO("Not yet implemented")
    }

    override fun showError(error: Int) {
        TODO("Not yet implemented")
    }
}