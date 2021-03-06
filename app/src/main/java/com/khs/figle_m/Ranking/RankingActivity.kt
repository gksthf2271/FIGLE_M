package com.khs.figle_m.Ranking

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.khs.figle_m.Base.BaseActivity
import com.khs.figle_m.MainActivity
import com.khs.figle_m.R
import com.khs.figle_m.Utils.FragmentUtils
import kotlinx.android.synthetic.main.activity_ranking.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        fragment_container.visibility = View.GONE
        avi_loading.show(true)
    }

    override fun hideLoading() {
        Log.v(TAG,"hideLoading(...)")
        avi_loading.hide()
        avi_loading.visibility = View.GONE
        fragment_container.visibility = View.VISIBLE
    }

    override fun showRanking(rankerList : List<Ranker>) {
        val rankingFragment = RankingFragment.getInstance()
        var bundle = Bundle()
        bundle.putParcelableArrayList(RankingFragment().KEY_RANKING_LIST,ArrayList(rankerList))
        rankingFragment.arguments = bundle
        FragmentUtils().loadFragment(rankingFragment, R.id.fragment_container ,supportFragmentManager)
    }

    override fun showNetworkError() {
        if (!this.window.isActive || isDestroyed) return
        Log.v(TAG,"showNetworkErrorPopup(...)")
        CoroutineScope(Dispatchers.Main).launch {
            val popupView = layoutInflater.inflate(R.layout.cview_network_error, null)
            val popupWindow = PopupWindow(
                popupView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )

            popupWindow!!.setFocusable(true)
            popupWindow!!.showAtLocation(popupView, Gravity.CENTER, 0, 0)

            val textView = popupView.findViewById<TextView>(R.id.txt_title)
            textView.text = "네트워크 상태를 확인해주세요."

            val cancel = popupView.findViewById(R.id.btn_setting) as Button
            cancel.setOnClickListener {
                popupWindow!!.dismiss()
                val intent = Intent(Settings.ACTION_SETTINGS)
                startActivityForResult(intent, 0)
            }

            val ok = popupView.findViewById(R.id.btn_finish) as Button
            ok.setOnClickListener {
                popupWindow!!.dismiss()
                finishAffinity()
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                System.exit(0)
            }
        }
    }

    override fun showError(error: Int) {

    }
}