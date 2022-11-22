package com.khs.figle_m.Analytics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.khs.figle_m.Base.BaseFragment
import com.khs.figle_m.BuildConfig
import com.khs.figle_m.PlayerDetail.PlayerDetailDialogFragment
import com.khs.figle_m.R
import com.khs.figle_m.Ranking.Ranker
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Response.DTO.RankerPlayerDTO
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.Utils.CrawlingUtils
import com.khs.figle_m.Utils.LogUtil
import kotlinx.android.synthetic.main.fragment_analytics.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnalyticsFragment : BaseFragment(), AnalyticsContract.View{
    val TAG:String = javaClass.simpleName
    val DEBUG = BuildConfig.DEBUG

    lateinit var mAnalyticsPresenter: AnalyticsPresenter
    lateinit var mCurrentRank: Ranker
    lateinit var mAccessId: String

    override fun initPresenter() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"initPresenter(...)")
        mAnalyticsPresenter = AnalyticsPresenter()
        mAnalyticsPresenter.takeView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_analytics, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        mAnalyticsPresenter.dropView()
    }

    override fun onStart() {
        super.onStart()
        initView()
        initList()
    }

    fun initView() {
        txt_title.text = "최근 10경기 분석"
        btn_back.setOnClickListener {
            activity?.finish()
        }
        val rating_layoutManager = LinearLayoutManager(context)
        rating_layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recycler_view.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        recycler_view.layoutManager = rating_layoutManager

        val goale_layoutManager = LinearLayoutManager(context)
        goale_layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recycler_view_goal.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        recycler_view_goal.layoutManager = goale_layoutManager

        val assist_layoutManager = LinearLayoutManager(context)
        assist_layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recycler_view_assist.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        recycler_view_assist.layoutManager = assist_layoutManager
   }

    fun initList() {
        var myList = arrayListOf<String>()
        arguments?.let {
            myList = it.getStringArrayList(AnalyticsActivity().KEY_MY_DATA)!!
            mAccessId = it.getString(AnalyticsActivity().KEY_ACCESS_ID)!!
        }
        var lastIdx = 10
        if (myList.size < 10) {
            lastIdx = myList.lastIndex
        }
        mAnalyticsPresenter.loadMatchDetail(myList.subList(0, lastIdx))
    }

    override fun showLoading() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showLoading(...)")
        CoroutineScope(Dispatchers.Main).launch {
            avi_loading.apply {
                visibility = View.VISIBLE
                backroundColorVisible(true)
                show(false)
            }
        }
    }

    override fun hideLoading(isError: Boolean) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"hideLoading(...)")
        CoroutineScope(Dispatchers.Main).launch {
            avi_loading?.let {
                it.visibility = View.GONE
                it.hide()
            }
        }
    }
    override fun showPlayerMap(playerMap: Map<Int, List<PlayerDTO>>) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showPlayerList, playerList size : ${playerMap.values.size}")
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
            ) {
                LogUtil.vLog(LogUtil.TAG_UI, TAG, "ItemClick, AVG Rating TOP 10 $it")
                showPlayerDetailDialogFragmentWrap(it)
            }

        recycler_view_goal.adapter = AnalyticsRecyclerViewAdapter(
            context!!,
            ROW_TYPE.GOAL,
            playerInfoList.sortedByDescending { it.totalData.totalGoal }.subList(0, 5)
        ) {
            LogUtil.vLog(LogUtil.TAG_UI, TAG, "ItemClick, Total Goal TOP 5 $it")
            showPlayerDetailDialogFragmentWrap(it)
        }

        recycler_view_assist.adapter = AnalyticsRecyclerViewAdapter(
            context!!,
            ROW_TYPE.ASSIST,
            playerInfoList.sortedByDescending { it.totalData.totalAssist }.subList(0, 5)
        ) {
            LogUtil.vLog(LogUtil.TAG_UI, TAG, "ItemClick, Total Assist TOP 5 $it")
            showPlayerDetailDialogFragmentWrap(it)
        }
    }

    fun showPlayerDetailDialogFragmentWrap(analyticsPlayer: AnalyticsPlayer) {
        if (analyticsPlayer.playerDataList.isNotEmpty()) {
            mAnalyticsPresenter.loadRankerPlayerList(50, analyticsPlayer.playerDataList.first())
        }
    }

    override fun showMatchDetail(matchDetailList: List<MatchDetailResponse>) {
        mAnalyticsPresenter.loadPlayerList(mAccessId, matchDetailList)

    }

    override fun showError(error: Int) {
        TODO("Not yet implemented")
    }

    override fun showPlayerDetailDialogFragment(
        playerDTO: PlayerDTO,
        rankerPlayerDTOList: List<RankerPlayerDTO>
    ) {
        updatePlayer(playerDTO) { imgUrl ->
            val playerDetailFragment = PlayerDetailDialogFragment.getInstance()
            val bundle = Bundle()
            playerDTO.imageUrl = imgUrl
            bundle.putParcelable(PlayerDetailDialogFragment().KEY_PLAYER_INFO, playerDTO)
            bundle.putParcelableArrayList(
                PlayerDetailDialogFragment().KEY_RANKER_PLAYER_INFO,
                ArrayList(rankerPlayerDTOList)
            )
            playerDetailFragment.arguments = bundle
            if (!playerDetailFragment.isAdded) {
                playerDetailFragment.show(
                    fragmentManager!!,
                    PlayerDetailDialogFragment().TAG_PLAYER_DETAIL_DIALOG
                )
            }
        }
    }

    private fun updatePlayer(player:PlayerDTO, callback: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            CrawlingUtils().getPlayerImg(player, {
                callback(it)
            }, {
                LogUtil.vLog(LogUtil.TAG_UI, TAG,"updatePlayer(...) : $it")
                showError(it)
            })
        }
    }

    enum class ROW_TYPE(val rowType:Int, val description:String){
        MATCH_RATING(0,"GRADE_TOP_10"),
        GOAL(1, "GOAL_TOP_5"),
        ASSIST(2, "ASSIST_TOP_5")
    }
}