package com.example.figle_m

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.figle_m.View.UserPresenter

class SearchListFragment: BaseFragment() {
    val TAG: String = javaClass.name

    lateinit var mUserPresenter: UserPresenter

    override fun initPresenter() {
        mUserPresenter = UserPresenter()
    }

    companion object {
        @Volatile
        private var instance: SearchListFragment? = null

        @JvmStatic
        fun getInstance(): SearchListFragment =
            instance ?: synchronized(this) {
                instance ?: SearchListFragment().also {
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
    }

    override fun onResume() {
        super.onResume()
    }

}