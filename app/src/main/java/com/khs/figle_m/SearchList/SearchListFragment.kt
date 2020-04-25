package com.khs.figle_m.SearchList

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.github.mikephil.charting.data.PieEntry
import com.khs.figle_m.Base.BaseFragment
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Home.HomeFragment
import com.khs.figle_m.R
import com.khs.figle_m.Response.DTO.MatchInfoDTO
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.Response.UserHighRankResponse
import com.khs.figle_m.Response.UserResponse
import com.khs.figle_m.SearchDetail.SearchDetailDialogFragment
import com.khs.figle_m.SearchList.SearchListView.SearchListView
import com.khs.figle_m.utils.DivisionEnum
import com.khs.figle_m.utils.FragmentUtils
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.android.synthetic.main.fragment_searchlist.*
import okhttp3.ResponseBody


class SearchListFragment : BaseFragment(), SearchContract.View {
    val TAG: String = javaClass.name
    val DEBUG: Boolean = false

    open val KEY_SEARCH_USER_INFO: String = "SearchUserInfo"

    lateinit var mSearchPresenter: SearchPresenter

    lateinit var mOfficialGameMatchList: ArrayList<MatchDetailResponse>
    lateinit var mCoachModeMatchList: ArrayList<MatchDetailResponse>

    lateinit var mOfficialGameMatchIdList: List<String>
    lateinit var mCoachModeMatchIdList: List<String>
    lateinit var mSearchUserInfo: UserResponse

    lateinit var mOfficialGameView: SearchListView
    lateinit var mCoachModeView: SearchListView

    lateinit var mNickNameView: TextView
    lateinit var mSubNickNameView: TextView
    lateinit var mLevelView: TextView
    lateinit var mDivisionView: TextView
    lateinit var mAchievementDateView: TextView
    lateinit var mRateTextView: TextView
    lateinit var mCustomPieChartView: PieChartView

    lateinit var mSelectedMatchInfo:MatchDetailResponse


    open val KEY_MATCH_DETAIL_LIST: String = "KEY_MATCH_DETAIL_LIST"
    override fun initPresenter() {
        mSearchPresenter = SearchPresenter()
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
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_searchlist, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()
        if (isRestartApp) return
        mSearchPresenter!!.takeView(this)
        initView()
        initMyInfoData()
        initListData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mSearchPresenter!!.dropView()
    }

    fun initView() {
        mNickNameView = view!!.findViewById(R.id.txt_MyNickName)
        mSubNickNameView = view!!.findViewById(R.id.txt_sub_MyNickName)
        mLevelView = view!!.findViewById(R.id.txt_Level)
        mDivisionView = view!!.findViewById(R.id.txt_High_Rank)
        mAchievementDateView = view!!.findViewById(R.id.txt_Achievement_Date)
        mRateTextView = view!!.findViewById(R.id.txt_rate)
        mCustomPieChartView = view!!.findViewById(R.id.cview_pie_chart)
        btn_back.setOnClickListener {
            FragmentUtils().loadFragment(
                HomeFragment.getInstance(),
                R.id.fragment_container,
                fragmentManager!!
            )
        }
    }

    fun initMyInfoData() {
        arguments.let {
            mSearchUserInfo = arguments!!.getParcelable<UserResponse>(KEY_SEARCH_USER_INFO)!!
        }
        mNickNameView.text = mSearchUserInfo.nickname
        mSubNickNameView.text = mSearchUserInfo.nickname
        mLevelView.text = mSearchUserInfo.level
        mLevelView.text = mSearchUserInfo.level
        mSearchPresenter.getUserHighRank(mSearchUserInfo.accessId)
    }

    fun initListData() {
        mOfficialGameMatchList = arrayListOf()
        mCoachModeMatchList = arrayListOf()

        //Todo: coach 테스트 완료 후 변경
        mCoachModeView = SearchListView(context!!)
        mCoachModeView.setSearchUserInfo(mSearchUserInfo)

//        var mCoachModeLockView = LockView(context!!)
//        mCoachModeLockView.updateImageView(R.drawable.lock)
//        mCoachModeLockView.updateTextView("감독모드\n추후 공개 예정입니다.")

        mOfficialGameView = SearchListView(context!!)
        mOfficialGameView.setSearchUserInfo(mSearchUserInfo)
        viewPager.adapter =
            SearchListPagerAdapter(context!!, mOfficialGameView, mCoachModeView)

        //////////////////////////////////////////////////////////////////////

        //감독모드 추가 후 Page 별 Rate falg 설
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                when (position) {
                    0 -> {
                        txt_play_mode.text = "1 ON 1"
                        initRate(true)
                    }
                    1 -> {
                        txt_play_mode.text = "감독모드"
                        initRate(false)
                    }
                }
            }
            override fun onPageSelected(position: Int) {
            }
        })

        //////////////////////////////////////////////////////////////////////

        viewPager.currentItem = 0

        mSearchPresenter!!.getMatchId(
            mSearchUserInfo.accessId!!,
            DataManager.matchType.normalMatch,
            DataManager.getInstance().offset,
            DataManager.getInstance().SEARCH_LIMIT
        )

        mSearchPresenter!!.getMatchId(
            mSearchUserInfo.accessId!!,
            DataManager.matchType.coachMatch,
            DataManager.getInstance().offset,
            DataManager.getInstance().SEARCH_LIMIT
        )
        initIndicator()
    }

    fun initIndicator() {
        val dotsIndicator = view!!.findViewById<WormDotsIndicator>(R.id.dots_indicator)
        dotsIndicator.setViewPager(viewPager)
    }

    fun showDetail(accessId: String, matchDetailResponse: MatchDetailResponse) {
        val searchDetailDialogFragment = SearchDetailDialogFragment.getInstance()
        val bundle = Bundle()
        val isCoachMode = viewPager.currentItem == 1
        mSelectedMatchInfo = matchDetailResponse
        bundle.putBoolean(searchDetailDialogFragment.KEY_IS_COACH_MODE, isCoachMode)
        bundle.putParcelable(searchDetailDialogFragment.KEY_MATCH_DETAIL_INFO, matchDetailResponse)
        bundle.putString(searchDetailDialogFragment.KEY_SEARCH_ACCESSID, accessId)
        searchDetailDialogFragment.arguments = bundle
        if (!searchDetailDialogFragment.isAdded) {
            searchDetailDialogFragment.show(
                fragmentManager!!,
                SearchDetailDialogFragment().TAG_MATCH_DETAIL_DIALOG
            )
        }
    }

    override fun showLoading() {
        Log.v(TAG, "showLoading(...)")
        avi_loading.visibility = View.VISIBLE
        group_info.visibility = View.GONE
//        group_rate.visibility = View.GONE
        viewPager.visibility = View.GONE
        avi_loading.show(false)
        container_indicator.visibility = View.INVISIBLE
    }

    override fun hideLoading(isError: Boolean) {
        Log.v(TAG, "hideLoading(...)")
        avi_loading.hide()
        avi_loading.visibility = View.GONE
        group_info.visibility = View.VISIBLE
//            group_rate.visibility = View.VISIBLE
        viewPager.visibility = View.VISIBLE
        group_info.visibility = View.VISIBLE
        btn_back.visibility = View.VISIBLE
        container_indicator.visibility = View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    override fun showOfficialGameMatchIdList(userMatchIdResponse: ResponseBody?) {
        userMatchIdResponse ?: return
        var result: String = userMatchIdResponse.string()
        mOfficialGameMatchIdList = arrayListOf()
        mOfficialGameMatchIdList = result.removeSurrounding("[", "]").replace("\"", "").split(",")

        if (result == null || result.isEmpty() || "[]".equals(result)) {
            Log.v(TAG,"officialGmae is null")
            mOfficialGameView.showEmptyView()
            return
        }

        for (item in mOfficialGameMatchIdList) {
            if (DEBUG) Log.v(TAG, "item, matchId : ${item} ")
            mSearchPresenter.getMatchDetailList(true, item)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun showCoachModeMatchIdList(matchDetailResponse: ResponseBody?) {
        matchDetailResponse ?: return
        var result: String = matchDetailResponse.string()
        mCoachModeMatchIdList = arrayListOf()
        mCoachModeMatchIdList = result.removeSurrounding("[", "]").replace("\"", "").split(",")

        if (result == null || result.isEmpty() || "[]".equals(result)) {
            Log.v(TAG,"coachList is null")
            mCoachModeView.showEmptyView()
            return
        }

        for (item in mCoachModeMatchIdList) {
            if (true) Log.v(TAG, "coach item, matchId : ${item} ")
            mSearchPresenter.getMatchDetailList(false, item)
        }
    }

    override fun showOfficialGameList(searchResponse: MatchDetailResponse?) {
        searchResponse ?: return
        Log.v(TAG, "showOfficialGameList : ${searchResponse!!.matchId}")
        synchronized("Lock") {
            mOfficialGameMatchList.add(searchResponse!!)
            mOfficialGameMatchList.sortByDescending { it.matchDate }
        }

        if(DEBUG) Log.v(TAG,"mOfficialGameMatchList size : ${mOfficialGameMatchList.size} , mOfficialGameMatchIdList size : ${mOfficialGameMatchIdList.size}")
        if (mOfficialGameMatchList.size == mOfficialGameMatchIdList.size) {
            initRate(true)
            updateOfficialGameList()
        }
    }

    override fun showCoachModeList(searchResponse: MatchDetailResponse?) {
        searchResponse ?: return
        Log.v(TAG, "showCoachModeList : ${searchResponse!!.matchId}")
        synchronized("Lock") {
            mCoachModeMatchList.add(searchResponse!!)
            mCoachModeMatchList.sortByDescending { it.matchDate }
        }
        if(DEBUG)  Log.v(TAG,"mCoachModeMatchList size : ${mCoachModeMatchList.size} , mOfficialGameMatchIdList size : ${mCoachModeMatchList.size}")
        if (mCoachModeMatchList.size == mCoachModeMatchIdList.size) {
            initRate(false)
            updateCoachModeList()
        }
    }

    fun updateCoachModeList() {
        mCoachModeView.updateView(mCoachModeMatchList, {
            showDetail(mSearchUserInfo.accessId, it)
        })
    }

    fun updateOfficialGameList() {
        hideLoading(false)
        mOfficialGameView.updateView(mOfficialGameMatchList, {
            showDetail(mSearchUserInfo.accessId, it)
        })
    }

    override fun showHighRank(userHighRankResponse: List<UserHighRankResponse>) {
        var normalMatchResponse: UserHighRankResponse? = null
        var division: String? = null
        for (item in userHighRankResponse) {
            if (DataManager.matchType.normalMatch.matchType == item.matchType)
                normalMatchResponse = item
        }

        normalMatchResponse ?: return

        for (item in DivisionEnum.values()) {
            if (item.divisionId.equals(normalMatchResponse.division))
                division = item.divisionName
        }

        mDivisionView.text = division ?: "-"
        mAchievementDateView.text = normalMatchResponse!!.achievementDate.replace("T", " / ")
    }

    fun initRate(isOfficialGame: Boolean) {
        var win = 0
        var draw = 0
        var lose = 0
        var arrayList = mutableListOf<MatchDetailResponse>()
        if (isOfficialGame) {
            arrayList = mOfficialGameMatchList
        } else {
            arrayList = mCoachModeMatchList
        }

        for (item in arrayList) {
            var myInfo: MatchInfoDTO? = null
            if (mSearchUserInfo.accessId == item.matchInfo[0].accessId) {
                myInfo = item.matchInfo[0]
            } else {
                myInfo = item.matchInfo[1]
            }
            myInfo.matchDetail.matchResult ?: continue
            when (myInfo.matchDetail.matchResult) {
                "승" -> win++
                "무" -> draw++
                "패" -> lose++
            }
        }

        val pieEntryList = arrayListOf(
            PieEntry(win.toFloat(), "승 : $win", null, null),
            PieEntry(draw.toFloat(), "무 : $draw", null, null),
            PieEntry(lose.toFloat(), "패 : $lose", null, null)
        )
        mCustomPieChartView.setData(pieEntryList, win + draw + lose)
    }

    override fun showError(error: Int) {
        if (SearchPresenter().ERROR_EMPTY.equals(error)) {
            hideLoading(true)
        }
    }
}