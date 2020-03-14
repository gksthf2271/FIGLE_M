package com.example.figle_m.SearchList.SearchDetailView

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.example.figle_m.BaseFragment
import com.example.figle_m.R
import com.example.figle_m.databinding.FragmentSearchContainerBinding

class SearchDetailDialogPageFragment : BaseFragment() {
    var mDataBinding: FragmentSearchContainerBinding? = null
    lateinit var mFM: FragmentManager

    override fun initPresenter() {
    }

    companion object {
        @Volatile
        private var instance: SearchDetailDialogPageFragment? = null

        @JvmStatic
        fun getInstance(): SearchDetailDialogPageFragment =
            instance ?: synchronized(this) {
                instance
                    ?: SearchDetailDialogPageFragment().also {
                        instance = it
                    }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.fragment_search_detail, null)
        mDataBinding = DataBindingUtil.findBinding(v)
        return v
    }
}