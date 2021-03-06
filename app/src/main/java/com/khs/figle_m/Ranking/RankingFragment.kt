package com.khs.figle_m.Ranking

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.fragment_ranking.*

class RankingFragment : BaseFragment(){
    val TAG:String = javaClass.name
    open val KEY_RANKING_LIST = "KEY_RANKING_LIST"
    val DEBUG = BuildConfig.DEBUG

    lateinit var mCurrentRank: Ranker

    override fun initPresenter() {
        Log.v(TAG,"initPresenter(...)")
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
        val v: View = inflater.inflate(R.layout.fragment_ranking, container, false)
        return v
    }


    override fun onStart() {
        super.onStart()
        initList()
    }

    fun initList() {
        var rankingList = arrayListOf<Ranker>()
        arguments.let {
            rankingList = arguments!!.getParcelableArrayList(KEY_RANKING_LIST)!!
        }
        val layoutManager = LinearLayoutManager(context)
        layout_recyclerview.addItemDecoration(SearchDecoration(10))
        layout_recyclerview.setLayoutManager(layoutManager)
        layout_recyclerview.adapter = RankingRecyclerViewAdapter(context!!, rankingList, {
            updateTopView(it)
        })
        updateTopView((layout_recyclerview.adapter as RankingRecyclerViewAdapter).getFirstRanker())
        img_search.setOnClickListener {
            if(DEBUG) Log.v(TAG,"TEST, onClick!!")
            var intent = Intent()
            intent.putExtra(HomeFragment().KEY_SEARCH, mCurrentRank.name)
            intent.putExtra(HomeFragment().KEY_SEARCH_TEAM_PRICE, mCurrentRank.price)
            if(DEBUG) Log.v(TAG,"TEST, 0!!")
            if (activity != null && activity is RankingActivity) {
                if(DEBUG) Log.v(TAG,"TEST, 1!!")
                activity!!.setResult(Activity.RESULT_OK, intent)
                activity!!.finish()
            }
        }
        btn_close.setOnClickListener{
            activity!!.finish()
        }
    }

    fun updateTopView(rank:Ranker?) {
        context ?: return
        rank ?: return
        mCurrentRank = rank
        txt_ranking.text = DisplayUtils().updateTextSize(mCurrentRank.rank_no + " 위", " 위")
        txt_rate.text = "상위 " + mCurrentRank.rank_percent + "%"
        txt_no1_id.text = rank.name
        txt_no1_total_price.text = mCurrentRank.price

        Glide.with(context!!)
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
                    if(DEBUG) Log.d(TAG, "TEST, onResourceReady(...) url : ${rank.rank_icon_url}")
                    return false
                }
            })
            .into(img_no1_logo)
    }
}