package com.khs.figle_m.SearchDetail

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import com.khs.figle_m.PlayerDetail.DialogBaseFragment
import com.khs.figle_m.PlayerDetail.PlayerDetailDialogFragment
import com.khs.figle_m.R
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Response.DTO.RankerPlayerDTO
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.Response.customDTO.PlayerListDTO
import com.khs.figle_m.SearchDetail.firstView.SearchDetailDialogTopView
import com.khs.figle_m.utils.CrawlingUtils
import com.khs.figle_m.utils.DisplayUtils
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.android.synthetic.main.fragment_search_container.*
import kotlinx.android.synthetic.main.fragment_searchlist.avi_loading

class SearchDetailDialogFragment : DialogBaseFragment(),
    SearchDetailContract.View {
    val TAG = javaClass.name

    open val KEY_MATCH_DETAIL_INFO = "KEY_MATCH_DETAIL_INFO"
    open val KEY_SEARCH_ACCESSID = "KEY_SEARCH_ACCESSID"
    open val KEY_IS_COACH_MODE = "KEY_IS_COACH_MODE"

    open val TAG_MATCH_DETAIL_DIALOG = "TAG_MATCH_DETAIL_DIALOG"

    lateinit var mMatchDetail: MatchDetailResponse
    lateinit var mSearchAccessId: String
    lateinit var mOpposingUserId: String
    lateinit var mSearchDetailPresenter: SearchDetailPresenter

    lateinit var mTopView: SearchDetailDialogTopView
    lateinit var mViewPager:ViewPager
    lateinit var mBtnClose:Button

    var mIsCoachMode: Boolean = false

    lateinit var mPlayerImgMap : HashMap<String, PlayerListDTO>

    companion object {
        @Volatile
        private var instance: SearchDetailDialogFragment? = null

        @JvmStatic
        fun getInstance(): SearchDetailDialogFragment =
            instance ?: synchronized(this) {
                instance
                    ?: SearchDetailDialogFragment().also {
                        instance = it
                    }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_search_container, container,true)
        return v
    }

    override fun onResume() {
        super.onResume()
        resizeDialog()
        setBackgroundColorDialog()
    }

    override fun onPause() {
        super.onPause()
        Log.v(TAG,"onPause(...)")
    }

    override fun initPresenter() {
        mSearchDetailPresenter = SearchDetailPresenter()
    }

    override fun onDestroy() {
        super.onDestroy()
        mSearchDetailPresenter!!.dropView()
    }

    override fun onStart() {
        super.onStart()
        if (isRestartApp) return
        mSearchDetailPresenter!!.takeView(this)
        mPlayerImgMap = hashMapOf()
        mTopView = view!!.findViewById(R.id.topView)
        mViewPager = view!!.findViewById(R.id.viewPager)
        mBtnClose = view!!.findViewById(R.id.btn_close)
        mBtnClose.setOnClickListener {
            dismiss()
        }
        arguments.let{
            mIsCoachMode = arguments!!.getBoolean(KEY_IS_COACH_MODE)!!
            mMatchDetail = arguments!!.getParcelable(KEY_MATCH_DETAIL_INFO)!!
            mSearchAccessId = arguments!!.getString(KEY_SEARCH_ACCESSID)!!
            when (mSearchAccessId){
                mMatchDetail.matchInfo[0].accessId -> mOpposingUserId = mMatchDetail.matchInfo[1].accessId
                mMatchDetail.matchInfo[1].accessId -> mOpposingUserId = mMatchDetail.matchInfo[0].accessId
            }

            for (matchInfo in mMatchDetail.matchInfo) {
                getPlayerImageUrlList(matchInfo.accessId, matchInfo.player)
            }
        }
    }

    fun initView() {
        mViewPager.adapter =
            SearchDetailDialogAdapter(context!!, mMatchDetail, {
                group_topInfo.background = resources.getDrawable(R.color.fragment_background, null)
                mTopView.updatePlayerInfo(it)
            })
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                when (position) {
                    0 -> {
                        mTopView.updateUserView(mIsCoachMode, mSearchAccessId, mMatchDetail)
                    }
                }
            }
            override fun onPageSelected(position: Int) {

            }

        })
        initIndicator()
        mViewPager.currentItem = 0
    }

    fun initIndicator() {
        val dotsIndicator = view!!.findViewById<WormDotsIndicator>(R.id.dots_indicator)
        dotsIndicator.setViewPager(mViewPager)
    }

    fun resizeDialog(){
        context ?: return
        val size = DisplayUtils().getDisplaySize(context!!)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        val deviceeHeight = size.y
        params?.width = (deviceWidth * 0.9).toInt()
        params?.height = (deviceeHeight * 0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    fun setBackgroundColorDialog() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
    }

    override fun showLoading() {
        if (avi_loading.isShown) return
        Log.v(TAG,"showLoading(...)")
        avi_loading.visibility = View.VISIBLE
        btn_close.visibility = View.GONE
        group_root.visibility = View.GONE
        avi_loading.show(false)
    }

    override fun hideLoading() {
        Log.v(TAG,"hideLoading(...)")
        avi_loading.hide()
        avi_loading.visibility = View.GONE
        btn_close.visibility = View.VISIBLE
        group_root.visibility = View.VISIBLE
    }

    fun getPlayerImageUrlList(accessId:String, playerList: List<PlayerDTO>){
        for (player in playerList) {
            mSearchDetailPresenter.getPlayerImage(accessId, player, playerList.size)
        }
    }

    override fun showPlayerImage(accessId: String, playerDTO: PlayerDTO, size: Int) {
        var playerListDTO =
            mPlayerImgMap.get(accessId) ?: PlayerListDTO("", listOf()) as PlayerListDTO

        var playerList:ArrayList<PlayerDTO>
        playerListDTO.let {
            playerList = ArrayList((playerListDTO).playerList) ?: arrayListOf()
            playerList.add(playerDTO)
        }
        playerListDTO.playerList = playerList

        mPlayerImgMap.put(accessId,playerListDTO)
        if (size == (mPlayerImgMap.get(accessId) as PlayerListDTO).playerList.size)  {
            hideLoading()
            initView()
        }
    }

    override fun showError(error: String) {
        Log.v(TAG,"showError(...) $error")
    }

    fun showPlayerDetailFragment(playerDTO: PlayerDTO) {
        mSearchDetailPresenter.getRankerPlayerList(mMatchDetail.matchType, playerDTO)
    }

    override fun showPlayerDetailDialogFragment(
        playerDTO: PlayerDTO,
        rankerPlayerDTOList: List<RankerPlayerDTO>
    ) {
        updatePlayer(playerDTO, {
            var playerDetailFragment = PlayerDetailDialogFragment.getInstance()
            val bundle = Bundle()
            bundle.putParcelable(PlayerDetailDialogFragment().KEY_PLAYER_INFO, playerDTO)
            bundle.putParcelableArrayList(PlayerDetailDialogFragment().KEY_RANKER_PLAYER_INFO, ArrayList(rankerPlayerDTOList))
            playerDetailFragment.arguments = bundle
            if (!playerDetailFragment.isAdded) {
                playerDetailFragment.show(
                    fragmentManager!!,
                    PlayerDetailDialogFragment().TAG_PLAYER_DETAIL_DIALOG
                )
            }
        })
    }

    fun updatePlayer(player:PlayerDTO, callback: (String) -> Unit) {
        CrawlingUtils().getPlayerImg(player,{
            callback(it)
        }, {
            Log.v(TAG,"updatePlayer(...) : $it")
        })
    }

    fun getPlayerImgMap(): HashMap<String,PlayerListDTO> {
        return mPlayerImgMap
    }
}