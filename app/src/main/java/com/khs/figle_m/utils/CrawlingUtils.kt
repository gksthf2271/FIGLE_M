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
import org.jsoup.nodes.Element

class CrawlingUtils() {
    private val TAG = this.javaClass.name
    private val DEBUG = false

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

                for (i in 0 .. bodyList.size-1){
                    val item = searchBody(i, bodyList[i])
                    if (item == null) continue
                    rankerList.add(item)
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

    fun searchBody(index : Int, body: Element) : Ranker? {
        var rank_no: String?               //순위
        var rank_icon_url: String?         //랭크 아이콘 주소
        var level: String?                 //레벨
        var level_gage: String?            //레벨 경험치 퍼센트
        var name: String?                  //이름
        var price: String?                 //구단가치
        var win: String?         //승무패
        var draw: String?         //승무패
        var lose: String?         //승무패
        var rank_rate: String?             //승률
        var rank_point: String?            //랭크점수
        var rank_percent: String?          //상위 랭크 퍼센트
        var best_rank_icon_url: String?    //역대 랭크 아이콘 주소
        var pre_rank_icon_url: String?     //이전 랭크 아이콘 주소
        var cur_rank_icon_url: String?      //현재 랭크 아이콘 주소

        rank_no = body.childNode(1).childNode(0).toString()
        rank_icon_url = body.childNode(3).childNode(1).childNode(0).attributes().get("src")
        name = body.childNode(3).childNode(3).childNode(3).childNode(0).toString()
        level_gage = body.childNode(3).childNode(3).childNode(1).childNode(0).attributes().get("style")
        level = body.childNode(3).childNode(3).childNode(1).childNode(1).childNode(0).toString()
        price = body.childNode(3).childNode(5).childNode(0).toString()
        win = body.childNode(5).childNode(0).toString()
        draw = body.childNode(5).childNode(2).toString()
        lose = body.childNode(5).childNode(4).toString()
        rank_rate = body.childNode(7).childNode(0).toString()
        rank_point = body.childNode(9).childNode(0).toString()
        rank_percent = body.childNode(11).childNode(0).toString()
        best_rank_icon_url = body.childNode(13).childNode(1).childNode(0).attributes().get("src")
        pre_rank_icon_url = body.childNode(13).childNode(5).childNode(0).attributes().get("src")
        cur_rank_icon_url = body.childNode(13).childNode(9).childNode(0).attributes().get("src")

        val result = Ranker(
            rank_no,
            rank_icon_url,
            level,
            level_gage,
            name,
            price,
            win,
            draw,
            lose,
            rank_rate,
            rank_point,
            rank_percent,
            best_rank_icon_url,
            pre_rank_icon_url,
            cur_rank_icon_url)
        if(DEBUG) Log.v(TAG,"TEST, Ranker : ${result}")
        return result
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