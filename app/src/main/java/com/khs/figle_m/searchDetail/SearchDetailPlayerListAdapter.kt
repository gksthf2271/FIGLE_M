package com.khs.figle_m.searchDetail

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
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
import com.khs.figle_m.BuildConfig
import com.khs.data.database.PlayerDataBase
import com.khs.data.database.entity.PlayerEntity
import com.khs.figle_m.R
import com.khs.data.nexon_api.response.DTO.PlayerDTO
import com.khs.figle_m.utils.LogUtil
import com.khs.figle_m.utils.PositionEnum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchDetailPlayerListAdapter(private val mContext: Context, var mPlayerList: List<PlayerDTO>, val itemClick: (PlayerDTO) -> Unit) :
    RecyclerView.Adapter<SearchDetailPlayerListAdapter.ViewHolder>() {
    private val TAG: String = javaClass.simpleName
    val DEBUG = BuildConfig.DEBUG
    var mMvpPlayer: PlayerDTO? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.cview_player_item_view, parent, false)
        viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mPlayerList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mContext, position)
    }

    fun updateMvpPlayer(mvpPlayer: PlayerDTO?) {
        mMvpPlayer = mvpPlayer
    }

    inner class ViewHolder(val mItemView: View) : RecyclerView.ViewHolder(mItemView) {
        val TAG: String = javaClass.simpleName

        var mRootLayout: ConstraintLayout = mItemView.findViewById(R.id.group_player)
        var mPlayerImg: ImageView = mItemView.findViewById(R.id.img_player)
        var mIcon: ImageView = mItemView.findViewById(R.id.img_icon)
        var mRating: TextView = mItemView.findViewById(R.id.txt_rating)
        var mPlayerName: TextView = mItemView.findViewById(R.id.txt_player_name)
        var mPlayerPosition: TextView = mItemView.findViewById(R.id.txt_player_position)
        var mPlayerSpGrade: TextView = mItemView.findViewById(R.id.txt_player_spGrade)

        fun bind(context: Context, position: Int) {
            val item = mPlayerList[position]
            val playerDB = PlayerDataBase.getInstance(context)
            playerDB?.let { playerDataBase ->
                CoroutineScope(Dispatchers.IO).launch {
                    val player : PlayerEntity? = playerDataBase.playerDao().getPlayer(item.spId.toString())
                    player ?: return@launch
                    CoroutineScope(Dispatchers.Main).launch {
                        mPlayerName.text = player.playerName
                    }
                }
            }

            updateSeason(context, item)

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

/*            runBlocking {
                launch {
                    mPlayerList?.let {
                        val item = mPlayerList.get(position)
                        CrawlingUtils.getPlayerImg(item,{
                            updatePlayerImage(mPlayerImg, item, it, position)
                        }, {
                            LogUtil.vLog(LogUtil.TAG_UI, TAG,"Failed Loading...")
                        })
//                        DataManager.loadPlayerImage(item.spId, {
//                            mPlayerList.get(position).imageUrl = it.toString()
//                            updatePlayerImage(mPlayerImg, item, it, position)
//                        }, {
//                            LogUtil.vLog(LogUtil.TAG_UI, TAG,"load Failed : $it")
//                            mPlayerImg.setImageResource(R.drawable.ic_launcher_foreground)
//                        })
                    }
                }
            }*/
            updatePlayerImage(mPlayerImg, item, item.imageUrl!!, position)
            itemView.setOnClickListener { itemClick(item) }
        }

        private fun updateSeason(context: Context, item: PlayerDTO) {
            val seasonDB = PlayerDataBase.getInstance(context)
            seasonDB?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    var seasonId = item.spId.toString().substring(0,3)
                    //Todo 224, 234 분리... 뭐가 맞는지 넥슨측확인 필요 // 답변완료 : 234가 맞음
                    if ("224" == seasonId) seasonId = "234"
                    val seasonEntity = it.seasonDao().getSeason(seasonId)
                    LogUtil.dLog(LogUtil.TAG_SEARCH, TAG,"TEST, seasonEntity, seasonId : ${seasonEntity.seasonId} , className : ${seasonEntity.className} , saesonUrl : ${seasonEntity.seasonImg}  ")
                    seasonEntity.let {
                        val url = it.seasonImg
                        CoroutineScope(Dispatchers.Main).launch {
                            LogUtil.dLog(LogUtil.TAG_SEARCH, TAG,"TEST, seasonUrl : $url")
                            Glide.with(context)
                                .load(url)
                                .listener(object : RequestListener<Drawable> {
                                    override fun onLoadFailed(
                                        e: GlideException?,
                                        model: Any,
                                        target: Target<Drawable>,
                                        isFirstResource: Boolean
                                    ): Boolean {
                                        LogUtil.dLog(LogUtil.TAG_SEARCH, TAG,"Season, onLoadFailed(...) GlideException!!! " + e!!)
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
                                        LogUtil.dLog(LogUtil.TAG_SEARCH, TAG,"Season, onResourceReady(...) $url")
                                        return false
                                    }
                                })
                                .into(mIcon)
                        }
                    }
                }
            }
        }

        private fun addGoalIcon(goalCount: Int) {
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

    fun updatePlayerImage(playerimg: ImageView, item: PlayerDTO, url: String, position: Int) {
        LogUtil.vLog(LogUtil.TAG_SEARCH, TAG,"updatePlayerImage(...) uri : $url")
        Glide.with(playerimg.context)
            .load(Uri.parse(url))
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
//                    LogUtil.dLog(LogUtil.TAG_UI, TAG,"TEST, onLoadFailed(...) GlideException!!! position : $position, url : $playerimg")
//                    LogUtil.dLog(LogUtil.TAG_UI, TAG,"TEST, item : $item")
//                    mPlayerList.let {
//                        if (mPlayerList!!.get(position).subImageUrl == null) {
//                            CrawlingUtils.getPlayerImg(item, {
//                                LogUtil.dLog(LogUtil.TAG_UI, TAG,"TEST, reload position : $position, url : $it")
//                                CrawlingUtils.updatePlayerImage(mContext, playerimg, it)
//                                mPlayerList!!.get(position).subImageUrl = it
//                            }, {
//                                LogUtil.vLog(LogUtil.TAG_UI, TAG,"Failed Crawling! : $it")
//                                mPlayerList!!.get(position).subImageUrl = it
//                            })
//                        } else {
//                            CrawlingUtils.updatePlayerImage(mContext, playerimg, mPlayerList!!.get(position).subImageUrl!!)
//                        }
//                    }
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    LogUtil.dLog(LogUtil.TAG_SEARCH, TAG,"TEST, onResourceReady(...) position : $position, url : $url")
                    return false
                }
            })
            .into(playerimg)
    }
}