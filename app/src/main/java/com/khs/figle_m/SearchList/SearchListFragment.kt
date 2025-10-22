package com.khs.figle_m.SearchList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.khs.figle_m.Base.BaseFragment
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.R
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.Response.UserResponse
import com.khs.figle_m.SearchDetail.SearchDetailDialogFragment
import com.khs.figle_m.SearchList.SearchListView.SearchListPresenter
import com.khs.figle_m.Utils.LogUtil
import com.khs.figle_m.ui.components.MatchItemData
import com.khs.figle_m.ui.components.toMatchItemData
import com.khs.figle_m.ui.screens.SearchListScreen
import com.khs.figle_m.ui.screens.SearchListUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchListFragment : BaseFragment(), SearchContract.SearchListView {
    val TAG: String = javaClass.simpleName
    val DEBUG: Boolean = true

    val KEY_SEARCH_USER_INFO: String = "KEY_SEARCH_USER_INFO"
    val KEY_SEARCH_MATCH_INFO: String = "KEY_SEARCH_MATCH_INFO"
    val KEY_SEARCH_MATCH_TYPE: String = "KEY_SEARCH_MATCH_TYPE"
    val KEY_SEARCH_MATCH_ID: String = "KEY_SEARCH_MATCH_ID"

    private val mMatchDetailList = arrayListOf<MatchDetailResponse>()
    var mMatchtype: Int = 0

    var mMatchIdList: ArrayList<String> = arrayListOf()
    lateinit var mSearchUserInfo: UserResponse

    lateinit var mSelectedMatchInfo: MatchDetailResponse
    var mIsCoachMode : Boolean = false

    // Compose UI State
    private var uiState by mutableStateOf(SearchListUiState(isLoading = true))

    // Presenter
    private lateinit var mSearchListPresenter: SearchListPresenter

    // 현재 로드된 인덱스 추적
    private var currentLoadedIndex = 0

    override fun initPresenter() {
        mSearchListPresenter = SearchListPresenter()
        mSearchListPresenter.takeView(this)
    }

    companion object {
        @Volatile
        private var instance: SearchListFragment? = null

        @JvmStatic
        fun getInstance(): SearchListFragment =
            instance ?: synchronized(this) {
                instance
                    ?: SearchListFragment().also {
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
                    SearchListScreen(
                        uiState = uiState,
                        onBackClick = {
                            fragmentManager?.popBackStack()
                        },
                        onMatchClick = { matchData ->
                            // matchData.matchId로 원본 MatchDetailResponse 찾기
                            val matchDetail = mMatchDetailList.find { it.matchId == matchData.matchId }
                            matchDetail?.let {
                                showDetail(mSearchUserInfo.ouid, it)
                            }
                        },
                        onLoadMore = {
                            loadMoreMatches()
                        }
                    )
                }
            }
        }

        // 기존 XML 레이아웃 구현 (주석 처리)
        // val v: View = inflater.inflate(R.layout.fragment_searchlist, container, false)
        // return v
    }

    override fun onStart() {
        super.onStart()
        initMyInfoData()
        initListData()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mSearchListPresenter.isInitialized) {
            mSearchListPresenter.dropView()
        }
    }

    fun initMyInfoData() {
        arguments?.let { bundle ->
            mSearchUserInfo = bundle.getParcelable<UserResponse>(KEY_SEARCH_USER_INFO)!!
            mMatchtype = bundle.getInt(KEY_SEARCH_MATCH_TYPE)
            mMatchIdList = bundle.getStringArrayList(KEY_SEARCH_MATCH_ID) as ArrayList<String>
        }

        val title = when (mMatchtype) {
            DataManager.matchType.normalMatch.ordinal -> {
                mIsCoachMode = false
                "1 ON 1 경기 조회"
            }
            DataManager.matchType.coachMatch.ordinal -> {
                mIsCoachMode = true
                "감독모드 경기 조회"
            }
            else -> "경기 조회"
        }

        uiState = uiState.copy(
            title = title,
            isEmpty = mMatchIdList.isEmpty()
        )
    }

    private fun initListData() {
        if (mMatchIdList.isEmpty()) {
            uiState = uiState.copy(isEmpty = true, isLoading = false)
            return
        }

        // 초기 데이터 로드
        val pageSize = DataManager().SEARCH_PAGE_SIZE
        val endIndex = if (mMatchIdList.size > pageSize) pageSize - 1 else mMatchIdList.size - 1
        loadMatches(0, endIndex)
    }

    private fun loadMoreMatches() {
        val childCount = mMatchDetailList.size
        val range = mMatchIdList.size - childCount

        if (range > 0 && !uiState.isLoading) {
            val pageSize = DataManager().SEARCH_PAGE_SIZE
            val endIndex = if (pageSize > range) {
                childCount + range - 1
            } else {
                childCount + pageSize - 1
            }
            loadMatches(childCount, endIndex)
        } else if (range <= 0) {
            // 더 이상 로드할 데이터가 없음
            uiState = uiState.copy(hasMoreData = false)
        }
    }

    private fun loadMatches(startIndex: Int, endIndex: Int) {
        uiState = uiState.copy(isLoading = true)
        for (index in startIndex..endIndex) {
            LogUtil.vLog(LogUtil.TAG_UI, TAG, "loadMatch : $index")
            if (index < mMatchIdList.size) {
                mSearchListPresenter.getMatchDetailList(
                    mMatchtype == DataManager.matchType.normalMatch.ordinal,
                    mMatchIdList[index]
                )
            }
        }
    }

    private fun showDetail(ouid: String, matchDetailResponse: MatchDetailResponse) {
        val searchDetailDialogFragment = SearchDetailDialogFragment.getInstance()
        val bundle = Bundle()
        mSelectedMatchInfo = matchDetailResponse

        bundle.putBoolean(searchDetailDialogFragment.KEY_IS_COACH_MODE, mIsCoachMode)
        bundle.putParcelable(searchDetailDialogFragment.KEY_MATCH_DETAIL_INFO, matchDetailResponse)
        bundle.putString(searchDetailDialogFragment.KEY_SEARCH_ACCESSID, ouid)
        searchDetailDialogFragment.arguments = bundle
        if (!searchDetailDialogFragment.isAdded) {
            searchDetailDialogFragment.show(
                fragmentManager!!,
                SearchDetailDialogFragment().TAG_MATCH_DETAIL_DIALOG
            )
        }
    }

    // SearchContract.SearchListView 인터페이스 구현
    override fun showLoading() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG, "showLoading(...)")
        // 이미 uiState.isLoading = true로 설정됨
    }

    override fun hideLoading(isError: Boolean) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG, "hideLoading(...)")
        CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            uiState = uiState.copy(isLoading = false)
        }
    }

    override fun showGameList(searchResponse: MatchDetailResponse?) {
        searchResponse?.let { matchDetailResponse ->
            if (matchDetailResponse.matchInfo.size <= 1) return

            LogUtil.vLog(LogUtil.TAG_UI, TAG, "showGameList : ${matchDetailResponse.matchId}")
            mMatchDetailList.add(matchDetailResponse)

            // MatchDetailResponse를 MatchItemData로 변환
            val matchItemData = matchDetailResponse.toMatchItemData(mSearchUserInfo.ouid)
            matchItemData?.let {
                val updatedList = uiState.matchList.toMutableList()
                updatedList.add(it)
                uiState = uiState.copy(matchList = updatedList)
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            hideLoading(false)
        }
    }

    override fun showError(error: Int) {
        LogUtil.eLog(LogUtil.TAG_UI, TAG, "showError : $error")
        // 에러 처리 (필요시 구현)
    }
}