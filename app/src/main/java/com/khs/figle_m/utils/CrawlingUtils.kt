package com.khs.figle_m.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.R
import com.khs.figle_m.Ranking.Ranker
import com.khs.figle_m.Response.DTO.PlayerDTO
import org.jsoup.Jsoup

class CrawlingUtils() {
    private val TAG = this.javaClass.name

    fun getPlayerImg(playerDTO: PlayerDTO, onSuccess: (String) -> Unit, onFailed: (Int) -> Unit) {
        val seasonId = playerDTO.spId.toString().substring(0, 3)
        var seasonName: String? = null
        var imageUrl: String? = null
        for (item in SeasonEnum.values()) {
            if (item.seasonId.toString().equals(seasonId))
                seasonName = item.className
        }
        if (seasonName == null) {
            Log.d(TAG, "seasonName is null")
            onFailed(0)
            return
        }
        try {
            DataManager.getInstance().loadPlayerInfo(playerDTO.spId, playerDTO.spGrade, {

                val doc = Jsoup.parseBodyFragment(it.string())
                val parentBody = doc.body().getElementById("wrapper")
                    .getElementById("middle")
                if (parentBody == null) {
                    Log.e(TAG, "ERROR ----------- \n $doc")
                    onFailed(0)
                    return@loadPlayerInfo
                }

                val imageUrl = parentBody
                    .getElementsByClass("datacenter").get(0)
                    .getElementsByClass("wrap").get(0)
                    .getElementsByClass("player_view").get(0)
                    .getElementsByClass("content data_detail").get(0)
                    .getElementsByClass("wrap").get(0)
                    .getElementsByClass("content_header").get(0)
                    .getElementsByClass("thumb ${seasonName}").get(0)
                    .getElementsByClass("img").get(0)
                    .childNodes().get(0)
                    .attributes().get("src")

                onSuccess(imageUrl!!)
            }, {
                onFailed(it)
            })
        } catch (e: IllegalStateException) {
            Log.d(TAG, "Error : $e")
            onFailed(0)
        }
    }

    fun getRanking(page : Int, onSuccess: (List<Ranker>) -> Unit, onFailed: (Int) -> Unit) {
        try {
            DataManager.getInstance().loadRaking(page, {
                val doc = Jsoup.parseBodyFragment(it.string())
                val parentBody = doc.body().getElementById("wrapper")
                    .getElementById("middle")
                if (parentBody == null) {
                    Log.e(TAG, "ERROR ----------- \n $doc")
                    onFailed(0)
                    return@loadRaking
                }
                val rankerList = arrayListOf<Ranker>()
                val bodyList = parentBody
                    .getElementsByClass("datacenter").get(0)
                    .getElementsByClass("wrap").get(0)
                    .getElementsByClass("board_list datacenter_rank").get(0)
                    .getElementsByClass("content datacenter_rank_list").get(0)
                    .getElementsByClass("list_wrap").get(0)
                    .getElementsByClass("tbody").get(0)
                    .getElementsByClass("tr")

                for (i in 0 .. bodyList.size){
                    rankerList.add(searchBody(null, i))
                }

                onSuccess(rankerList)
            }, {
                onFailed(it)
            })
        } catch (e: IllegalStateException) {
            Log.d(TAG, "Error : $e")
            onFailed(0)
        }
    }

    fun searchBody(ranker: Ranker?, index : Int) : Ranker {
        if (ranker == null) {
            searchBody(
                Ranker(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                ), index
            )
        }

        
    }

    fun updatePlayerImage(context: Context, imgView: ImageView, url: String) {
        Log.v(TAG,"updatePlayerImage :: $url")
        url ?: return
        Glide.with(context!!)
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
            .into(imgView)
    }
}