package com.khs.figle_m.Analytics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.khs.figle_m.Base.BaseFragment
import com.khs.figle_m.BuildConfig
import com.khs.figle_m.R
import com.khs.figle_m.Ranking.Ranker
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Response.MatchDetailResponse
import kotlinx.android.synthetic.main.fragment_analytics.*

class AnalyticsFragment : BaseFragment(), AnalyticsContract.View{
    val TAG:String = javaClass.name
    val DEBUG = BuildConfig.DEBUG

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
        val rating_layoutManager = LinearLayoutManager(context)
        rating_layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recycler_view.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        recycler_view.setLayoutManager(rating_layoutManager)

        val goale_layoutManager = LinearLayoutManager(context)
        goale_layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recycler_view_goal.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        recycler_view_goal.setLayoutManager(goale_layoutManager)

        val assist_layoutManager = LinearLayoutManager(context)
        assist_layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recycler_view_assist.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        recycler_view_assist.setLayoutManager(assist_layoutManager)
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
        recycler_view.adapter =
            AnalyticsRecyclerViewAdapter(
                context!!,
                ROW_TYPE.MATCH_RATING,
                playerInfoList.sortedByDescending { it.totalData.totalSpRating / it.playerDataList.size }
                    .subList(0, 10)
                , {
                    Log.v(TAG, "ItemClick, AVG Rating TOP 10 ${it}")
                })

        recycler_view_goal.adapter = AnalyticsRecyclerViewAdapter(
            context!!,
            ROW_TYPE.GOAL,
            playerInfoList.sortedByDescending { it.totalData.totalGoal }.subList(0, 5)
            ,{
                Log.v(TAG, "ItemClick, Total Goal TOP 5 ${it}")
            }
        )

        recycler_view_assist.adapter = AnalyticsRecyclerViewAdapter(
            context!!,
            ROW_TYPE.ASSIST,
            playerInfoList.sortedByDescending { it.totalData.totalAssist }.subList(0, 5)
            ,{
                Log.v(TAG, "ItemClick, Total Assist TOP 5 ${it}")
            }
        )
    }

    override fun showMatchDetail(matchDetailList: List<MatchDetailResponse>) {
        mAnalyticsPresenter.loadPlayerList(mAccessId, matchDetailList)

    }

    override fun showError(error: Int) {
        TODO("Not yet implemented")
    }

    enum class ROW_TYPE(val rowType:Int, val description:String){
        MATCH_RATING(0,"GRADE_TOP_10"),
        GOAL(1, "GOAL_TOP_5"),
        ASSIST(2, "ASSIST_TOP_5")
    }
}