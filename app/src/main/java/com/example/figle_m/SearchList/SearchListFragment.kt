package com.example.figle_m.SearchList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.figle_m.Base.BaseFragment
import com.example.figle_m.Data.DataManager
import com.example.figle_m.R
import com.example.figle_m.Response.DTO.MatchInfoDTO
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.Response.UserHighRankResponse
import com.example.figle_m.Response.UserResponse
import com.example.figle_m.SearchList.SearchDetailView.SearchDetailDialogFragment
import com.example.figle_m.SearchList.SearchDetailView.customView.PieChartView
import com.example.figle_m.utils.DivisionEnum
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fragment_searchlist.*


class SearchListFragment : BaseFragment(), SearchContract.View {
    val TAG: String = javaClass.name
    val DEBUG: Boolean = false

    open val KEY_SEARCH_USER_INFO: String = "SearchUserInfo"
    open val KEY_MATCH_ID_LIST: String = "MatchIdList"

    lateinit var mSearchPresenter: SearchPresenter
    lateinit var mSearchResponseList: ArrayList<MatchDetailResponse>
    lateinit var mMatchIdList: MutableList<String>
    lateinit var mSearchUserInfo: UserResponse

    lateinit var mRecyclerView: RecyclerView
    lateinit var mEmptyView: TextView
    lateinit var mNickNameView: TextView
    lateinit var mLevelView: TextView
    lateinit var mDivisionView: TextView
    lateinit var mAchievementDateView: TextView
    lateinit var mRateTextView: TextView
    lateinit var mCustomPieChartView: PieChartView


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
        mLevelView = view!!.findViewById(R.id.txt_Level)
        mDivisionView = view!!.findViewById(R.id.txt_High_Rank)
        mAchievementDateView = view!!.findViewById(R.id.txt_Achievement_Date)
        mEmptyView = view!!.findViewById(R.id.txt_emptyView)
        mRecyclerView = view!!.findViewById(R.id.layout_recyclerview)
        mRateTextView = view!!.findViewById(R.id.txt_rate)
        mCustomPieChartView = view!!.findViewById(R.id.cview_pie_chart)
    }

    fun initMyInfoData() {
        arguments.let {
            mSearchUserInfo = arguments!!.getParcelable<UserResponse>(KEY_SEARCH_USER_INFO)!!
        }
        mNickNameView.text = mSearchUserInfo.nickname
        mLevelView.text = mSearchUserInfo.level
        mLevelView.text = mSearchUserInfo.level
        mSearchPresenter.getUserHighRank(mSearchUserInfo.accessId)
    }

    fun initListData() {
        arguments.let {
            mSearchResponseList = arrayListOf()
            mMatchIdList = mutableListOf()
            mMatchIdList.addAll((arguments!!.get(KEY_MATCH_ID_LIST) as Array<String>).toList())
        }

        for (item in mMatchIdList) {
            if (DEBUG) Log.v(TAG, "item, matchId : ${item} ")
            mSearchPresenter.getMatchDetailList(item)
        }
        context ?: return
        val layoutManager = LinearLayoutManager(context)
        mRecyclerView.addItemDecoration(SearchDecoration(10))
        mRecyclerView.setLayoutManager(layoutManager)
        mRecyclerView.adapter =
            SearchListAdapter(context!!, mSearchUserInfo.accessId, mSearchResponseList, {
                Log.v(TAG,"ItemClick! ${it.matchInfo}")
                showDetail(mSearchUserInfo.accessId, it)
            })

        Log.v(TAG, "SearchList total count ::: ${mRecyclerView.adapter!!.itemCount}")
    }

    fun showDetail(accessId: String, matchDetailResponse: MatchDetailResponse) {
        val searchDetailDialogFragment = SearchDetailDialogFragment.getInstance()
        val bundle = Bundle()
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
        Log.v(TAG,"showLoading(...)")
        avi_loading.visibility = View.VISIBLE
        group_info.visibility = View.GONE
//        group_rate.visibility = View.GONE
        layout_recyclerview.visibility = View.GONE
        avi_loading.show(false)
    }

    override fun hideLoading(isError: Boolean) {
        Log.v(TAG, "hideLoading(...) ${mMatchIdList.size} , ${mRecyclerView.adapter!!.itemCount}")
        if (mMatchIdList.size <= mRecyclerView.adapter!!.itemCount || isError) {
            avi_loading.hide()
            avi_loading.visibility = View.GONE
            group_info.visibility = View.VISIBLE
//            group_rate.visibility = View.VISIBLE
            layout_recyclerview.visibility = View.VISIBLE
            group_info.visibility = View.VISIBLE
        }
    }

    override fun showSearchList(searchResponse: MatchDetailResponse?) {
        searchResponse ?: return
        mEmptyView.visibility = View.GONE
        Log.v(TAG, "showSearchList : ${searchResponse!!.matchId}")
        synchronized("Lock") {
            mSearchResponseList.add(searchResponse!!)
//            if (mSearchResponseList.size == DataManager().SEARCH_LIMIT) {
                mSearchResponseList.sortByDescending { it.matchDate }
                mRecyclerView.adapter!!.notifyItemInserted(mSearchResponseList.size - 1)
                mRecyclerView.adapter!!.notifyDataSetChanged()
//            }
        }
        initRate()
    }

    override fun showHighRank(userHighRankResponse: List<UserHighRankResponse>) {
        var normalMatchResponse: UserHighRankResponse? = null
        var division:String? = null
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

    fun initRate() {
        var win = 0
        var draw = 0
        var lose = 0
        for (item in mSearchResponseList) {
            var myInfo: MatchInfoDTO? = null
            if (mSearchUserInfo.accessId == item.matchInfo[0].accessId) {
                myInfo = item.matchInfo[0]
            } else {
                myInfo = item.matchInfo[1]
            }
            when (myInfo.matchDetail.matchResult) {
                "승" -> win++
                "무" -> draw++
                "패" -> lose++
            }
        }

        val pieEntryList = arrayListOf(
            PieEntry(win.toFloat(), null,null,null),
            PieEntry(draw.toFloat(), null,null,null),
            PieEntry(lose.toFloat(), null,null,null)
        )
        mCustomPieChartView.setData(pieEntryList)
    }

//    fun initRate() {
//        var win = 0
//        var draw = 0
//        var lose = 0
//        for (item in mSearchResponseList) {
//            var myInfo: MatchInfoDTO? = null
//            if (mSearchUserInfo.accessId == item.matchInfo[0].accessId) {
//                myInfo = item.matchInfo[0]
//            } else {
//                myInfo = item.matchInfo[1]
//            }
//            when (myInfo.matchDetail.matchResult) {
//                "승" -> win++
//                "무" -> draw++
//                "패" -> lose++
//            }
//        }
//        mRateTextView.text =
//            "최근 ${win + draw + lose} 경기 승:$win / 무:$draw / 패:$lose"
//        mRateTextView.visibility = View.VISIBLE
//
//    }


    override fun showError(error: String) {
        if (SearchPresenter().ERROR_EMPTY.equals(error)) {
            mEmptyView.visibility = View.VISIBLE
            hideLoading(true)
        }
    }
}