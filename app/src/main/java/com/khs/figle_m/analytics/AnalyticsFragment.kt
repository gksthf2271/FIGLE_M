package com.khs.figle_m.analytics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.khs.data.nexon_api.response.DTO.PlayerDTO
import com.khs.data.nexon_api.response.DTO.RankerPlayerDTO
import com.khs.data.nexon_api.response.MatchDetailResponse
import com.khs.figle_m.base.BaseFragment
import com.khs.figle_m.BuildConfig
import com.khs.figle_m.playerDetail.PlayerDetailDialogFragment
import com.khs.figle_m.ranking.Ranker
import com.khs.figle_m.utils.CrawlingUtils
import com.khs.figle_m.utils.LogUtil
import com.khs.figle_m.databinding.FragmentAnalyticsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnalyticsFragment : BaseFragment(), AnalyticsContract.View{
    private val TAG:String = javaClass.simpleName
    private val DEBUG = BuildConfig.DEBUG
    private lateinit var mBinding : FragmentAnalyticsBinding

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
        mBinding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        return mBinding.root
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
        mBinding.apply {
            txtTitle.text = "최근 10경기 분석"
            btnBack.setOnClickListener {
                activity?.finish()
            }
            val ratingLayoutManager = LinearLayoutManager(context)
            ratingLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
            recyclerView.layoutManager = ratingLayoutManager

            val goaleLayoutManager = LinearLayoutManager(context)
            goaleLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            recyclerViewGoal.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
            recyclerViewGoal.layoutManager = goaleLayoutManager

            val assistLayoutManager = LinearLayoutManager(context)
            assistLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            recyclerViewAssist.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
            recyclerViewAssist.layoutManager = assistLayoutManager
        }
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
            mBinding.aviLoading.apply {
                visibility = View.VISIBLE
                backgroundColorVisible(true)
                show(false)
            }
        }
    }

    override fun hideLoading(isError: Boolean) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"hideLoading(...)")
        CoroutineScope(Dispatchers.Main).launch {
            mBinding.aviLoading.let {
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
        mBinding.recyclerView.adapter = AnalyticsRecyclerViewAdapter(
                requireContext(),
                ROW_TYPE.MATCH_RATING,
                playerInfoList.sortedByDescending { it.totalData.totalSpRating / it.playerDataList.size }
                    .subList(0, 10)
            ) {
                LogUtil.vLog(LogUtil.TAG_UI, TAG, "ItemClick, AVG Rating TOP 10 $it")
                showPlayerDetailDialogFragmentWrap(it)
            }

        mBinding.recyclerViewGoal.adapter = AnalyticsRecyclerViewAdapter(
            requireContext(),
            ROW_TYPE.GOAL,
            playerInfoList.sortedByDescending { it.totalData.totalGoal }.subList(0, 5)
        ) {
            LogUtil.vLog(LogUtil.TAG_UI, TAG, "ItemClick, Total Goal TOP 5 $it")
            showPlayerDetailDialogFragmentWrap(it)
        }

        mBinding.recyclerViewAssist.adapter = AnalyticsRecyclerViewAdapter(
            requireContext(),
            ROW_TYPE.ASSIST,
            playerInfoList.sortedByDescending { it.totalData.totalAssist }.subList(0, 5)
        ) {
            LogUtil.vLog(LogUtil.TAG_UI, TAG, "ItemClick, Total Assist TOP 5 $it")
            showPlayerDetailDialogFragmentWrap(it)
        }
    }

    private fun showPlayerDetailDialogFragmentWrap(analyticsPlayer: AnalyticsPlayer) {
        if (analyticsPlayer.playerDataList.isNotEmpty()) {
            mAnalyticsPresenter.loadRankerPlayerList(50, analyticsPlayer.playerDataList.first())
        }
    }

    override fun showMatchDetail(matchDetailList: List<MatchDetailResponse>) {
        mAnalyticsPresenter.loadPlayerList(mAccessId, matchDetailList)

    }

    override fun showError(error: Int) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showError(...) : $error")
    }

    override fun showPlayerDetailDialogFragment(
        playerDTO: PlayerDTO,
        rankerPlayerDTOList: List<RankerPlayerDTO>
    ) {
        updatePlayer(playerDTO) { imgUrl ->
            val playerDetailFragment = PlayerDetailDialogFragment.getInstance()
            val bundle = Bundle()
            playerDTO.imageUrl = imgUrl
            bundle.putSerializable(PlayerDetailDialogFragment().KEY_PLAYER_INFO, playerDTO)
            bundle.putSerializable(
                PlayerDetailDialogFragment().KEY_RANKER_PLAYER_INFO,
                ArrayList(rankerPlayerDTOList)
            )
            playerDetailFragment.arguments = bundle
            if (!playerDetailFragment.isAdded) {
                playerDetailFragment.show(
                    requireActivity().supportFragmentManager,
                    PlayerDetailDialogFragment().TAG_PLAYER_DETAIL_DIALOG
                )
            }
        }
    }

    private fun updatePlayer(player: PlayerDTO, callback: (String) -> Unit) {
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