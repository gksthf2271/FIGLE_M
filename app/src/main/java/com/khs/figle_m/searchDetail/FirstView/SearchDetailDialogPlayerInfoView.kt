package com.khs.figle_m.searchDetail.FirstView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.khs.data.nexon_api.response.CustomDTO.PlayerListDTO
import com.khs.data.nexon_api.response.DTO.PlayerDTO
import com.khs.data.nexon_api.response.MatchDetailResponse
import com.khs.figle_m.searchDetail.SearchDetailDialogFragment
import com.khs.figle_m.searchDetail.SearchDetailPlayerListAdapter
import com.khs.figle_m.searchList.SearchDecoration
import com.khs.figle_m.utils.LogUtil
import com.khs.figle_m.utils.UserSortUtils
import com.khs.figle_m.databinding.CviewDetailPlayerViewBinding

class SearchDetailDialogPlayerInfoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private val TAG = javaClass.simpleName
    private var mBinding : CviewDetailPlayerViewBinding
    private var mMVPPlayer : PlayerDTO? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = CviewDetailPlayerViewBinding.inflate(inflater, this, true)
    }

    fun updatePlayerInfo(
        searchUserAccessId: String,
        opposingUserAccessId: String,
        playerMap: HashMap<String, PlayerListDTO>,
        itemClick: (Pair<PlayerDTO, Boolean>) -> Unit
    ) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"updatePlayerInfo(...) $playerMap")
        var playerList = initPlayerList(true, (playerMap[searchUserAccessId] ?: PlayerListDTO("", listOf())).playerList)
        mBinding.recyclerviewLeftPalyer.addItemDecoration(SearchDecoration(10))
        mBinding.recyclerviewLeftPalyer.layoutManager = LinearLayoutManager(context)
        if (playerList.isEmpty()) {
            mBinding.groupLeftError.visibility = View.VISIBLE
        } else {
            mBinding.groupLeftError.visibility = View.INVISIBLE
        }
        mBinding.recyclerviewLeftPalyer.adapter = SearchDetailPlayerListAdapter(context, playerList) { playerDTO ->
                LogUtil.vLog(LogUtil.TAG_UI, TAG,"ItemClick! $playerDTO")
                itemClick(Pair(playerDTO, true))
            }

        mBinding.recyclerviewRightPalyer.addItemDecoration(SearchDecoration(10))
        mBinding.recyclerviewRightPalyer.layoutManager = LinearLayoutManager(context)

        playerList = initPlayerList(false, (playerMap[opposingUserAccessId] ?: PlayerListDTO("", listOf())).playerList)
        if (playerList.isEmpty()) {
            mBinding.groupRightError.visibility = View.VISIBLE
        } else {
            mBinding.groupRightError.visibility = View.INVISIBLE
        }
        mBinding.recyclerviewRightPalyer.adapter = SearchDetailPlayerListAdapter(context, playerList) { playerDTO ->
                LogUtil.vLog(LogUtil.TAG_UI, TAG,"ItemClick! $playerDTO")
                itemClick(Pair(playerDTO, false))
            }

        (mBinding.recyclerviewRightPalyer.adapter as SearchDetailPlayerListAdapter).updateMvpPlayer(mMVPPlayer)
        (mBinding.recyclerviewLeftPalyer.adapter as SearchDetailPlayerListAdapter).updateMvpPlayer(mMVPPlayer)
    }

    fun initPlayerList(isLeftInfo: Boolean, matchInfo: MatchDetailResponse): List<PlayerDTO>{
        val pair = UserSortUtils.sortUserList(SearchDetailDialogFragment.getInstance().mSearchAccessId, matchInfo)
        val playerList = mutableListOf<PlayerDTO>()
        when (isLeftInfo) {
            true -> {
                playerList.addAll(pair.first.player)
                playerList.sortByDescending { it.status.spRating }
                if (playerList.isNotEmpty() && mMVPPlayer == null) mMVPPlayer = playerList[0]
            }
            false -> {
                playerList.addAll(pair.second.player)
                playerList.sortByDescending { it.status.spRating }
                if (playerList.isNotEmpty()
                    && (mMVPPlayer == null || mMVPPlayer!!.status.spRating < playerList[0].status.spRating)) {
                    mMVPPlayer = playerList[0]
                }
            }
        }
        return playerList
    }

    private fun initPlayerList(isLeftInfo: Boolean, listPlayer: List<PlayerDTO>): List<PlayerDTO> {
        val playerList:ArrayList<PlayerDTO> = ArrayList(listPlayer)
        when (isLeftInfo) {
            true -> {
                playerList.sortByDescending { it.status.spRating }
                if (playerList.isNotEmpty() && mMVPPlayer == null) mMVPPlayer = playerList[0]
            }
            false -> {
                playerList.sortByDescending { it.status.spRating }
                if (playerList.isNotEmpty()
                    && (mMVPPlayer == null || mMVPPlayer!!.status.spRating < playerList[0].status.spRating)) {
                    mMVPPlayer = playerList[0]
                }
            }
        }
        return playerList
    }
}