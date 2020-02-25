package com.example.figle_m.Adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.db.williamchart.view.ImplementsAlphaChart
import com.example.figle_m.R
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.databinding.ItemSearchListBinding

class SearchListAdapter(context: Context, matchList: MutableList<MatchDetailResponse>?) :
    RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {
    private val TAG: String = javaClass.name

    val mContext: Context
    val mMatchList: MutableList<MatchDetailResponse>?
    var mItemSearchListBinding: ItemSearchListBinding? = null
    init {
        mContext = context
        mMatchList = matchList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_list, parent, false)
        val viewHolder = ViewHolder(view, this)
        mItemSearchListBinding = ItemSearchListBinding.bind(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mMatchList.let { mMatchList!!.size }
    }

    @UseExperimental(ImplementsAlphaChart::class)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mMatchList!![position].matchInfo.let {
            var score: String? = mMatchList[position].matchInfo!![0].shoot.goalTotal.toString()
            score += " : " + mMatchList[position].matchInfo!![1].shoot.goalTotal.toString()

            var user: String? = mMatchList[position].matchInfo!![0].nickname
            user += " vs " + mMatchList[position].matchInfo!![1].nickname

            val result = mMatchList[position].matchInfo!![0].matchDetail!!.matchResult

            mItemSearchListBinding!!.txtResult.text = result
            mItemSearchListBinding!!.txtNickName.text = user
            mItemSearchListBinding!!.txtScore.text = score

            val res =
                if ("승".equals(result))
                    mContext.resources.getColor(R.color.search_list_win, null)
                else
                    mContext.resources.getColor(R.color.search_list_lose, null)
            mItemSearchListBinding!!.rootLayout.setBackgroundColor(res)

            val horizontalBarSet = linkedMapOf(
                "PORRO" to 10F,
                "FUSCE" to 5F,
                "EGET" to 3F
            )

            val donutSet = listOf(
                20f,
                80f
            )

            val animationDuration = 1000L

            val HorizontalBarChartView = mItemSearchListBinding!!.chartResult
            val DonutChartView = mItemSearchListBinding!!.chartPossession

            HorizontalBarChartView.animation.duration = animationDuration
            HorizontalBarChartView.animate(horizontalBarSet)


            DonutChartView.donutColors = intArrayOf(
                R.color.search_list_win,
                R.color.search_list_lose
            )
            DonutChartView.animation.duration = animationDuration
            DonutChartView.animate(donutSet)
        }
//        mDataBinding.txtNickName = mMatchList[position].matchInfoList[0].nickname
//        holder.mItemView = mMatchList[position].matchInfoList[0].nickname
    }

    open class ViewHolder(itemView: View, searchListAdapter: SearchListAdapter) :
        RecyclerView.ViewHolder(itemView) {
        var mItemView: View
        var mSearchListAdapter: SearchListAdapter

        init {
            mItemView = itemView
            mSearchListAdapter = searchListAdapter
        }
    }
}