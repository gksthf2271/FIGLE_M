package com.khs.figle_m.PlayerDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khs.figle_m.Base.BaseFragment
import com.khs.figle_m.R

class PlayerDetailFragment : BaseFragment() {
    val TAG: String = javaClass.name
    val DEBUG: Boolean = false

    override fun initPresenter() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_player_detail, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    fun initView() {

    }
}