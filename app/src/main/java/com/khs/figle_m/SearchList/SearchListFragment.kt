package com.khs.figle_m.SearchList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khs.figle_m.Base.BaseFragment
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.R
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.Response.UserResponse
import com.khs.figle_m.SearchDetail.SearchDetailDialogFragment
import com.khs.figle_m.Utils.LogUtil
import kotlinx.android.synthetic.main.fragment_searchlist.*


class SearchListFragment : BaseFragment() {
    val TAG: String = javaClass.simpleName
    val DEBUG: Boolean = true

    open val KEY_SEARCH_USER_INFO: String = "KEY_SEARCH_USER_INFO"
    open val KEY_SEARCH_MATCH_INFO: String = "KEY_SEARCH_MATCH_INFO"
    open val KEY_SEARCH_MATCH_TYPE: String = "KEY_SEARCH_MATCH_TYPE"
    open val KEY_SEARCH_MATCH_ID: String = "KEY_SEARCH_MATCH_ID"

    lateinit var mSearchList: ArrayList<MatchDetailResponse>
    var mMatchtype: Int = 0

    var mMatchIdList: ArrayList<String> = arrayListOf()
    lateinit var mSearchUserInfo: UserResponse

    lateinit var mSelectedMatchInfo: MatchDetailResponse
    var mIsCoachMode : Boolean = false
    override fun initPresenter() {
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
        initView()
        initMyInfoData()
        initListData()
    }

    fun initView() {
        btn_back.visibility = View.VISIBLE
        btn_back.setOnClickListener {
            fragmentManager.let {
                fragmentManager!!.popBackStack()
            }
        }
    }

    fun initMyInfoData() {
        arguments?.let { bundle ->
            mSearchUserInfo = bundle.getParcelable<UserResponse>(KEY_SEARCH_USER_INFO)!!
//            mSearchList = bundle.getParcelableArrayList<MatchDetailResponse>(KEY_SEARCH_MATCH_INFO)!!
            mMatchtype = bundle.getInt(KEY_SEARCH_MATCH_TYPE)
            mMatchIdList = bundle.getStringArrayList(KEY_SEARCH_MATCH_ID) as ArrayList<String>
        }

        when (mMatchtype) {
            DataManager.matchType.normalMatch.ordinal -> {
                txt_title.text = "1 ON 1 경기 조회"
                txt_title.visibility = View.VISIBLE
                mIsCoachMode = false
            }
            DataManager.matchType.coachMatch.ordinal -> {
                txt_title.text = "감독모드 경기 조회"
                txt_title.visibility = View.VISIBLE
                mIsCoachMode = true
            }
        }
    }

    private fun initListData() {
        view_searchList.setSearchUserInfo(mSearchUserInfo)
        view_searchList.updateView(mMatchtype, mMatchIdList) { matchDetailResponse ->
            showDetail(mSearchUserInfo.accessId, matchDetailResponse)
        }
    }

    private fun showDetail(accessId: String, matchDetailResponse: MatchDetailResponse) {
        val searchDetailDialogFragment = SearchDetailDialogFragment.getInstance()
        val bundle = Bundle()
        mSelectedMatchInfo = matchDetailResponse
        var isCoachMode = false

        bundle.putBoolean(searchDetailDialogFragment.KEY_IS_COACH_MODE, mIsCoachMode)
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
}