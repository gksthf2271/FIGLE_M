package com.khs.figle_m.Analytics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khs.figle_m.Base.BaseFragment
import com.khs.figle_m.R
import com.khs.figle_m.Ranking.Ranker
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Response.MatchDetailResponse
import kotlinx.android.synthetic.main.fragment_analytics.*

class AnalyticsFragment : BaseFragment(), AnalyticsContract.View{
    val TAG:String = javaClass.name
    val DEBUG = false

    lateinit var mAnalyticsPresenter: AnalyticsPresenter
    lateinit var mCurrentRank: Ranker
    lateinit var mAccessId: String

    override fun initPresenter() {
        Log.v(TAG,"initPresenter(...)")
        mAnalyticsPresenter = AnalyticsPresenter()
        mAnalyticsPresenter.takeView(this)
    }

    companion object {
        @Volatile
        private var instance: AnalyticsFragment? = null

        @JvmStatic
        fun getInstance(): AnalyticsFragment =
            instance ?: synchronized(this) {
                instance
                    ?: AnalyticsFragment().also {
                        instance = it
                    }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_analytics, container, false)
    }

    override fun onStart() {
        super.onStart()
        initView()
        initList()
    }

    fun initView() {
        txt_title.text = "최근 10경기 분석"
        btn_back.setOnClickListener {
            activity.let {
                activity!!.finish()
            }
        }
   }

    fun initList() {
        var myList = arrayListOf<String>()
        arguments.let {
            myList = arguments!!.getStringArrayList(AnalyticsActivity().KEY_MY_DATA)!!
            mAccessId = arguments!!.getString(AnalyticsActivity().KEY_ACCESS_ID)!!
        }
        mAnalyticsPresenter.loadMatchDetail(myList.subList(0,10))
    }

    override fun onDestroy() {
        super.onDestroy()
        mAnalyticsPresenter.dropView()
    }

    override fun showLoading() {
        Log.v(TAG,"showLoading(...)")
        avi_loading.visibility = View.VISIBLE
        avi_loading.show(true)
    }

    override fun hideLoading(isError: Boolean) {
        Log.v(TAG,"hideLoading(...)")
        avi_loading.visibility = View.GONE
        avi_loading.hide()
    }
    override fun showPlayerList(playerMap: Map<Int, List<PlayerDTO>>) {
        Log.v(TAG,"showPlayerList, playerList size : ${playerMap.values.size}")

    }

    override fun showMatchDetail(matchDetailList: List<MatchDetailResponse>) {
        mAnalyticsPresenter.loadPlayerList(mAccessId, matchDetailList)
    }

    override fun showError(error: Int) {
        TODO("Not yet implemented")
    }
}