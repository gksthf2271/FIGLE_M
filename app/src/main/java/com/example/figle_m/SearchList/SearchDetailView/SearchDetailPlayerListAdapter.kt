package com.example.figle_m.SearchList.SearchDetailView

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.figle_m.R
import com.example.figle_m.Response.DTO.PlayerDTO

class SearchDetailPlayerListAdapter(context: Context, playerList: List<PlayerDTO>?, val itemClick: (PlayerDTO) -> Unit) :
    RecyclerView.Adapter<SearchDetailPlayerListAdapter.ViewHolder>() {
    private val TAG: String = javaClass.name

    val mContext: Context
    val mPlayerList: List<PlayerDTO>?

    init {
        mContext = context
        mPlayerList = playerList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cview_player_item_view, parent, false)
        viewHolder = ViewHolder(view, itemClick)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mPlayerList.let { mPlayerList!!.size }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.v(TAG, "onBindViewHolder, position : $position")
        holder.bind(mPlayerList!!.get(position), mContext)
    }

    inner class ViewHolder(itemView: View, itemClick: (PlayerDTO) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        var mItemView: View
        var mRootLayout: ConstraintLayout
        var mPlayerImg: ImageView
        var mRating: TextView
        var mPlayerName: TextView
        var mPlayerPosition: TextView

        init {
            mItemView = itemView
            mRootLayout = mItemView.findViewById(R.id.group_player)
            mPlayerImg = mItemView.findViewById(R.id.img_player)
            mRating = mItemView.findViewById(R.id.txt_rating)
            mPlayerName = mItemView.findViewById(R.id.txt_player_name)
            mPlayerPosition = mItemView.findViewById(R.id.txt_player_position)
        }
        val TAG: String = javaClass.name
        fun bind(item: PlayerDTO, context: Context) {
            mPlayerName.text = item.spId.toString()
            mRating.text = item.status.spRating.toString()
            mPlayerPosition.text = item.spPosition.toString()
            itemView.setOnClickListener { itemClick(item) }
        }
    }
}