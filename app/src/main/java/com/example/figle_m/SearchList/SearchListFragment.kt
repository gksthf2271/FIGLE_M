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
import com.example.figle_m.HomeFragment
import com.example.figle_m.R
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.View.UserPresenter
import com.example.figle_m.Response.UserResponse
import com.example.figle_m.databinding.FragmentSearchlistBinding
import java.util.*
import java.util.concurrent.locks.Lock
import kotlin.collections.ArrayList

class SearchListFragment : BaseFragment(), SearchContract.View {
    val TAG: String = javaClass.name

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
            Log.v(TAG, "item, matchId : ${item} ")
            mSearchPresenter.getMatchDetailList(item)
        }
        context ?: return
        val layoutManager = LinearLayoutManager(context)
        mRecyclerView.addItemDecoration(SearchDecoration(10))
        mRecyclerView.setLayoutManager(layoutManager)
        mRecyclerView.adapter =  SearchListAdapter(context!!, mSearchUserInfo.nickname ,mSearchResponseList)

        Log.v(TAG,"SearchList total count ::: ${mRecyclerView.adapter!!.itemCount}")
    }


    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showSearchList(searchResponse: MatchDetailResponse?) {
        searchResponse ?: return
        mEmptyView.visibility = View.GONE
        Log.v(TAG,"showSearchList : ${searchResponse!!.matchId}")
        synchronized("Lock") {
            mSearchResponseList.add(searchResponse!!)
            if (mSearchResponseList.size == DataManager().SEARCH_LIMIT) {
                mSearchResponseList.sortByDescending { it.matchDate }
                mRecyclerView.adapter!!.notifyItemInserted(mSearchResponseList.size - 1)
                mRecyclerView.adapter!!.notifyDataSetChanged()
            }
        }
    }

    override fun showError(error: String) {
        if (SearchPresenter().ERROR_EMPTY.equals(error)) mEmptyView.visibility = View.VISIBLE
    }
}