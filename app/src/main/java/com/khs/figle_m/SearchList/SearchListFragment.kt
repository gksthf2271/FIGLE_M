package com.khs.figle_m.SearchList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khs.data.nexon_api.response.MatchDetailResponse
import com.khs.figle_m.Base.BaseFragment
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Response.UserResponse
import com.khs.figle_m.SearchDetail.SearchDetailDialogFragment
import com.khs.figle_m.databinding.FragmentSearchlistBinding


class SearchListFragment : BaseFragment() {
    val TAG: String = javaClass.simpleName
    lateinit var mBinding : FragmentSearchlistBinding
    val DEBUG: Boolean = true

    val KEY_SEARCH_USER_INFO: String = "KEY_SEARCH_USER_INFO"
    val KEY_SEARCH_MATCH_INFO: String = "KEY_SEARCH_MATCH_INFO"
    val KEY_SEARCH_MATCH_TYPE: String = "KEY_SEARCH_MATCH_TYPE"
    val KEY_SEARCH_MATCH_ID: String = "KEY_SEARCH_MATCH_ID"

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
        mBinding = FragmentSearchlistBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initView()
        initMyInfoData()
        initListData()
    }

    fun initView() {
        mBinding.btnBack.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun initMyInfoData() {
        arguments?.let { bundle ->
            mSearchUserInfo = bundle.getParcelable<UserResponse>(KEY_SEARCH_USER_INFO)!!
//            mSearchList = bundle.getParcelableArrayList<MatchDetailResponse>(KEY_SEARCH_MATCH_INFO)!!
            mMatchtype = bundle.getInt(KEY_SEARCH_MATCH_TYPE)
            mMatchIdList = bundle.getStringArrayList(KEY_SEARCH_MATCH_ID) as ArrayList<String>
        }

        mBinding.txtTitle.apply {
            when (mMatchtype) {
                DataManager.matchType.normalMatch.ordinal -> {
                    text = "1 ON 1 경기 조회"
                    visibility = View.VISIBLE
                    mIsCoachMode = false
                }
                DataManager.matchType.coachMatch.ordinal -> {
                    text = "감독모드 경기 조회"
                    visibility = View.VISIBLE
                    mIsCoachMode = true
                }
            }
        }
    }

    private fun initListData() {
        mBinding.viewSearchList.apply {
            setSearchUserInfo(mSearchUserInfo)
            updateView(mMatchtype, mMatchIdList) { matchDetailResponse ->
                showDetail(mSearchUserInfo.accessId, matchDetailResponse)
            }
        }
    }

    private fun showDetail(accessId: String, matchDetailResponse: MatchDetailResponse) {
        val searchDetailDialogFragment = SearchDetailDialogFragment.getInstance()
        val bundle = Bundle()
        mSelectedMatchInfo = matchDetailResponse

        bundle.putBoolean(searchDetailDialogFragment.KEY_IS_COACH_MODE, mIsCoachMode)
        bundle.putSerializable(searchDetailDialogFragment.KEY_MATCH_DETAIL_INFO, matchDetailResponse)
        bundle.putString(searchDetailDialogFragment.KEY_SEARCH_ACCESSID, accessId)
        searchDetailDialogFragment.arguments = bundle
        if (!searchDetailDialogFragment.isAdded) {
            searchDetailDialogFragment.show(
                requireActivity().supportFragmentManager,
                SearchDetailDialogFragment().TAG_MATCH_DETAIL_DIALOG
            )
        }
    }
}