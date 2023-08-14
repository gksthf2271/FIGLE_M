package com.khs.figle_m.Ranking

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.khs.figle_m.Base.BaseFragment
import com.khs.figle_m.BuildConfig
import com.khs.figle_m.Home.HomeFragment
import com.khs.figle_m.R
import com.khs.figle_m.SearchList.SearchDecoration
import com.khs.figle_m.Utils.DisplayUtils
import com.khs.figle_m.Utils.LogUtil
import com.khs.figle_m.databinding.FragmentRankingBinding

class RankingFragment : BaseFragment(){
    val TAG:String = javaClass.simpleName
    val KEY_RANKING_LIST = "KEY_RANKING_LIST"
    val DEBUG = BuildConfig.DEBUG
    lateinit var mBinding : FragmentRankingBinding
    lateinit var mCurrentRank: Ranker

    override fun initPresenter() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"initPresenter(...)")
    }

    companion object {
        @Volatile
        private var instance: RankingFragment? = null

        @JvmStatic
        fun getInstance(): RankingFragment =
            instance ?: synchronized(this) {
                instance
                    ?: RankingFragment().also {
                        instance = it
                    }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentRankingBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    override fun onStart() {
        super.onStart()
        initList()
    }

    private fun initList() {
        var rankingList = arrayListOf<Ranker>()
        arguments?.let {
            rankingList = it.getParcelableArrayList(KEY_RANKING_LIST)!!
        }
        val layoutManager = LinearLayoutManager(context)
        mBinding.layoutRecyclerview.apply {
            addItemDecoration(SearchDecoration(10))
            setLayoutManager(layoutManager)
            adapter = RankingRecyclerViewAdapter(requireContext(), rankingList) { ranker ->
                updateTopView(ranker)
            }
            updateTopView((adapter as RankingRecyclerViewAdapter).getFirstRanker())
        }
        mBinding.imgSearch.setOnClickListener {
            LogUtil.dLog(LogUtil.TAG_UI, TAG,"TEST, onClick!!")
            var intent = Intent()
            intent.putExtra(HomeFragment().KEY_SEARCH, mCurrentRank.name)
            intent.putExtra(HomeFragment().KEY_SEARCH_TEAM_PRICE, mCurrentRank.price)
            LogUtil.dLog(LogUtil.TAG_UI, TAG,"TEST, 0!!")
            if (activity is RankingActivity) {
                LogUtil.dLog(LogUtil.TAG_UI, TAG,"TEST, 1!!")
                activity?.setResult(Activity.RESULT_OK, intent)
                activity?.finish()
            }
        }
        mBinding.btnClose.setOnClickListener{
            activity?.finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateTopView(rank:Ranker?) {
        rank ?: return
        mCurrentRank = rank
        mBinding.txtRanking.text = DisplayUtils.updateTextSize(mCurrentRank.rank_no + " 위", " 위")
        mBinding.txtRate.text = "상위 ${mCurrentRank.rank_percent} %"
        mBinding.txtNo1Id.text = rank.name
        mBinding.txtNo1TotalPrice.text = mCurrentRank.price
        context?.let {
            Glide.with(it)
                .load(Uri.parse(mCurrentRank.rank_icon_url))
                .placeholder(R.drawable.person_icon)
                .error(R.drawable.person_icon)
                .skipMemoryCache(false)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        LogUtil.dLog(LogUtil.TAG_UI, TAG,"TEST, onResourceReady(...) url : ${rank.rank_icon_url}")
                        return false
                    }
                })
                .into(mBinding.imgNo1Logo)
        }
    }
}