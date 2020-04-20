package com.khs.figle_m.SearchList.SearchDetailView.customView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khs.figle_m.R
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.Response.customDTO.PlayerListDTO
import com.khs.figle_m.SearchList.SearchDecoration
import com.khs.figle_m.SearchList.SearchDetailView.SearchDetailDialogFragment
import com.khs.figle_m.SearchList.SearchDetailView.SearchDetailPlayerListAdapter
import com.khs.figle_m.utils.UserSortUtils
import kotlinx.android.synthetic.main.cview_detail_player_view.view.*

class SearchDetailDialogPlayerInfoView : ConstraintLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    val TAG = javaClass.name
    lateinit var mLeftRecyclerView: RecyclerView
    lateinit var mRightRecyclerView: RecyclerView
    var mMVPPlayer : PlayerDTO? = null


    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_detail_player_view, this)

        mLeftRecyclerView = findViewById(R.id.recyclerview_left_palyer)
        mRightRecyclerView = findViewById(R.id.recyclerview_right_palyer)

    }

    fun updatePlayerInfo(
        searchUserAccessId: String,
        opposingUserAccessId: String,
        playerMap: HashMap<String, PlayerListDTO>,
        itemClick: (Pair<PlayerDTO, Boolean>) -> Unit
    ) {
        Log.v(TAG, "updatePlayerInfo(...) $playerMap")
        var playerList = listOf<PlayerDTO>()
        val leftLayoutManager = LinearLayoutManager(context)
        mLeftRecyclerView.addItemDecoration(SearchDecoration(10))
        mLeftRecyclerView.setLayoutManager(leftLayoutManager)
        playerList = initPlayerList(
            true,
            (playerMap.get(searchUserAccessId) ?: PlayerListDTO(
                "",
                listOf()
            ) as PlayerListDTO).playerList
        )
//        playerList = initPlayerList(true, matchInfo)
        if (playerList.isEmpty() || playerList.size == 0) {
            group_left_error.visibility = View.VISIBLE
        } else {
            group_left_error.visibility = View.INVISIBLE
        }
        mLeftRecyclerView.adapter =
            SearchDetailPlayerListAdapter(context!!, playerList, {
                Log.v(TAG, "ItemClick! ${it}")
                itemClick(Pair(it, true))
            })

        val rightLayoutManager = LinearLayoutManager(context)
        mRightRecyclerView.addItemDecoration(SearchDecoration(10))
        mRightRecyclerView.setLayoutManager(rightLayoutManager)

        playerList = initPlayerList(
            false, (playerMap.get(opposingUserAccessId) ?: PlayerListDTO(
                "",
                listOf()
            ) as PlayerListDTO).playerList
        )
//        playerList = initPlayerList(false, matchInfo)
        if (playerList.isEmpty() || playerList.size == 0) {
            group_right_error.visibility = View.VISIBLE
        } else {
            group_right_error.visibility = View.INVISIBLE
        }
        mRightRecyclerView.adapter =
            SearchDetailPlayerListAdapter(context!!, playerList, {
                Log.v(TAG, "ItemClick! ${it}")
                itemClick(Pair(it, false))
            })

        (mRightRecyclerView.adapter as SearchDetailPlayerListAdapter).updateMvpPlayer(mMVPPlayer)
        (mLeftRecyclerView.adapter as SearchDetailPlayerListAdapter).updateMvpPlayer(mMVPPlayer)
    }

    fun initPlayerList(isLeftInfo: Boolean, matchInfo: MatchDetailResponse): List<PlayerDTO>{
        var pair = UserSortUtils().sortUserList(SearchDetailDialogFragment.getInstance().mSearchAccessId, matchInfo)
        var playerList = mutableListOf<PlayerDTO>()
        when (isLeftInfo) {
            true -> {
                playerList.addAll(pair.first.player)
                playerList.sortByDescending { it.status.spRating }
                if (!playerList.isEmpty() && mMVPPlayer == null) mMVPPlayer = playerList[0]
            }
            false -> {
                playerList.addAll(pair.second.player)
                playerList.sortByDescending { it.status.spRating }
                if (!playerList.isEmpty()
                    && (mMVPPlayer == null || mMVPPlayer!!.status.spRating < playerList[0].status.spRating)) {
                    mMVPPlayer = playerList[0]
                }
            }
        }
        return playerList
    }

    fun initPlayerList(isLeftInfo: Boolean, listPlayer: List<PlayerDTO>): List<PlayerDTO> {
        var playerList:ArrayList<PlayerDTO> = ArrayList(listPlayer)
        when (isLeftInfo) {
            true -> {
                playerList.sortByDescending { it.status.spRating }
                if (!playerList.isEmpty() && mMVPPlayer == null) mMVPPlayer = playerList[0]
            }
            false -> {
                playerList.sortByDescending { it.status.spRating }
                if (!playerList.isEmpty()
                    && (mMVPPlayer == null || mMVPPlayer!!.status.spRating < playerList[0].status.spRating)) {
                    mMVPPlayer = playerList[0]
                }
            }
        }
        return playerList
    }
}