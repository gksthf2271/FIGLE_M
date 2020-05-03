package com.khs.figle_m.Ranking

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.khs.figle_m.R

class RankingRecyclerViewAdapter(context: Context, rankerList:List<Ranker>, val itemClick: (Ranker) -> Unit) :
    RecyclerView.Adapter<RankingRecyclerViewAdapter.ViewHolder>() {
    private val TAG: String = javaClass.name
    val DEBUG = false
    val mContext: Context
    val mRankerList: List<Ranker>?

    init {
        mContext = context
        mRankerList = rankerList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_list, parent, false)
        viewHolder = ViewHolder(view, itemClick)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mRankerList.let { mRankerList!!.size }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(DEBUG) Log.v(TAG, "onBindViewHolder, position : $position")
        holder.bind(mRankerList!!.get(position), mContext)
    }

    inner class ViewHolder(itemView: View, itemClick: (Ranker) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val TAG: String = javaClass.name
        var mRootLayout: ConstraintLayout
        var mItemView: View
        var mTxtLeftNickName: TextView
        var mTxtRightNickName: TextView
        var mTxtLeftScore: TextView
        var mTxtRightScore: TextView
        var mTxtLeftResult: TextView
        var mTxtRightResult: TextView
        var mTxtDate: TextView

        init {
            mItemView = itemView
            mRootLayout = mItemView.findViewById(R.id.root_layout)
            mTxtRightNickName = mItemView.findViewById(R.id.txt_right_nickName)
            mTxtLeftNickName = mItemView.findViewById(R.id.txt_left_nickName)
            mTxtLeftScore = mItemView.findViewById(R.id.txt_left_score)
            mTxtRightScore = mItemView.findViewById(R.id.txt_right_score)
            mTxtLeftResult = mItemView.findViewById(R.id.txt_left_result)
            mTxtRightResult = mItemView.findViewById(R.id.txt_right_result)
            mTxtDate = mItemView.findViewById(R.id.txt_date)
        }


        fun bind(item: Ranker, context: Context) {
        }
    }
}