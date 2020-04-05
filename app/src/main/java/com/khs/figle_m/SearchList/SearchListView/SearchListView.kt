package com.khs.figle_m.SearchList.SearchListView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khs.figle_m.R
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.Response.UserResponse
import com.khs.figle_m.SearchList.SearchDecoration
import com.khs.figle_m.SearchList.SearchListAdapter
import kotlinx.android.synthetic.main.cview_search_list.view.*

class SearchListView : ConstraintLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    val TAG = javaClass.name
    lateinit var mRecyclerView: RecyclerView
    lateinit var mSearchUserInfo : UserResponse

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_search_list, this)
        mRecyclerView = findViewById(R.id.layout_recyclerview)
        val layoutManager = LinearLayoutManager(context)
        mRecyclerView.addItemDecoration(SearchDecoration(10))
        mRecyclerView.setLayoutManager(layoutManager)
    }

    fun setSearchUserInfo(searchUserInfo: UserResponse) {
        mSearchUserInfo = searchUserInfo
    }

    fun updateView(matchDetailList: List<MatchDetailResponse>, itemClick: (MatchDetailResponse) -> Unit) {
        Log.v(TAG,"updateView : ${matchDetailList}")
        if (matchDetailList.isEmpty()) {
            showEmptyView()
            return
        } else {
            hideEmptyView()
        }
        mRecyclerView.adapter =
            SearchListAdapter(context!!, mSearchUserInfo.accessId, matchDetailList.toMutableList(), {
                Log.v(TAG,"ItemClick! ${it.matchInfo}")
                itemClick(it)
            })
    }

    fun showEmptyView() {
        Log.v(TAG,"showEmptyView(...)")
        mRecyclerView.visibility = View.GONE
        txt_emptyView.visibility = View.VISIBLE
    }

    fun hideEmptyView() {
        Log.v(TAG,"hideEmptyView(...)")
        mRecyclerView.visibility = View.VISIBLE
        txt_emptyView.visibility = View.GONE
    }
}
