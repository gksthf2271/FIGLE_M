package com.khs.figle_m.SearchList.SearchDetailView

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
import com.khs.figle_m.DB.PlayerDataBase
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.R
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.utils.PositionEnum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl

class SearchDetailPlayerListAdapter(context: Context, playerList: List<PlayerDTO>?, val itemClick: (PlayerDTO) -> Unit) :
    RecyclerView.Adapter<SearchDetailPlayerListAdapter.ViewHolder>() {
    private val TAG: String = javaClass.name
    val isDebug = false
    val mContext: Context
    val mPlayerList: List<PlayerDTO>?
    var mMvpPlayer: PlayerDTO? = null

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

    fun updateMvpPlayer(mvpPlayer: PlayerDTO?) {
        mMvpPlayer = mvpPlayer
    }

    inner class ViewHolder(itemView: View, itemClick: (PlayerDTO) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        var mItemView: View
        var mRootLayout: ConstraintLayout
        var mPlayerImg: ImageView
        var mIcon: ImageView
        var mRating: TextView
        var mPlayerName: TextView
        var mPlayerPosition: TextView
        var mPlayerSpGrade: TextView

        init {
            mItemView = itemView
            mRootLayout = mItemView.findViewById(R.id.group_player)
            mIcon = mItemView.findViewById(R.id.img_icon)
            mPlayerImg = mItemView.findViewById(R.id.img_player)
            mRating = mItemView.findViewById(R.id.txt_rating)
            mPlayerName = mItemView.findViewById(R.id.txt_player_name)
            mPlayerPosition = mItemView.findViewById(R.id.txt_player_position)
            mPlayerSpGrade = mItemView.findViewById(R.id.txt_player_spGrade)
        }
        val TAG: String = javaClass.name
        fun bind(item: PlayerDTO, context: Context) {
            itemView.setOnClickListener { itemClick(item) }
//            val playerName = SharedPreferenceUtil().getPref(context, MainActivity().PREF_NAME,item.spId.toString())
            val playerDB = PlayerDataBase.getInstance(context)
            playerDB.let {
                CoroutineScope(Dispatchers.IO).launch {
                    val player = playerDB!!.playerDao().getPlayer(item.spId.toString())
                    player ?: return@launch
                    CoroutineScope(Dispatchers.Main).launch {
                        mPlayerName.text = player.playerName
                    }
                }
            }

            val seasonDB = PlayerDataBase.getInstance(context)
            seasonDB.let {
                CoroutineScope(Dispatchers.IO).launch {
                    val seasonId = item.spId.toString().substring(0,3)
                    val seasonEntity = seasonDB!!.seasonDao().getSeason(seasonId)
                    if(isDebug) Log.v(TAG,"TEST, seasonEntity, seasonId : ${seasonEntity.seasonId} , className : ${seasonEntity.className} , saesonUrl : ${seasonEntity.seasonImg}  ")
                    seasonEntity.let {
                        val url = seasonEntity.seasonImg
                        CoroutineScope(Dispatchers.Main).launch {
                            Log.v(TAG,"TEST, saesonUrl : ${url}")
                            Glide.with(context)
                                .load(url)
                                .listener(object : RequestListener<Drawable> {
                                    override fun onLoadFailed(
                                        e: GlideException?,
                                        model: Any,
                                        target: Target<Drawable>,
                                        isFirstResource: Boolean
                                    ): Boolean {
                                        Log.d(TAG, "Season, onLoadFailed(...) GlideException!!! " + e!!)
                                        mIcon.visibility = View.GONE
                                        return false
                                    }

                                    override fun onResourceReady(
                                        resource: Drawable,
                                        model: Any,
                                        target: Target<Drawable>,
                                        dataSource: DataSource,
                                        isFirstResource: Boolean
                                    ): Boolean {
                                        mIcon.visibility = View.VISIBLE
                                        Log.d(TAG, "Season, onResourceReady(...) $url")
                                        return false
                                    }
                                })
                                .into(mIcon)
                        }
                    }
                }
            }

            mRating.text = item.status.spRating.toString()
            if (mMvpPlayer == null) {
                if (item.status.spRating >= 8) {
                    mRating.background = mContext.getDrawable(R.drawable.rounded_player_team_mvp)
                } else {
                    mRating.background = mContext.getDrawable(R.drawable.rounded_player)
                }
            } else {
                if (item == mMvpPlayer) {
                    mRating.background = mContext.getDrawable(R.drawable.rounded_player_team_mvp)
                } else {
                    mRating.background = mContext.getDrawable(R.drawable.rounded_player)
                }
            }

            mPlayerSpGrade.text = item.spGrade.toString()
            when(item.spGrade) {
                in 0..3 -> {
                    mPlayerSpGrade.background = context.getDrawable(R.drawable.player_grade_bronze)
                }
                in 4..7 -> {
                    mPlayerSpGrade.background = context.getDrawable(R.drawable.player_grade_silver)
                }
                in 8..10 -> {
                    mPlayerSpGrade.background = context.getDrawable(R.drawable.player_grade_gold)
                }
            }

            addGoalIcon(item.status.goal)
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

        fun addGoalIcon(goalCount: Int) {
            val rootView = mItemView.findViewById(R.id.layout_goal) as ConstraintLayout
            val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            rootView.removeAllViews()
            if (goalCount == 0) {
                return
            }
            for(i in 1..goalCount) {
                val imageView: ImageView = inflater.inflate(R.layout.cview_card,rootView,false) as ImageView
                imageView.scaleType= ImageView.ScaleType.FIT_XY
                imageView.background = mContext.getDrawable(R.mipmap.icon_ball)

                var layoutParams= ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
                layoutParams.leftMargin = i * 20
                layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
                imageView.layoutParams = layoutParams
                imageView.layoutParams.height = 50
                imageView.layoutParams.width = 50
                rootView.addView(imageView)
            }
        }
    }
}