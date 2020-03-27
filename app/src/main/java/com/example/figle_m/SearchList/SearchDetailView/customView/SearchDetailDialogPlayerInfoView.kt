package com.example.figle_m.SearchList.SearchDetailView.customView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.figle_m.R
import com.example.figle_m.Response.DTO.PlayerDTO
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.SearchList.SearchDecoration
import com.example.figle_m.SearchList.SearchDetailView.SearchDetailDialogFragment
import com.example.figle_m.SearchList.SearchDetailView.SearchDetailPlayerListAdapter
import com.example.figle_m.utils.UserSortUtils

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

    fun updatePlayerInfo(matchInfo: MatchDetailResponse, itemClick: (Pair<PlayerDTO,Boolean>) -> Unit) {
        Log.v(TAG,"updatePlayerInfo(...) $matchInfo")

        val leftLayoutManager = LinearLayoutManager(context)
        mLeftRecyclerView.addItemDecoration(SearchDecoration(10))
        mLeftRecyclerView.setLayoutManager(leftLayoutManager)
        mLeftRecyclerView.adapter =
            SearchDetailPlayerListAdapter(context!!, initPlayerList(true, matchInfo), {
                Log.v(TAG,"ItemClick! ${it}")
                itemClick(Pair(it,true))
            })

        val rightLayoutManager = LinearLayoutManager(context)
        mRightRecyclerView.addItemDecoration(SearchDecoration(10))
        mRightRecyclerView.setLayoutManager(rightLayoutManager)
        mRightRecyclerView.adapter =
            SearchDetailPlayerListAdapter(context!!, initPlayerList(false, matchInfo), {
                Log.v(TAG,"ItemClick! ${it}")
                itemClick(Pair(it,false))
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
}