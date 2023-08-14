package com.khs.figle_m.SearchList.SearchListView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khs.data.nexon_api.response.MatchDetailResponse
import com.khs.data.nexon_api.response.UserResponse
import com.khs.figle_m.BuildConfig
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.SearchList.SearchContract
import com.khs.figle_m.SearchList.SearchDecoration
import com.khs.figle_m.SearchList.SearchListAdapter
import com.khs.figle_m.Utils.LogUtil
import com.khs.figle_m.databinding.CviewSearchListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class SearchListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), SearchContract.SearchListView{
    val TAG = javaClass.simpleName
    lateinit var mBinding: CviewSearchListBinding
    val DEBUG = BuildConfig.DEBUG
    lateinit var mSearchUserInfo: UserResponse
    var mMatchIdList = arrayListOf<String>()
    var mMatchType by Delegates.notNull<Int>()
    lateinit var mSearchListPresenter: SearchListPresenter

    init {
        initView(context)
    }

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
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"initView(...)")
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = CviewSearchListBinding.inflate(inflater, this, true)

        val layoutManager = LinearLayoutManager(context)
        mBinding.layoutRecyclerview.addItemDecoration(SearchDecoration(10))
        mBinding.layoutRecyclerview.layoutManager = layoutManager
    }

    fun setSearchUserInfo(searchUserInfo: UserResponse) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"setSearchUserInfo : $searchUserInfo")
        mSearchUserInfo = searchUserInfo
    }

    fun updateView(
        matchType : Int,
        matchIdList: ArrayList<String>,
        itemClick: (MatchDetailResponse) -> Unit
    ) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"updateView matchIdList size : ${matchIdList.size}")
        mMatchIdList = matchIdList
        if (matchIdList.isEmpty()) {
            showEmptyView()
            return
        } else {
            hideEmptyView()
        }

        mMatchType = matchType
        mBinding.layoutRecyclerview.adapter = SearchListAdapter(context, mSearchUserInfo.accessId, arrayListOf()) { matchDetailResponse ->
                LogUtil.vLog(LogUtil.TAG_UI, TAG,"ItemClick! ${matchDetailResponse.matchInfo}")
                itemClick(matchDetailResponse)
            }
        if (mMatchIdList.size > DataManager.SEARCH_PAGE_SIZE) {
            loadMatch(matchType, 0, DataManager.SEARCH_PAGE_SIZE - 1)
        } else {
            loadMatch(matchType, 0, mMatchIdList.size - 1)
        }

        mBinding.layoutRecyclerview.addOnScrollListener(mRecyclerViewScrollListener)
    }

    fun loadMatch(matchType: Int, startIndex : Int, endIndex: Int) {
        for (index in startIndex .. endIndex) {
            LogUtil.vLog(LogUtil.TAG_UI, TAG,"loadMatch : $index")
            if (this::mSearchListPresenter.isInitialized) {
                mSearchListPresenter.getMatchDetailList(matchType == DataManager.matchType.normalMatch.ordinal, mMatchIdList[index])
            }
        }
    }

    private val mRecyclerViewScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val childCount: Int = recyclerView.adapter.let { recyclerView.adapter!!.itemCount }
            if (childCount < 7) return;
            val range = mMatchIdList.size - childCount
            if (!recyclerView.canScrollVertically(1)) {
                LogUtil.dLog(LogUtil.TAG_UI, TAG,"TEST, mMatchIdList : ${mMatchIdList.size}, childCount : ${recyclerView.adapter.let{recyclerView.adapter!!.itemCount}}")
                LogUtil.dLog(LogUtil.TAG_UI, TAG,"TEST, range : $range")

                if (range > 0) {
                    showLoading()
                    if (DataManager.SEARCH_PAGE_SIZE > range) {
                        LogUtil.dLog(LogUtil.TAG_UI, TAG,"TEST1 scrolled(...) childCount : $childCount , range : $range")
                        loadMatch(mMatchType, childCount, childCount + range - 1)
                    } else {
                        LogUtil.dLog(LogUtil.TAG_UI, TAG,"TEST2 scrolled(...) childCount : $childCount , range : $range")
                        loadMatch(mMatchType, childCount, childCount + DataManager.SEARCH_PAGE_SIZE - 1)
                    }
                }
            }
        }
    }

    private fun showEmptyView() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showEmptyView(...)")
        mBinding.layoutRecyclerview.visibility = View.GONE
        mBinding.txtEmptyView.visibility = View.VISIBLE
    }

    private fun hideEmptyView() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"hideEmptyView(...)")
        mBinding.layoutRecyclerview.visibility = View.VISIBLE
        mBinding.txtEmptyView.visibility = View.GONE
    }

    fun getViewChildCount(): Int {
        return mBinding.layoutRecyclerview.childCount
    }

    override fun showLoading() {
        if (!mBinding.aviLoading.isShownLoadingView()) mBinding.aviLoading.visibility = View.VISIBLE
        mBinding.aviLoading.backgroundColorVisible(false)
        mBinding.aviLoading.show(false)
    }

    override fun hideLoading(isError: Boolean) {
        mBinding.aviLoading.hide()
        if (mBinding.aviLoading.isShownLoadingView()) mBinding.aviLoading.visibility = View.GONE
    }

    override fun showGameList(searchResponse: MatchDetailResponse?) {
        searchResponse?.let { matchDetailResponse ->
            if (matchDetailResponse.matchInfo.size <= 1) return
            LogUtil.vLog(LogUtil.TAG_UI, TAG,"showOfficialGameList : ${matchDetailResponse.matchId}")
            (mBinding.layoutRecyclerview.adapter as SearchListAdapter).updateList(matchDetailResponse)
        }
        CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            hideLoading(false)
        }
    }

    override fun showError(error: Int) {

    }
}
