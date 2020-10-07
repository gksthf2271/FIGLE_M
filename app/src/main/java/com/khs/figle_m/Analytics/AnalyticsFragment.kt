package com.khs.figle_m.Analytics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khs.figle_m.Base.BaseFragment
import com.khs.figle_m.R
import com.khs.figle_m.Ranking.Ranker
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.SearchList.SearchDecoration
import com.khs.figle_m.utils.PositionEnum
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
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recycler_view.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        recycler_view.setLayoutManager(layoutManager)
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
        avi_loading.backroundColorVisible(true)
        avi_loading.show(true)
    }

    override fun hideLoading(isError: Boolean) {
        Log.v(TAG,"hideLoading(...)")
        avi_loading.visibility = View.GONE
        avi_loading.hide()
    }
    override fun showPlayerMap(playerMap: Map<Int, List<PlayerDTO>>) {
        Log.v(TAG,"showPlayerList, playerList size : ${playerMap.values.size}")
        mAnalyticsPresenter.loadPlayerInfoList(playerMap)
    }

    override fun showPlayerInfoList(playerInfoList: List<AnalyticsPlayer>) {
        mAnalyticsPresenter.loadPlayerImageUrl(playerInfoList)
    }

    override fun showPlayerImage(playerInfoList: List<AnalyticsPlayer>) {
        recycler_view.adapter = AnalyticsRecyclerViewAdapter(context!!,playerInfoList ,{
            Log.v(TAG, "ItemClick! ${it}")
        })
    }

    override fun showMatchDetail(matchDetailList: List<MatchDetailResponse>) {
        mAnalyticsPresenter.loadPlayerList(mAccessId, matchDetailList)
        //TODO : 경기에 대한 분석 어떻게 노출할지 생각해봐야됨. Contents Depth가 너무 얕음.
    }

    override fun showError(error: Int) {
        TODO("Not yet implemented")
    }
}