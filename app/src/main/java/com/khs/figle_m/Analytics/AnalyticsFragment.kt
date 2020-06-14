package com.khs.figle_m.Analytics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khs.figle_m.Base.BaseFragment
import com.khs.figle_m.R
import com.khs.figle_m.Ranking.Ranker
import com.khs.figle_m.Response.DTO.MatchInfoDTO
import com.khs.figle_m.SearchList.Common.CustomPagerAdapter
import kotlinx.android.synthetic.main.fragment_analytics.*
import kotlinx.android.synthetic.main.fragment_searchlist_ver2.*

class AnalyticsFragment : BaseFragment(){
    val TAG:String = javaClass.name
    val DEBUG = false

    lateinit var mCurrentRank: Ranker

    override fun initPresenter() {
        Log.v(TAG,"initPresenter(...)")
    }

    companion object {
        @Volatile
        private var instance: AnalyticsFragment? = null

        @JvmStatic
        fun getInstance(): AnalyticsFragment =
            instance ?: synchronized(this) {
                instance
                    ?: AnalyticsFragment().also {
                        instance = it
                    }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_analytics, container, false)
    }

    override fun onStart() {
        super.onStart()
        initList()
    }

    fun initViewPager(firstView: View, lastView: View) {
        view_pager.adapter =
            CustomPagerAdapter(
                context!!,
                firstView,
                lastView
            )
        view_pager.currentItem = 0

        indicator.setViewPager(view_pager)
    }

    fun initList() {
        var myList = arrayListOf<MatchInfoDTO>()
        var opposingUserList = arrayListOf<MatchInfoDTO>()
        arguments.let {
            myList = arguments!!.getParcelableArrayList(AnalyticsActivity().KEY_MY_DATA)!!
            opposingUserList = arguments!!.getParcelableArrayList(AnalyticsActivity().KEY_OPPOSING_USER_DATA)!!
        }
        var fieldView = FiledView(context!!)
        fieldView.setMatchList(myList)

        initViewPager(fieldView, fieldView)

    }
}