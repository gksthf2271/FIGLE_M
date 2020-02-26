package com.example.figle_m.SearchList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.figle_m.BaseFragment
import com.example.figle_m.HomeFragment
import com.example.figle_m.R
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.View.UserPresenter
import com.example.figle_m.databinding.FragmentSearchlistBinding

class SearchListFragment : BaseFragment() {
    val TAG: String = javaClass.name

    lateinit var mUserPresenter: UserPresenter
    var mDataBinding: FragmentSearchlistBinding? = null
    lateinit var mSearchResponseList: ArrayList<MatchDetailResponse>
    lateinit var mSearchString: String

    open val KEY_MATCH_DETAIL_LIST: String = "KEY_MATCH_DETAIL_LIST"
    override fun initPresenter() {
        mUserPresenter = UserPresenter()
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

    override fun onResume() {
        super.onResume()
        arguments.let {
            mSearchResponseList = arrayListOf()
            mSearchResponseList.addAll(arguments!!.get(KEY_MATCH_DETAIL_LIST) as ArrayList<MatchDetailResponse>)

            mSearchString = arguments!!.getString(HomeFragment.getInstance().KEY_SEARCH_STRING)!!
        }
        for (response in mSearchResponseList) {
            Log.v(TAG, "item ::: ${response}")
        }

        context ?: return

        val recyclerView = view!!.findViewById<RecyclerView>(R.id.layout_recyclerview)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(SearchDecoration(10))
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.adapter =  SearchListAdapter(context!!, mSearchString ,mSearchResponseList)

        Log.v(TAG,"TEST, adpater total count ::: ${recyclerView.adapter!!.itemCount}")
    }

}