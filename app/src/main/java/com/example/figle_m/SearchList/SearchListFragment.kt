package com.example.figle_m.SearchList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.figle_m.BaseFragment
import com.example.figle_m.Data.DataManager
import com.example.figle_m.R
import com.example.figle_m.Response.DTO.MatchInfoDTO
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.Response.UserHighRankResponse
import com.example.figle_m.Response.UserResponse
import com.example.figle_m.databinding.FragmentSearchlistBinding
import com.example.figle_m.utils.DivisionEnum
import okhttp3.ResponseBody
import org.json.JSONObject

class SearchListFragment : BaseFragment(), SearchContract.View {
    val TAG: String = javaClass.name
    val DEBUG: Boolean = false

    open val KEY_SEARCH_USER_INFO: String = "SearchUserInfo"
    open val KEY_MATCH_ID_LIST: String = "MatchIdList"

    lateinit var mSearchPresenter: SearchPresenter
    var mDataBinding: FragmentSearchlistBinding? = null
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
        mDataBinding = DataBindingUtil.findBinding(v)
        return v
    }

    override fun onStart() {
        super.onStart()
        mSearchPresenter!!.takeView(this)
        initView()
        initMyInfoData()
        initListData()
    }

    override fun onResume() {
        super.onResume()
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
            SearchListAdapter(context!!, mSearchUserInfo.accessId, mSearchResponseList)

        Log.v(TAG, "SearchList total count ::: ${mRecyclerView.adapter!!.itemCount}")
    }


    override fun showLoading() {
        Log.v(TAG,"showLoading(...)")
    }

    override fun hideLoading() {
        Log.v(TAG,"hideLoading(...)")
    }

    override fun showSearchList(searchResponse: MatchDetailResponse?) {
        searchResponse ?: return
        mEmptyView.visibility = View.GONE
        if (DEBUG) Log.v(TAG, "showSearchList : ${searchResponse!!.matchId}")
        synchronized("Lock") {
            mSearchResponseList.add(searchResponse!!)
            if (mSearchResponseList.size == DataManager().SEARCH_LIMIT) {
                mSearchResponseList.sortByDescending { it.matchDate }
                mRecyclerView.adapter!!.notifyItemInserted(mSearchResponseList.size - 1)
                mRecyclerView.adapter!!.notifyDataSetChanged()
            }
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
        if (DataManager().SEARCH_LIMIT == mRecyclerView.adapter!!.itemCount) {
            for (item in mSearchResponseList) {
                var myInfo : MatchInfoDTO? = null
                if (mSearchUserInfo.accessId == item.matchInfo[0].accessId){
                    myInfo = item.matchInfo[0]
                } else {
                    myInfo = item.matchInfo[1]
                }
                when(myInfo.matchDetail.matchResult){
                    "승" -> win++
                    "무" -> draw++
                    "패" -> lose++
                }
            }
            mRateTextView.text =
                "최근 ${win+draw+lose} 경기 승:$win / 무:$draw / 패:$lose"
            mRateTextView.visibility = View.VISIBLE
        }
    }


    override fun showError(error: String) {
        if (SearchPresenter().ERROR_EMPTY.equals(error)) mEmptyView.visibility = View.VISIBLE
    }
}