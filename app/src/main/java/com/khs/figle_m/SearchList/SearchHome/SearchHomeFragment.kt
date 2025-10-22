package com.khs.figle_m.SearchList.SearchHome

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.khs.figle_m.Analytics.AnalyticsActivity
import com.khs.figle_m.Base.BaseFragment
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Home.HomeFragment
import com.khs.figle_m.MainActivity
import com.khs.figle_m.R
import com.khs.figle_m.Response.DTO.MatchInfoDTO
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.Response.UserHighRankResponse
import com.khs.figle_m.Response.UserResponse
import com.khs.figle_m.SearchList.SearchContract
import com.khs.figle_m.SearchList.SearchHomePresenter
import com.khs.figle_m.SearchList.SearchListFragment
import com.khs.figle_m.Trade.TradeActivity
import com.khs.figle_m.Utils.DivisionEnum
import com.khs.figle_m.Utils.FragmentUtils
import com.khs.figle_m.Utils.LogUtil
import com.khs.figle_m.ui.components.WinRateData
import com.khs.figle_m.ui.screens.SearchHomeScreen
import com.khs.figle_m.ui.screens.SearchHomeUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody


class SearchHomeFragment : BaseFragment(),
    SearchContract.View {
    val TAG: String = javaClass.simpleName
    val DEBUG: Boolean = true

    val KEY_SEARCH_USER_INFO: String = "SearchUserInfo"

    lateinit var mSearchHomePresenter: SearchHomePresenter

    var mOfficialGameMatchIdList: List<String> = arrayListOf()
    var mCoachModeMatchIdList: List<String> = arrayListOf()
    lateinit var mSearchUserInfo: UserResponse

    // Compose UI State
    private var uiState by mutableStateOf(SearchHomeUiState(isLoading = true))

    var mCoachDivision: String = ""
    var mNormalDivision: String = ""

    var mNormalMatchResponse: UserHighRankResponse? = null
    var mCoachMatchResponse: UserHighRankResponse? = null


    val KEY_MATCH_DETAIL_LIST: String = "KEY_MATCH_DETAIL_LIST"
    override fun initPresenter() {
        mSearchHomePresenter = SearchHomePresenter()
    }

    companion object {
        @Volatile
        private var instance: SearchHomeFragment? = null

        @JvmStatic
        fun getInstance(): SearchHomeFragment =
            instance
                ?: synchronized(this) {
                instance
                    ?: SearchHomeFragment()
                        .also {
                        instance = it
                    }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Compose를 사용한 새로운 구현
        return androidx.compose.ui.platform.ComposeView(requireContext()).apply {
            setContent {
                com.khs.figle_m.ui.theme.FigleComposeTheme {
                    SearchHomeScreen(
                        uiState = uiState,
                        onBackClick = {
                            FragmentUtils().loadFragment(
                                HomeFragment.getInstance(),
                                R.id.fragment_container,
                                fragmentManager!!
                            )
                        },
                        onNormalMatchClick = {
                            if (mOfficialGameMatchIdList.isNotEmpty()) {
                                showSearchList(DataManager.matchType.normalMatch, mOfficialGameMatchIdList)
                            }
                        },
                        onCoachMatchClick = {
                            if (mCoachModeMatchIdList.isNotEmpty()) {
                                showSearchList(DataManager.matchType.coachMatch, mCoachModeMatchIdList)
                            }
                        },
                        onTradeClick = {
                            val intent = Intent(context, TradeActivity::class.java)
                            intent.putExtra(TradeActivity().KEY_ACCESS_ID, mSearchUserInfo.ouid)
                            startActivityForResult(intent, HomeFragment().RESULT_REQUEST_CODE)
                        },
                        onAnalysisClick = {
                            if (mOfficialGameMatchIdList.isNotEmpty()) {
                                mSearchHomePresenter.getMatchAnalysisByMatchId(
                                    mSearchUserInfo.ouid,
                                    mOfficialGameMatchIdList
                                )
                            }
                        }
                    )
                }
            }
        }

        // 기존 XML 레이아웃 구현 (주석 처리)
        // val v: View = inflater.inflate(R.layout.fragment_searchlist_ver2, container, false)
        // return v
    }

    override fun onStart() {
        super.onStart()
        if (isRestartApp) return
        if (mSearchHomePresenter == null) {
            mSearchHomePresenter = SearchHomePresenter()
        }
        mSearchHomePresenter!!.takeView(this)
        initMyInfoData()
        initListData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mSearchHomePresenter.dropView()
    }

    // Compose로 전환했으므로 View 초기화 메서드 불필요
    // fun initView() { ... }
    // private fun initRateView() { ... }

    private fun initMyInfoData() {
        arguments?.let { bundle ->
            mSearchUserInfo = bundle.getParcelable(KEY_SEARCH_USER_INFO)!!

            // UI State 업데이트
            uiState = uiState.copy(
                nickname = mSearchUserInfo.nickname,
                teamPrice = mSearchUserInfo.teamPrice,
                level = mSearchUserInfo.level
            )
        }
        mSearchHomePresenter.getUserHighRank(mSearchUserInfo.ouid)
    }

    private fun initListData() {

        mSearchHomePresenter.getMatchId(
            mSearchUserInfo.ouid,
            DataManager.matchType.normalMatch,
            DataManager.getInstance().offset,
            DataManager.getInstance().SEARCH_LIMIT
        )

        mSearchHomePresenter.getMatchId(
            mSearchUserInfo.ouid,
            DataManager.matchType.coachMatch,
            DataManager.getInstance().offset,
            DataManager.getInstance().SEARCH_LIMIT
        )
    }

    override fun showLoading() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showLoading(...)")
        uiState = uiState.copy(isLoading = true)
    }

    override fun hideLoading(isError: Boolean) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"hideLoading(...)")
        uiState = uiState.copy(isLoading = false)
    }

    @SuppressLint("SetTextI18n")
    override fun showOfficialGameMatchIdList(userMatchIdResponse: ResponseBody?) {
        userMatchIdResponse ?: return
        var result: String = userMatchIdResponse.string()
        mOfficialGameMatchIdList = result.removeSurrounding("[", "]").replace("\"", "").split(",")

        val hasMatches = !result.isNullOrEmpty() && result != "[]"

        uiState = uiState.copy(hasNormalMatches = hasMatches)

        if (hasMatches) {
            // 승률 데이터 로드
            loadWinRateData(mSearchUserInfo.ouid, DataManager.matchType.normalMatch, mOfficialGameMatchIdList)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun showCoachModeMatchIdList(matchDetailResponse: ResponseBody?) {
        matchDetailResponse ?: return
        var result: String = matchDetailResponse.string()
        mCoachModeMatchIdList = result.removeSurrounding("[", "]").replace("\"", "").split(",")

        val hasMatches = !result.isNullOrEmpty() && result != "[]"

        uiState = uiState.copy(hasCoachMatches = hasMatches)

        if (hasMatches) {
            // 승률 데이터 로드
            loadWinRateData(mSearchUserInfo.ouid, DataManager.matchType.coachMatch, mCoachModeMatchIdList)
        }

        hideLoading(false)
    }

    /**
     * 승률 데이터를 로드하는 함수
     */
    private fun loadWinRateData(ouid: String, matchType: DataManager.matchType, matchIdList: List<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            val matchDetailList = arrayListOf<MatchDetailResponse>()
            var searchSize = DataManager().SEARCH_PAGE_SIZE
            if (matchIdList.size < DataManager().SEARCH_PAGE_SIZE) {
                searchSize = matchIdList.size
            }

            var successCount = 0
            var failCount = 0

            for (index in 0 until searchSize) {
                if (matchIdList.size <= index) break

                DataManager.getInstance().loadMatchDetailWrapper(
                    matchIdList[index],
                    { matchDetail ->
                        matchDetailList.add(matchDetail)
                        successCount++

                        if (searchSize == successCount + failCount) {
                            // 승률 계산
                            val winRateData = calculateWinRate(ouid, matchDetailList)

                            // UI State 업데이트
                            CoroutineScope(Dispatchers.Main).launch {
                                when (matchType) {
                                    DataManager.matchType.normalMatch -> {
                                        uiState = uiState.copy(normalWinRate = winRateData)
                                    }
                                    DataManager.matchType.coachMatch -> {
                                        uiState = uiState.copy(coachWinRate = winRateData)
                                    }
                                }
                            }
                        }
                    },
                    { error ->
                        LogUtil.eLog(LogUtil.TAG_UI, TAG, "Failed Request : $error")
                        failCount++
                    }
                )
            }
        }
    }

    /**
     * 승률 계산 함수
     */
    private fun calculateWinRate(ouid: String, matchInfoList: List<MatchDetailResponse>): WinRateData {
        var win = 0
        var draw = 0
        var lose = 0

        for (item in matchInfoList) {
            val myInfo: MatchInfoDTO? = if (item.matchInfo.size < 2) {
                item.matchInfo.firstOrNull()
            } else {
                if (ouid == item.matchInfo[0].ouid) {
                    item.matchInfo[0]
                } else {
                    item.matchInfo[1]
                }
            }

            myInfo?.matchDetail?.matchResult ?: continue
            when (myInfo.matchDetail.matchResult) {
                "승" -> win++
                "무" -> draw++
                "패" -> lose++
            }
        }

        return WinRateData(win = win, draw = draw, lose = lose)
    }

    override fun showAnalysisInfo(ouid: String, matchIdList: List<String>) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showAnaysisInfo : $matchIdList")
        val intent = Intent(context, AnalyticsActivity::class.java)
        intent.putStringArrayListExtra(AnalyticsActivity().KEY_MY_DATA, ArrayList(matchIdList))
        intent.putExtra(AnalyticsActivity().KEY_ACCESS_ID, ouid)
        startActivityForResult(intent, HomeFragment().RESULT_REQUEST_CODE)
    }

    fun showSearchList(matchtype: DataManager.matchType, matchIdList: List<String>) {
        val fragment = SearchListFragment()
        val bundle = Bundle()
        bundle.putStringArrayList(SearchListFragment().KEY_SEARCH_MATCH_ID, ArrayList(matchIdList))
        bundle.putInt(SearchListFragment().KEY_SEARCH_MATCH_TYPE, matchtype.ordinal)
        bundle.putParcelable(SearchListFragment().KEY_SEARCH_USER_INFO, mSearchUserInfo)

        fragment.arguments = bundle
        FragmentUtils().loadFragment(
            fragment,
            R.id.fragment_container,
            fragmentManager!!,
            true
        )
    }

    override fun showHighRank(userHighRankResponse: List<UserHighRankResponse>) {
        if (userHighRankResponse.isEmpty() || userHighRankResponse.size == 0) {
            showError(SearchHomePresenter().ERROR_EMPTY)
            return
        }

        for (item in userHighRankResponse) {
            if (DataManager.matchType.normalMatch.matchType == item.matchType) {
                mNormalMatchResponse = item
            } else if (DataManager.matchType.coachMatch.matchType == item.matchType) {
                mCoachMatchResponse = item
            }
        }

        var normalRank: String? = null
        var normalDate: String? = null
        var coachRank: String? = null
        var coachDate: String? = null

        for (item in DivisionEnum.values()) {
            if (mNormalMatchResponse != null && item.divisionId.equals(mNormalMatchResponse!!.division)) {
                mNormalDivision = item.divisionName
                normalRank = mNormalDivision
                normalDate = mNormalMatchResponse!!.achievementDate
            } else if (mCoachMatchResponse != null && item.divisionId.equals(mCoachMatchResponse!!.division)) {
                mCoachDivision = item.divisionName
                coachRank = mCoachDivision
                coachDate = mCoachMatchResponse!!.achievementDate
            }
        }

        // UI State 업데이트
        uiState = uiState.copy(
            normalHighRank = normalRank,
            normalAchievementDate = normalDate,
            coachHighRank = coachRank,
            coachAchievementDate = coachDate
        )
    }

    override fun showError(error: Int) {
        when (error) {
            DataManager().ERROR_UNAUTHORIZED,
            DataManager().ERROR_FORBIDDEN,
            DataManager().ERROR_NOT_FOUND,
            DataManager().ERROR_METHOD_NOT_ALLOWED,
            DataManager().ERROR_REQUEST_ENTITY_TOO_LARGE,
            DataManager().ERROR_TOO_MANY_REQUEST,
            DataManager().ERROR_INTERNAL_SERVER_ERROR,
            DataManager().ERROR_OTHERS,
            DataManager().ERROR_BAD_REQUEST -> {
                hideLoading(false)
                mSearchHomePresenter.dropView()
            }
            DataManager().ERROR_NETWORK_DISCONNECTED,
            DataManager().ERROR_GATEWAY_TIMEOUT -> {
                hideLoading(true)
                mSearchHomePresenter.dropView()
                (activity as MainActivity).showErrorPopup(error)
            }
        }

    }
}