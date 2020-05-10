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
import kotlinx.android.synthetic.main.fragment_searchlist.*


class SearchListFragment : BaseFragment() {
    val TAG: String = javaClass.name
    val DEBUG: Boolean = true

    open val KEY_SEARCH_USER_INFO: String = "KEY_SEARCH_USER_INFO"
    open val KEY_SEARCH_MATCH_INFO: String = "KEY_SEARCH_MATCH_INFO"
    open val KEY_SEARCH_MATCH_TYPE: String = "KEY_SEARCH_MATCH_TYPE"

    lateinit var mSearchList: ArrayList<MatchDetailResponse>
    var mMatchtype: Int = 0

    lateinit var mMatchIdList: List<String>
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
            Log.v(TAG, "btn_back Clicked")
            fragmentManager.let {
                fragmentManager!!.popBackStack()
            }
        }
    }

    fun initMyInfoData() {
        arguments.let {
            mSearchUserInfo = arguments!!.getParcelable<UserResponse>(KEY_SEARCH_USER_INFO)!!
            mSearchList =
                arguments!!.getParcelableArrayList<MatchDetailResponse>(KEY_SEARCH_MATCH_INFO)!!
            mMatchtype = arguments!!.getInt(KEY_SEARCH_MATCH_TYPE)!!
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

    fun initListData() {
        view_searchList.setSearchUserInfo(mSearchUserInfo)
        view_searchList.updateView(mSearchList, {
            showDetail(mSearchUserInfo.accessId, it)
        })
    }

    fun showDetail(accessId: String, matchDetailResponse: MatchDetailResponse) {
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