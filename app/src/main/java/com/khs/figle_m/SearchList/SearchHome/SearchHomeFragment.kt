package com.khs.figle_m.SearchList.SearchHome

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khs.figle_m.Analytics.AnalyticsActivity
import com.khs.figle_m.Base.BaseFragment
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Home.HomeFragment
import com.khs.figle_m.MainActivity
import com.khs.figle_m.R
import com.khs.figle_m.Response.DTO.MatchInfoDTO
import com.khs.figle_m.Response.UserHighRankResponse
import com.khs.figle_m.Response.UserResponse
import com.khs.figle_m.SearchList.Common.CustomPagerAdapter
import com.khs.figle_m.SearchList.SearchContract
import com.khs.figle_m.SearchList.SearchHomePresenter
import com.khs.figle_m.SearchList.SearchListFragment
import com.khs.figle_m.utils.DivisionEnum
import com.khs.figle_m.utils.FragmentUtils
import kotlinx.android.synthetic.main.fragment_searchlist.btn_back
import kotlinx.android.synthetic.main.fragment_searchlist_ver2.*
import okhttp3.ResponseBody


class SearchHomeFragment : BaseFragment(),
    SearchContract.View {
    val TAG: String = javaClass.name
    val DEBUG: Boolean = true

    open val KEY_SEARCH_USER_INFO: String = "SearchUserInfo"

    lateinit var mSearchHomePresenter: SearchHomePresenter

    var mOfficialGameMatchIdList: List<String> = arrayListOf()
    var mCoachModeMatchIdList: List<String> = arrayListOf()
    lateinit var mSearchUserInfo: UserResponse

    lateinit var mOfficialView: MatchView
    lateinit var mCoachView: MatchView

    var mCoachDivision: String = ""
    var mNormalDivision: String = ""

    var mNormalMatchResponse: UserHighRankResponse? = null
    var mCoachMatchResponse: UserHighRankResponse? = null


    open val KEY_MATCH_DETAIL_LIST: String = "KEY_MATCH_DETAIL_LIST"
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
        val v: View = inflater.inflate(R.layout.fragment_searchlist_ver2, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()
        if (isRestartApp) return
        mSearchHomePresenter!!.takeView(this)
        initView()
        initMyInfoData()
        initListData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mSearchHomePresenter!!.dropView()
    }

    fun initView() {
        btn_back.setOnClickListener {
            FragmentUtils().loadFragment(
                HomeFragment.getInstance(),
                R.id.fragment_container,
                fragmentManager!!
            )
        }
        mOfficialView = MatchView(context!!)
        mCoachView = MatchView(context!!)

        mOfficialView.updateView(DataManager.matchType.normalMatch.matchType)
        mCoachView.updateView(DataManager.matchType.coachMatch.matchType)

        viewPager_search.adapter =
            CustomPagerAdapter(
                context!!,
                mOfficialView,
                mCoachView
            )
        viewPager_search.currentItem = 0

        match_indicator.setViewPager(viewPager_search)
    }

    fun initRateView(accessId: String) {
        initRateView(accessId, mOfficialGameMatchIdList, mCoachModeMatchIdList)
    }

    fun initRateView(accessId : String, officialModeList: List<String>, coachModeList: List<String>) {
        val officialModeView = SearchHomeRateView(context!!)
        val coachModeView = SearchHomeRateView(context!!)

        //승률 갯수 20개로 한정
        officialModeView.updateView(accessId, DataManager.matchType.normalMatch, officialModeList)
        coachModeView.updateView(accessId, DataManager.matchType.coachMatch, coachModeList)

        viewPager_team.adapter =
            CustomPagerAdapter(
                context!!,
                officialModeView,
                coachModeView
            )
        team_indicator.setViewPager(viewPager_team)
    }

    fun initMyInfoData() {
        arguments.let {
            mSearchUserInfo = arguments!!.getParcelable<UserResponse>(KEY_SEARCH_USER_INFO)!!
        }
        txt_MyNickName.text = mSearchUserInfo.nickname
        txt_Level.text = mSearchUserInfo.level
        mSearchUserInfo.teamPrice.let{
            txt_team_price.text = mSearchUserInfo.teamPrice
            txt_team_price.visibility = View.VISIBLE
        }
        mSearchHomePresenter.getUserHighRank(mSearchUserInfo.accessId)
    }

    fun initListData() {

        mSearchHomePresenter!!.getMatchId(
            mSearchUserInfo.accessId!!,
            DataManager.matchType.normalMatch,
            DataManager.getInstance().offset,
            DataManager.getInstance().SEARCH_LIMIT
        )

        mSearchHomePresenter!!.getMatchId(
            mSearchUserInfo.accessId!!,
            DataManager.matchType.coachMatch,
            DataManager.getInstance().offset,
            DataManager.getInstance().SEARCH_LIMIT
        )
    }

    override fun showLoading() {
        Log.v(TAG, "showLoading(...)")
        avi_loading2.visibility = View.VISIBLE
        group_content.visibility = View.GONE
//        group_rate.visibility = View.GONE
        viewPager_team.visibility = View.GONE
        viewPager_search.visibility = View.GONE
        txt_title.visibility = View.GONE
        avi_loading2.show(false)
    }

    override fun hideLoading(isError: Boolean) {
        Log.v(TAG, "hideLoading(...)")
        avi_loading2.hide()
        avi_loading2.visibility = View.GONE
        group_content.visibility = View.VISIBLE
//            group_rate.visibility = View.VISIBLE
        viewPager_team.visibility = View.VISIBLE
        viewPager_search.visibility = View.VISIBLE
        btn_back.visibility = View.VISIBLE
        txt_title.visibility = View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    override fun showOfficialGameMatchIdList(userMatchIdResponse: ResponseBody?) {
        userMatchIdResponse ?: return
        var result: String = userMatchIdResponse.string()
        mOfficialGameMatchIdList = result.removeSurrounding("[", "]").replace("\"", "").split(",")

        if (result == null || result.isEmpty() || "[]".equals(result)) {
            Log.v(TAG,"officialGmae is null")
            mOfficialView.showEmptyView()
            return
        } else {
            mOfficialView.hideEmptyView()
        }
        mOfficialView.setOnClickListener {
            showSearchList(DataManager.matchType.normalMatch, mOfficialGameMatchIdList)
        }
        initRateView(mSearchUserInfo.accessId)
    }

    @SuppressLint("SetTextI18n")
    override fun showCoachModeMatchIdList(matchDetailResponse: ResponseBody?) {
        matchDetailResponse ?: return
        var result: String = matchDetailResponse.string()
        mCoachModeMatchIdList = result.removeSurrounding("[", "]").replace("\"", "").split(",")

        if (result == null || result.isEmpty() || "[]".equals(result)) {
            Log.v(TAG,"coachList is null")
            mCoachView.showEmptyView()
            return
        } else {
            mCoachView.hideEmptyView()
        }
        hideLoading(false)
        mCoachView.setOnClickListener {
            showSearchList(DataManager.matchType.coachMatch, mOfficialGameMatchIdList)
        }
        initRateView(mSearchUserInfo.accessId)
    }

    override fun showAnaysisInfo(
       userMatchList: List<MatchInfoDTO>,
        opposingUserList: List<MatchInfoDTO>
    ) {
        Log.v(TAG,"userMatchList : $userMatchList")
        group_sq.setOnClickListener {
            val intent = Intent(context, AnalyticsActivity::class.java)
            intent.putParcelableArrayListExtra(AnalyticsActivity().KEY_MY_DATA, ArrayList(userMatchList))
            intent.putParcelableArrayListExtra(AnalyticsActivity().KEY_OPPOSING_USER_DATA, ArrayList(opposingUserList))
            startActivityForResult(intent, HomeFragment().RESULT_REQUEST_CODE)
        }
    }

    fun showSearchList(matchtype: DataManager.matchType, matchIdList: List<String>) {
        var fragment = SearchListFragment()
        var bundle = Bundle()
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

        for (item in DivisionEnum.values()) {
            if (mNormalMatchResponse != null && item.divisionId.equals(mNormalMatchResponse!!.division)) {
                mNormalDivision = item.divisionName
                txt_High_Rank.text = mNormalDivision ?: "-"
                txt_Achievement_Date.text =
                    mNormalMatchResponse!!.achievementDate.replace("T", " / ")
            } else if (mCoachMatchResponse != null && item.divisionId.equals(mCoachMatchResponse!!.division)) {
                mCoachDivision = item.divisionName
                txt_CoachMode_High_Rank.text = mCoachDivision ?: "-"
                txt_CoachMode_Achievement_Date.text =
                    mCoachMatchResponse!!.achievementDate.replace("T", " / ")
            }
        }
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