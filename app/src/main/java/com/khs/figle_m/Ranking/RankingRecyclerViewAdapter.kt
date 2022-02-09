package com.khs.figle_m.Ranking

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.khs.figle_m.BuildConfig
import com.khs.figle_m.R
import com.khs.figle_m.Utils.DisplayUtils

class RankingRecyclerViewAdapter(private val mContext: Context, rankerList:List<Ranker>, val itemClick: (Ranker) -> Unit) :
    RecyclerView.Adapter<RankingRecyclerViewAdapter.ViewHolder>() {
    private val TAG: String = javaClass.name
    val DEBUG = BuildConfig.DEBUG
    val mRankerList: List<Ranker>?

    init {
        mRankerList = rankerList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ranking_list, parent, false)
        viewHolder = ViewHolder(view, itemClick)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mRankerList.let { mRankerList!!.size }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(DEBUG) Log.v(TAG, "onBindViewHolder, position : $position")
        holder.bind(mRankerList!!.get(position))
    }

    fun getFirstRanker(): Ranker? {
        if (mRankerList == null || mRankerList.isEmpty()) {
            Log.v(TAG,"mRankerList is null")
            return null
        }
        return mRankerList!!.get(0)
    }

    inner class ViewHolder(itemView: View, itemClick: (Ranker) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val TAG: String = javaClass.name
        var mItemView: View
        var mRootLayout: ConstraintLayout

        var mTxtRanking: TextView
        var mTxtRankingPoint: TextView
        var mTxtId: TextView
        var mTxtTotalPrice: TextView

        init {
            mItemView = itemView
            mRootLayout = mItemView.findViewById(R.id.view_no1)
            mTxtRanking = mItemView.findViewById(R.id.txt_ranking)
            mTxtRankingPoint = mItemView.findViewById(R.id.txt_rank_point)
            mTxtId = mItemView.findViewById(R.id.txt_no1_id)
            mTxtTotalPrice = mItemView.findViewById(R.id.txt_no1_total_price)
        }

        fun bind(item: Ranker) {
            mTxtRanking.text = DisplayUtils().updateTextSize(item.rank_no + " 위", " 위")
            mTxtRankingPoint.text = item.rank_point + " 점"
            mTxtId.text = item.name
            mTxtTotalPrice.text = item.price
            mRootLayout.setOnClickListener() {
                itemClick(item)
            }
        }
    }
}