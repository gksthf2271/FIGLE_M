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
        val v: View = inflater.inflate(R.layout.fragment_analytics, container, false)
        return v
    }


    override fun onStart() {
        super.onStart()
        initList()
    }

    fun initList() {
        var myList = arrayListOf<MatchInfoDTO>()
        var opposingUserList = arrayListOf<MatchInfoDTO>()
        arguments.let {
            myList = arguments!!.getParcelableArrayList(AnalyticsActivity().KEY_MY_DATA)!!
            opposingUserList = arguments!!.getParcelableArrayList(AnalyticsActivity().KEY_OPPOSING_USER_DATA)!!
        }
    }

    fun updateTopView(rank: Ranker?) {

    }
}