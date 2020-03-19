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
import com.example.figle_m.SearchList.SearchDetailView.SearchDetailPlayerListAdapter

class SearchDetailDialogPlayerInfoView : ConstraintLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    val TAG = javaClass.name
    lateinit var mLeftRecyclerView: RecyclerView
    lateinit var mRightRecyclerView: RecyclerView

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_detail_player_view, this)

        mLeftRecyclerView = findViewById(R.id.recyclerview_left_palyer)
        mRightRecyclerView = findViewById(R.id.recyclerview_right_palyer)

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    fun updatePlayerInfo(matchInfo: MatchDetailResponse) {
        Log.v(TAG,"updatePlayerInfo(...) $matchInfo")

        val leftLayoutManager = LinearLayoutManager(context)
        mLeftRecyclerView.addItemDecoration(SearchDecoration(10))
        mLeftRecyclerView.setLayoutManager(leftLayoutManager)
        mLeftRecyclerView.adapter =
            SearchDetailPlayerListAdapter(context!!, initPlayerList(true, matchInfo), {
                Log.v(TAG,"ItemClick! ${it}")
            })

        val rightLayoutManager = LinearLayoutManager(context)
        mRightRecyclerView.addItemDecoration(SearchDecoration(10))
        mRightRecyclerView.setLayoutManager(rightLayoutManager)
        mRightRecyclerView.adapter =
            SearchDetailPlayerListAdapter(context!!, initPlayerList(false, matchInfo), {
                Log.v(TAG,"ItemClick! ${it}")
            })
    }

    fun initPlayerList(isLeftInfo: Boolean, matchInfo: MatchDetailResponse): List<PlayerDTO>{
        var playerList = mutableListOf<PlayerDTO>()
        when (isLeftInfo) {
            true -> playerList.addAll(matchInfo.matchInfo[0].player)
            false -> playerList.addAll(matchInfo.matchInfo[1].player)
        }
        return playerList
    }
}