package com.khs.figle_m.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.khs.figle_m.BuildConfig
import com.khs.figle_m.DB.PlayerDataBase
import com.khs.figle_m.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class DrawUtils () {
    private val TAG: String = javaClass.name
    val DEBUG = BuildConfig.DEBUG

    fun drawPlayerImage(playerimg: ImageView, url: String) {
        Log.v(TAG,"updatePlayerImage(...) uri : ${url}")
        Glide.with(playerimg.getContext())
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
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(playerimg)
    }

    fun drawSeasonIcon(context: Context, targetView: ImageView, spId: String) {
        val seasonDB = PlayerDataBase.getInstance(context)
        seasonDB.let {
            CoroutineScope(Dispatchers.IO).launch {
                if (spId.length < 3) return@launch
                var seasonId = spId.substring(0,3)
                //Todo 224, 234 분리... 뭐가 맞는지 넥슨측확인 필요 // 답변완료 : 234가 맞음
                if ("224".equals(seasonId)) seasonId = "234"
                val seasonEntity = seasonDB!!.seasonDao().getSeason(seasonId)
                if(!DEBUG) Log.v(TAG,"TEST, seasonEntity, seasonId : ${seasonEntity.seasonId} , className : ${seasonEntity.className} , saesonUrl : ${seasonEntity.seasonImg}  ")
                seasonEntity.let {
                    val url = seasonEntity.seasonImg
                    CoroutineScope(Dispatchers.Main).launch {
                        if(DEBUG) Log.v(TAG,"TEST, saesonUrl : ${url}")
                        Glide.with(context)
                            .load(url)
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any,
                                    target: Target<Drawable>,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    if(DEBUG) Log.d(TAG, "Season, onLoadFailed(...) GlideException!!! " + e!!)
                                    targetView.visibility = View.GONE
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable,
                                    model: Any,
                                    target: Target<Drawable>,
                                    dataSource: DataSource,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    targetView.visibility = View.VISIBLE
                                    if(DEBUG) Log.d(TAG, "Season, onResourceReady(...) $url")
                                    return false
                                }
                            })
                            .into(targetView)
                    }
                }
            }
        }
    }
}