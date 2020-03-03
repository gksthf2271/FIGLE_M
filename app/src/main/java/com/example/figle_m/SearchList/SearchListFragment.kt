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
import java.util.*
import java.util.concurrent.locks.Lock
import kotlin.collections.ArrayList

class SearchListFragment : BaseFragment(), SearchContract.View {
    val TAG: String = javaClass.name

    open val KEY_SEARCH_STRING: String = "SearchString"
    open val KEY_MATCH_ID_LIST: String = "MatchIdList"

    lateinit var mSearchPresenter: SearchPresenter
    var mDataBinding: FragmentSearchlistBinding? = null
    lateinit var mSearchResponseList: ArrayList<MatchDetailResponse>
    lateinit var mMatchIdList: MutableList<String>
    lateinit var mSearchString: String

    lateinit var mRecyclerView: RecyclerView

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
    }

    override fun onDestroy() {
        super.onDestroy()
        mSearchPresenter!!.dropView()
    }

    override fun onResume() {
        super.onResume()
        arguments.let {
            mSearchResponseList = arrayListOf()
            mMatchIdList = mutableListOf()
            mMatchIdList.addAll((arguments!!.get(KEY_MATCH_ID_LIST) as Array<String>).toList())

            mSearchString = arguments!!.getString(KEY_SEARCH_STRING)!!
        }

        for (item in mMatchIdList) {
            Log.v(TAG, "item, matchId : ${item} ")
            mSearchPresenter.getMatchDetailList(item)
        }

        context ?: return

        mRecyclerView = view!!.findViewById<RecyclerView>(R.id.layout_recyclerview)
        val layoutManager = LinearLayoutManager(context)
        mRecyclerView.addItemDecoration(SearchDecoration(10))
        mRecyclerView.setLayoutManager(layoutManager)
        mRecyclerView.adapter =  SearchListAdapter(context!!, mSearchString ,mSearchResponseList)

        Log.v(TAG,"SearchList total count ::: ${mRecyclerView.adapter!!.itemCount}")
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showSearchList(searchResponse: MatchDetailResponse?) {
        searchResponse ?: return
        Log.v(TAG,"showSearchList : ${searchResponse!!.matchId}")
        synchronized("Lock") {
            mSearchResponseList.add(searchResponse!!)
            mRecyclerView.adapter!!.notifyItemInserted(mSearchResponseList.size - 1)
            mRecyclerView.adapter!!.notifyDataSetChanged()
        }
    }

    override fun showError(error: String) {
    }
}