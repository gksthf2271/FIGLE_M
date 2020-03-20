package com.example.figle_m.SearchList.SearchDetailView

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.figle_m.Data.DataManager
import com.example.figle_m.R
import com.example.figle_m.Response.DTO.PlayerDTO
import com.example.figle_m.utils.PositionEnum
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl

class SearchDetailPlayerListAdapter(context: Context, playerList: List<PlayerDTO>?, val itemClick: (PlayerDTO) -> Unit) :
    RecyclerView.Adapter<SearchDetailPlayerListAdapter.ViewHolder>() {
    private val TAG: String = javaClass.name
    val isDebug = false
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
        if(isDebug) Log.v(TAG, "onBindViewHolder, position : $position")
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
            itemView.setOnClickListener { itemClick(item) }
            mPlayerName.text = item.spId.toString()
            mRating.text = item.status.spRating.toString()
            for (positionItem in PositionEnum.values()) {
                if (positionItem.spposition.equals(item.spPosition))
                    mPlayerPosition.text = positionItem.description
            }
            runBlocking {
                launch {
                    DataManager.getInstance().loadPlayerImage(item.spId, {
                        updatePlayerImage(it)

                    }, {
                        Log.v(TAG,"load Failed : $it")
                        mPlayerImg.setImageResource(R.drawable.ic_launcher_foreground)
                    })
                }
            }
        }
        fun updatePlayerImage(url: HttpUrl) {
            Log.v(TAG,"updatePlayerImage(...) uri : ${url}")
            Glide.with(mPlayerImg.getContext())
                .load(Uri.parse(url.toString()))
                .placeholder(R.drawable.person_icon)
                .error(R.drawable.person_icon)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d(TAG, "onLoadFailed(...) GlideException!!! " + e!!)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d(TAG, "onResourceReady(...) $url")
                        return false
                    }
                })
                .into(mPlayerImg)
        }
    }
}