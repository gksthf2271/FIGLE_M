package com.example.figle_m.Adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.figle_m.R
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.databinding.ItemSearchListBinding

class SearchListAdapter(context: Context, matchList:MutableList<MatchDetailResponse>) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {
    private val TAG:String = javaClass.name

    val mContext: Context
    val mMatchList:MutableList<MatchDetailResponse>
    var mDataBinding: ItemSearchListBinding? = null

    init {
        mContext = context
        mMatchList = matchList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_search_list, parent, false)
        val viewHolder = ViewHolder(view, this)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mMatchList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mDataBinding = DataBindingUtil.getBinding<ItemSearchListBinding>(holder.mItemView)
        mMatchList[position].matchInfoList.let { return }
        var user: String? = mMatchList[position].matchInfoList!![0].nickname
        user += " vs " + mMatchList[position].matchInfoList!![1].nickname

        mDataBinding!!.txtResult.text = mMatchList[position].matchInfoList!![0].matchDetail!!.matchResult
        mDataBinding!!.txtNickName.text = user
//        mDataBinding.txtNickName = mMatchList[position].matchInfoList[0].nickname
//        holder.mItemView = mMatchList[position].matchInfoList[0].nickname
    }

    open class ViewHolder(itemView: View, searchListAdapter: SearchListAdapter): RecyclerView.ViewHolder(itemView) {
        var mItemView: View
        var mSearchListAdapter:SearchListAdapter

        init {
            mItemView = itemView
            mSearchListAdapter = searchListAdapter
        }
    }
}