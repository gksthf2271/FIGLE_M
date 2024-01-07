package com.khs.figle_m.searchList.SearchHome

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khs.data.nexon_api.response.UserCareerHighResponse
import com.khs.data.nexon_api.response.UserResponse
import com.khs.figle_m.analytics.AnalyticsActivity
import com.khs.figle_m.base.BaseFragment
import com.khs.figle_m.data.DataManager
import com.khs.figle_m.home.HomeFragment
import com.khs.figle_m.MainActivity
import com.khs.figle_m.R
import com.khs.figle_m.searchList.Common.CustomPagerAdapter
import com.khs.figle_m.searchList.SearchContract
import com.khs.figle_m.searchList.SearchHomePresenter
import com.khs.figle_m.searchList.SearchListFragment
import com.khs.figle_m.trade.TradeActivity
import com.khs.figle_m.utils.DivisionEnum
import com.khs.figle_m.utils.FragmentUtils
import com.khs.figle_m.utils.LogUtil
import com.khs.figle_m.databinding.FragmentSearchlistVer2Binding
import okhttp3.ResponseBody


class SearchHomeFragment : BaseFragment(),
    SearchContract.View {
    val TAG: String = javaClass.simpleName
    val DEBUG: Boolean = true
    lateinit var mBinding : FragmentSearchlistVer2Binding
    val KEY_SEARCH_USER_INFO: String = "SearchUserInfo"

    lateinit var mSearchHomePresenter: SearchHomePresenter

    var mOfficialGameMatchIdList: List<String> = arrayListOf()
    var mCoachModeMatchIdList: List<String> = arrayListOf()
    lateinit var mSearchUserInfo: UserResponse

    lateinit var mOfficialView: MatchView
    lateinit var mCoachView: MatchView

    var mCoachDivision: String = ""
    var mNormalDivision: String = ""

    var mNormalMatchResponse: UserCareerHighResponse? = null
    var mCoachMatchResponse: UserCareerHighResponse? = null


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
    ): View? {
        mBinding = FragmentSearchlistVer2Binding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        if (isRestartApp) return
        mSearchHomePresenter = mSearchHomePresenter ?: SearchHomePresenter()
        mSearchHomePresenter.takeView(this)
        initView()
        initMyInfoData()
        initListData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mSearchHomePresenter.dropView()
    }

    fun initView() {
        mBinding.btnBack.setOnClickListener {
            FragmentUtils().loadFragment(
                HomeFragment.getInstance(),
                R.id.fragment_container,
                parentFragmentManager
            )
        }

        context?.let { context ->
            mOfficialView = MatchView(context)
            mCoachView = MatchView(context)

            mOfficialView.updateView(DataManager.matchType.normalMatch.matchType)
            mCoachView.updateView(DataManager.matchType.coachMatch.matchType)

            mBinding.viewPagerSearch.adapter =
                CustomPagerAdapter(
                    context,
                    mOfficialView,
                    mCoachView
                )
            mBinding.viewPagerSearch.currentItem = 0

            mBinding.matchIndicator.setViewPager(mBinding.viewPagerSearch)
        }
    }

    private fun initRateView(accessId: String) {
        initRateView(accessId, mOfficialGameMatchIdList, mCoachModeMatchIdList)
    }

    private fun initRateView(accessId : String, officialModeList: List<String>, coachModeList: List<String>) {
        context?.let { context ->
            val officialModeView = SearchHomeRateView(context)
            val coachModeView = SearchHomeRateView(context)

            //승률 갯수 20개로 한정
            officialModeView.updateView(accessId, DataManager.matchType.normalMatch, officialModeList)
            coachModeView.updateView(accessId, DataManager.matchType.coachMatch, coachModeList)

            mBinding.viewPagerTeam.adapter =
                CustomPagerAdapter(
                    context,
                    officialModeView,
                    coachModeView
                )
            mBinding.teamIndicator.setViewPager(mBinding.viewPagerTeam)
        }
    }

    private fun initMyInfoData() {
        arguments?.let { bundle ->
            mSearchUserInfo = bundle.getSerializable(KEY_SEARCH_USER_INFO) as UserResponse
        }
        mBinding.txtMyNickName.text = mSearchUserInfo.nickname
        mBinding.txtLevel.text = mSearchUserInfo.level
        mSearchUserInfo.teamPrice.let{
            mBinding.txtTeamPrice.text = it
            mBinding.txtTeamPrice.visibility = View.VISIBLE
        }
        mSearchHomePresenter.getUserHighRank(mSearchUserInfo.ouid)
        mBinding.groupTrade.setOnClickListener{
            val intent = Intent(context, TradeActivity::class.java)
            intent.putExtra(TradeActivity().KEY_ACCESS_ID, mSearchUserInfo.ouid)
            startActivityForResult(intent, HomeFragment().RESULT_REQUEST_CODE)
        }
    }

    private fun initListData() {

        mSearchHomePresenter.getMatchId(
            mSearchUserInfo.ouid,
            DataManager.matchType.normalMatch,
            DataManager.offset,
            DataManager.SEARCH_LIMIT
        )

        mSearchHomePresenter.getMatchId(
            mSearchUserInfo.ouid,
            DataManager.matchType.coachMatch,
            DataManager.offset,
            DataManager.SEARCH_LIMIT
        )
    }

    override fun showLoading() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showLoading(...)")
        mBinding.apply {
            aviLoading2.visibility = View.VISIBLE
            groupContent.visibility = View.GONE
//        group_rate.visibility = View.GONE
            viewPagerTeam.visibility = View.GONE
            viewPagerSearch.visibility = View.GONE
            txtTitle.visibility = View.GONE
            aviLoading2.show(false)
        }
    }

    override fun hideLoading(isError: Boolean) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"hideLoading(...)")
        mBinding.apply {
            aviLoading2.hide()
            aviLoading2.visibility = View.GONE
            groupContent.visibility = View.VISIBLE
//            group_rate.visibility = View.VISIBLE
            viewPagerTeam.visibility = View.VISIBLE
            viewPagerSearch.visibility = View.VISIBLE
            btnBack.visibility = View.VISIBLE
            txtTitle.visibility = View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    override fun showOfficialGameMatchIdList(userMatchIdResponse: ResponseBody?) {
        userMatchIdResponse ?: return
        val result: String = userMatchIdResponse.string()
        mOfficialGameMatchIdList = result.removeSurrounding("[", "]").replace("\"", "").split(",")

        if (result.isEmpty() || "[]" == result) {
            LogUtil.vLog(LogUtil.TAG_UI, TAG,"officialGmae is null")
            mOfficialView.showEmptyView()
            return
        } else {
            mOfficialView.hideEmptyView()
        }
        mOfficialView.setOnClickListener {
            showSearchList(DataManager.matchType.normalMatch, mOfficialGameMatchIdList)
        }
        initRateView(mSearchUserInfo.ouid)
        mBinding.groupSq.setOnClickListener {
            mSearchHomePresenter.getMatchAnalysisByMatchId(mSearchUserInfo.ouid, mOfficialGameMatchIdList)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun showCoachModeMatchIdList(matchDetailResponse: ResponseBody?) {
        matchDetailResponse ?: return
        val result: String = matchDetailResponse.string()
        mCoachModeMatchIdList = result.removeSurrounding("[", "]").replace("\"", "").split(",")

        if (result.isEmpty() || "[]" == result) {
            LogUtil.vLog(LogUtil.TAG_UI, TAG,"coachList is null")
            mCoachView.showEmptyView()
            hideLoading(false)
            return
        } else {
            mCoachView.hideEmptyView()
        }
        hideLoading(false)
        mCoachView.setOnClickListener {
            showSearchList(DataManager.matchType.coachMatch, mCoachModeMatchIdList)
        }
        initRateView(mSearchUserInfo.ouid)
    }

    override fun showAnalysisInfo(accessId: String, matchIdList: List<String>) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showAnaysisInfo : $matchIdList")
        val intent = Intent(context, AnalyticsActivity::class.java)
        intent.putStringArrayListExtra(AnalyticsActivity().KEY_MY_DATA, ArrayList(matchIdList))
        intent.putExtra(AnalyticsActivity().KEY_ACCESS_ID, accessId)
        startActivityForResult(intent, HomeFragment().RESULT_REQUEST_CODE)
    }

    private fun showSearchList(matchtype: DataManager.matchType, matchIdList: List<String>) {
        val fragment = SearchListFragment()
        val bundle = Bundle()
        bundle.putStringArrayList(SearchListFragment().KEY_SEARCH_MATCH_ID, ArrayList(matchIdList))
        bundle.putInt(SearchListFragment().KEY_SEARCH_MATCH_TYPE, matchtype.ordinal)
        bundle.putSerializable(SearchListFragment().KEY_SEARCH_USER_INFO, mSearchUserInfo)

        fragment.arguments = bundle
        FragmentUtils().loadFragment(
            fragment,
            R.id.fragment_container,
            parentFragmentManager,
            true
        )
    }

    override fun showHighRank(userCareerHighResponse: List<UserCareerHighResponse>) {
        if (userCareerHighResponse.isEmpty() || userCareerHighResponse.isEmpty()) {
            showError(SearchHomePresenter().ERROR_EMPTY)
            return
        }
        for (item in userCareerHighResponse) {
            if (DataManager.matchType.normalMatch.matchType == item.matchType) {
                mNormalMatchResponse = item
            } else if (DataManager.matchType.coachMatch.matchType == item.matchType) {
                mCoachMatchResponse = item
            }
        }

        for (item in DivisionEnum.values()) {
            if (mNormalMatchResponse != null && item.divisionId.equals(mNormalMatchResponse!!.division)) {
                mNormalDivision = item.divisionName
                mBinding.txtHighRank.text = mNormalDivision ?: "-"
                mBinding.txtAchievementDate.text =
                    mNormalMatchResponse!!.achievementDate.replace("T", " / ")
            } else if (mCoachMatchResponse != null && item.divisionId.equals(mCoachMatchResponse!!.division)) {
                mCoachDivision = item.divisionName
                mBinding.txtCoachModeHighRank.text = mCoachDivision ?: "-"
                mBinding.txtCoachModeAchievementDate.text =
                    mCoachMatchResponse!!.achievementDate.replace("T", " / ")
            }
        }
    }

    override fun showError(error: Int) {
        when (error) {
            DataManager.ERROR_UNAUTHORIZED,
            DataManager.ERROR_FORBIDDEN,
            DataManager.ERROR_NOT_FOUND,
            DataManager.ERROR_METHOD_NOT_ALLOWED,
            DataManager.ERROR_REQUEST_ENTITY_TOO_LARGE,
            DataManager.ERROR_TOO_MANY_REQUEST,
            DataManager.ERROR_INTERNAL_SERVER_ERROR,
            DataManager.ERROR_OTHERS,
            DataManager.ERROR_BAD_REQUEST -> {
                hideLoading(false)
                mSearchHomePresenter.dropView()
            }
            DataManager.ERROR_NETWORK_DISCONNECTED,
            DataManager.ERROR_GATEWAY_TIMEOUT -> {
                hideLoading(true)
                mSearchHomePresenter.dropView()
                (activity as MainActivity).showErrorPopup(error)
            }
        }

    }
}