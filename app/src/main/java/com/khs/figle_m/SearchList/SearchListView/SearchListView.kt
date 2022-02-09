package com.khs.figle_m.SearchList.SearchListView

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khs.figle_m.BuildConfig
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.R
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.Response.UserResponse
import com.khs.figle_m.SearchList.SearchContract
import com.khs.figle_m.SearchList.SearchDecoration
import com.khs.figle_m.SearchList.SearchListAdapter
import kotlinx.android.synthetic.main.cview_search_list.view.*
import kotlin.properties.Delegates

class SearchListView : ConstraintLayout, SearchContract.SearchListView{
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }
    val DEBUG = BuildConfig.DEBUG
    val TAG = javaClass.name
    lateinit var mSearchUserInfo: UserResponse
    var mMatchIdList = arrayListOf<String>()
    var mMatchList = arrayListOf<MatchDetailResponse>()
    var mMatchType by Delegates.notNull<Int>()
    lateinit var mSearchListPresenter: SearchListPresenter

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mSearchListPresenter = SearchListPresenter()
        mSearchListPresenter.takeView(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mSearchListPresenter.dropView()
    }

    fun initView(context: Context) {
        Log.v(TAG, "initView(...)")
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_search_list, this)

        val layoutManager = LinearLayoutManager(context)
        layout_recyclerview.addItemDecoration(SearchDecoration(10))
        layout_recyclerview.setLayoutManager(layoutManager)
    }

    fun setSearchUserInfo(searchUserInfo: UserResponse) {
        Log.v(TAG, "setSearchUserInfo : $searchUserInfo")
        mSearchUserInfo = searchUserInfo
    }

    fun updateView(
        matchType : Int,
        matchIdList: ArrayList<String>,
        itemClick: (MatchDetailResponse) -> Unit
    ) {
        Log.v(TAG, "updateView matchIdList size : ${matchIdList.size}")
        mMatchIdList = matchIdList
        if (matchIdList.isEmpty()) {
            showEmptyView()
            return
        } else {
            hideEmptyView()
        }

        mMatchType = matchType
        layout_recyclerview.adapter =
            SearchListAdapter(context!!, mSearchUserInfo.accessId, mMatchList) { matchDetailResponse ->
                Log.v(TAG, "ItemClick! ${matchDetailResponse.matchInfo}")
                itemClick(matchDetailResponse)
            }
        if (mMatchIdList.size > DataManager().SEARCH_PAGE_SIZE) {
            loadMatch(matchType, 0, DataManager().SEARCH_PAGE_SIZE - 1)
        } else {
            loadMatch(matchType, 0, mMatchIdList.size - 1)
        }

        layout_recyclerview.addOnScrollListener(mRecyclerViewScrollListener)
    }

    fun loadMatch(matchType: Int, startIndex : Int, endIndex: Int) {
        for (index in startIndex .. endIndex) {
            Log.v(TAG,"loadMatch : $index")
            mSearchListPresenter.let {
                mSearchListPresenter.getMatchDetailList(
                matchType == DataManager.matchType.normalMatch.ordinal, mMatchIdList[index]
                )
            }
        }
    }

    private val mRecyclerViewScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val childCount: Int = recyclerView.adapter.let { recyclerView.adapter!!.itemCount }
            if (childCount < 7) return;
            var range = mMatchIdList.size - childCount
            if (!recyclerView.canScrollVertically(1)) {
                if (DEBUG) Log.v(TAG,"TEST, mMatchIdList : ${mMatchIdList.size}, childCount : ${recyclerView.adapter.let{recyclerView.adapter!!.itemCount}}")
                if (DEBUG) Log.v(TAG, "TEST, range : $range")

                if (range > 0) {
                    showLoading()
                    if (DataManager().SEARCH_PAGE_SIZE > range) {
                        if (DEBUG) Log.v(TAG, "TEST1 scrolled(...) childCount : $childCount , range : $range")
                        loadMatch(mMatchType, childCount, childCount + range - 1)
                    } else {
                        if (DEBUG) Log.v(TAG, "TEST2 scrolled(...) childCount : $childCount , range : $range")
                        loadMatch(mMatchType, childCount, childCount + DataManager().SEARCH_PAGE_SIZE - 1)
                    }
                }
            }
        }
    }

    private fun showEmptyView() {
        Log.v(TAG, "showEmptyView(...)")
        layout_recyclerview.visibility = View.GONE
        txt_emptyView.visibility = View.VISIBLE
    }

    private fun hideEmptyView() {
        Log.v(TAG, "hideEmptyView(...)")
        layout_recyclerview.visibility = View.VISIBLE
        txt_emptyView.visibility = View.GONE
    }

    fun getViewChildCount(): Int {
        return layout_recyclerview.childCount
    }

    override fun showLoading() {
        if (!avi_loading.isShownLoadingView()) avi_loading.visibility = View.VISIBLE
        avi_loading.backroundColorVisible(false)
        avi_loading.show(false)
    }

    override fun hideLoading(isError: Boolean) {
        avi_loading.hide()
        if (avi_loading.isShownLoadingView()) avi_loading.visibility = View.GONE
    }

    override fun showGameList(searchResponse: MatchDetailResponse?) {
        searchResponse ?: return
        if (searchResponse.matchInfo.size <= 1) return
        Log.v(TAG, "showOfficialGameList : ${searchResponse!!.matchId}")
        synchronized("Lock") {
            mMatchList.add(searchResponse!!)
            mMatchList.sortByDescending { it.matchDate }
        }
        if (DEBUG) Log.v(TAG, "mMatchList size : ${mMatchList.size} , mMatchIdList size : ${mMatchIdList.size}")
        layout_recyclerview.adapter?.notifyDataSetChanged()
        Handler().postDelayed({
            hideLoading(false)
        }, 500)
    }

    override fun showError(error: Int) {

    }
}
