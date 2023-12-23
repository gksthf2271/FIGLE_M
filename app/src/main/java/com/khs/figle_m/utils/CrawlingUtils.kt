package com.khs.figle_m.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.khs.figle_m.BuildConfig
import com.khs.figle_m.data.DataManager
import com.khs.figle_m.R
import com.khs.figle_m.ranking.Ranker
import com.khs.data.nexon_api.response.DTO.PlayerDTO
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

object CrawlingUtils {
    private val TAG = this.javaClass.simpleName
    private val DEBUG = BuildConfig.DEBUG

    fun getPlayerImg(playerDTO: PlayerDTO, onSuccess: (String) -> Unit, onFailed: (Int) -> Unit) {
        getPlayerImg(playerDTO.spId, playerDTO.spGrade, onSuccess, onFailed)
    }

    fun getPlayerImg(spId: Int, spGrade:Int, onSuccess: (String) -> Unit, onFailed: (Int) -> Unit) {
        var cSpId = spId
        var seasonId = spId.toString().substring(0, 3)
        var seasonName: String? = null
        if (seasonId == "224") {
            LogUtil.vLog(LogUtil.TAG_NETWORK, TAG,"getPlayerImage > 224 : $spId")
            cSpId = spId.toString().replaceRange(0 .. 2, "234").toInt()
            seasonId = "234"
        }
        for (item in SeasonManager.loadSeason()) {
            if (item.seasonId == seasonId)
                seasonName = item.classId
        }
        if (seasonName == null) {
            LogUtil.vLog(LogUtil.TAG_UI, TAG,"seasonName is null")
            onFailed(0)
            return
        }
        try {
            DataManager.loadPlayerInfo(cSpId, spGrade, {

                val doc = Jsoup.parseBodyFragment(it.string())
                val parentBody: Element? = doc.body().getElementById("wrapper")?.getElementById("middle")
                if (parentBody == null) {
                    LogUtil.eLog(LogUtil.TAG_UI, TAG,"ERROR ----------- \n $doc")
                    onFailed(0)
                    return@loadPlayerInfo
                }

                var imageUrl = ""
                try {
                    imageUrl = parentBody
                        .getElementsByClass("datacenter")[0]
                        .getElementsByClass("wrap")[0]
                        .getElementsByClass("player_view")[0]
                        .getElementsByClass("content data_detail")[0]
                        .getElementsByClass("wrap")[0]
                        .getElementsByClass("content_header")[0]
                        .getElementsByClass("thumb $seasonName  _${seasonName.toUpperCase()}")[0]
                        .getElementsByClass("img")[0]
                        .childNodes()[0]
                        .attributes().get("src")
                }catch (e : IndexOutOfBoundsException) {
                    LogUtil.eLog(LogUtil.TAG_UI, TAG,"Error, $seasonName")
                }

                onSuccess(imageUrl!!)
            }, {
                onFailed(it)
            })
        } catch (e: IllegalStateException) {
            LogUtil.eLog(LogUtil.TAG_UI, TAG,"Error : $e")
            onFailed(0)
        } catch (index: IndexOutOfBoundsException) {
            LogUtil.eLog(LogUtil.TAG_UI, TAG,"Error : $index")
            onFailed(0)
        }
    }

    fun getPlayerImage(
        playerDTO: PlayerDTO,
        onSuccess: ((String) -> Unit),
        onFailed: (Int) -> Unit
    ) {
        var seasonId = playerDTO.spId.toString().substring(0, 3)
        var seasonName: String? = null
        if (seasonId == "224") {
            LogUtil.vLog(LogUtil.TAG_NETWORK, TAG,"getPlayerImage > 224 : $playerDTO")
            playerDTO.spId = playerDTO.spId.toString().replaceRange(0 .. 2, "234").toInt()
            seasonId = "234"
        }
        for (item in SeasonManager.loadSeason()) {
            if (item.seasonId == seasonId)
                seasonName = item.seasonId
        }

        LogUtil.vLog(LogUtil.TAG_NETWORK, TAG,"getPlayerImage > seasonName : $seasonName")
        if (seasonName == null) {
            onFailed(0)
            return
        }

        try {
            DataManager.loadPlayerInfo(playerDTO.spId, playerDTO.spGrade, {
                val doc = Jsoup.parseBodyFragment(it.string())
                val parentBody = doc.body().getElementById("wrapper")
                    .getElementById("middle")
                if (parentBody == null) {
                    onFailed(0)
                    return@loadPlayerInfo
                }

                try {
                    val imageUrl = parentBody
                        .getElementsByClass("datacenter").first()
                        .getElementsByClass("wrap").first()
                        .getElementsByClass("player_view").first()
                        .getElementsByClass("content data_detail").first()
                        .getElementsByClass("wrap").first()
                        .getElementsByClass("content_header").first()
                        .getElementsByClass("thumb $seasonName  _${seasonName.toUpperCase()}").first()
                        .getElementsByClass("img").first()
                        .childNodes().first()
                        .attributes().get("src")
                    onSuccess(imageUrl!!)
                } catch (e : Exception) {
                    LogUtil.eLog(LogUtil.TAG_NETWORK, TAG,"getPlayerImage > exception $e")
                    onFailed(0)
                }
            }, {

            })
        }catch (e : Exception) {
            onFailed(0)
        }
    }

    fun getRanking(page : Int, onSuccess: (List<Ranker>) -> Unit, onFailed: (Int) -> Unit) {
        try {
            DataManager.loadRaking(page, {
                val doc = Jsoup.parseBodyFragment(it.string())
                val parentBody = doc.body().getElementById("wrapper")
                    .getElementById("middle")
                if (parentBody == null) {
                    LogUtil.eLog(LogUtil.TAG_UI, TAG,"ERROR ----------- \n $doc")
                    onFailed(0)
                    return@loadRaking
                }
                val rankerList = arrayListOf<Ranker>()
                val bodyList = parentBody
                    .getElementsByClass("datacenter")[0]
                    .getElementsByClass("wrap")[0]
                    .getElementsByClass("board_list datacenter_rank")[0]
                    .getElementsByClass("content datacenter_rank_list")[0]
                    .getElementsByClass("list_wrap")[0]
                    .getElementsByClass("tbody")[0]
                    .getElementsByClass("tr")

                for (i in 0 until bodyList.size){
                    val item = searchBody(i, bodyList[i]) ?: continue
                    rankerList.add(item)
                }

                onSuccess(rankerList)
            }, {
                onFailed(it)
            })
        } catch (e: IllegalStateException) {
            LogUtil.eLog(LogUtil.TAG_UI, TAG,"Error : $e")
            onFailed(0)
        }
    }

    fun searchBody(index : Int, body: Element) : Ranker? {
        var rank_no: String? = null               //순위
        var rank_icon_url: String? = null        //랭크 아이콘 주소
        var level: String? = null               //레벨
        var level_gage: String? = null            //레벨 경험치 퍼센트
        var name: String? = null                  //이름
        var price: String? = null                 //구단가치
        var win: String? = null         //승무패
        var draw: String? = null         //승무패
        var lose: String? = null         //승무패
        var rank_rate: String? = null             //승률
        var rank_point: String? = null            //랭크점수
        var rank_percent: String? = null          //상위 랭크 퍼센트
        var best_rank_icon_url: String? = null    //역대 랭크 아이콘 주소
        var pre_rank_icon_url: String? = null     //이전 랭크 아이콘 주소
        var cur_rank_icon_url: String? = null      //현재 랭크 아이콘 주소

        try{
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
        } catch(npe : NullPointerException){
            LogUtil.eLog(LogUtil.TAG_UI, TAG,"searchBody NPE!")
        }

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
        LogUtil.dLog(LogUtil.TAG_UI, TAG,"TEST, Ranker : ${result}")
        return result
    }

    fun updatePlayerImage(context: Context, imgView: ImageView, url: String) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"updatePlayerImage :: $url")
        url ?: return
        Glide.with(context)
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
                    LogUtil.dLog(LogUtil.TAG_UI, TAG,"onLoadFailed(...) GlideException!!! " + e!!)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    LogUtil.dLog(LogUtil.TAG_UI, TAG,"onResourceReady(...) $url")
                    return false
                }
            })
            .into(imgView)
    }
}