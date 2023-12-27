package com.khs.figle_m.SearchDetail

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.viewpager.widget.ViewPager
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.MainActivity
import com.khs.figle_m.PlayerDetail.DialogBaseFragment
import com.khs.figle_m.PlayerDetail.PlayerDetailDialogFragment
import com.khs.figle_m.R
import com.khs.figle_m.Response.CustomDTO.PlayerListDTO
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Response.DTO.RankerPlayerDTO
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.Utils.CrawlingUtils
import com.khs.figle_m.Utils.DisplayUtils
import com.khs.figle_m.Utils.LogUtil
import com.khs.figle_m.Utils.NetworkUtils
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.android.synthetic.main.fragment_search_container.*
import kotlinx.android.synthetic.main.fragment_searchlist.avi_loading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchDetailDialogFragment : DialogBaseFragment(),
    SearchDetailContract.View {
    val TAG = javaClass.simpleName

    val KEY_MATCH_DETAIL_INFO = "KEY_MATCH_DETAIL_INFO"
    val KEY_SEARCH_ACCESSID = "KEY_SEARCH_ACCESSID"
    val KEY_IS_COACH_MODE = "KEY_IS_COACH_MODE"

    val TAG_MATCH_DETAIL_DIALOG = "TAG_MATCH_DETAIL_DIALOG"

    lateinit var mMatchDetail: MatchDetailResponse
    lateinit var mSearchAccessId: String
    lateinit var mOpposingUserId: String
    private var mSearchDetailPresenter: SearchDetailPresenter? = null

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
        return inflater.inflate(R.layout.fragment_search_container, container, true)
    }

    override fun onResume() {
        super.onResume()
        context?.let {
            if (!NetworkUtils().checkNetworkStatus(it)) {
                dismiss()
                (activity as MainActivity).showErrorPopup(DataManager().ERROR_NETWORK_DISCONNECTED, false)
                return
            }
        }
        resizeDialog()
        setBackgroundColorDialog()
    }

    override fun onPause() {
        super.onPause()
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"onPause(...)")
    }

    override fun initPresenter() {
        mSearchDetailPresenter = SearchDetailPresenter()
    }

    override fun onDestroy() {
        super.onDestroy()
        mSearchDetailPresenter?.dropView()
    }

    override fun onStart() {
        super.onStart()
        if (isRestartApp) return
        mSearchDetailPresenter?.takeView(this)
        mPlayerImgMap = hashMapOf()
        btn_close.setOnClickListener {
            dismiss()
        }
        arguments?.let{
            mIsCoachMode = it.getBoolean(KEY_IS_COACH_MODE)
            mMatchDetail = it.getParcelable(KEY_MATCH_DETAIL_INFO)!!
            mSearchAccessId = it.getString(KEY_SEARCH_ACCESSID)!!
            when (mSearchAccessId){
                mMatchDetail.matchInfo[0].ouid -> mOpposingUserId = mMatchDetail.matchInfo[1].ouid
                mMatchDetail.matchInfo[1].ouid -> mOpposingUserId = mMatchDetail.matchInfo[0].ouid
            }

            for (matchInfo in mMatchDetail.matchInfo) {
                getPlayerImageUrlList(matchInfo.ouid, matchInfo.player)
            }
        }
    }

    fun initView() {
        viewPager.adapter = SearchDetailDialogAdapter(context!!, mMatchDetail) {
                group_topInfo.background = resources.getDrawable(R.color.fragment_background, null)
                topView.updatePlayerInfo(it)
            }
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                when (position) {
                    0 -> {
                        topView.updateUserView(mIsCoachMode, mSearchAccessId, mMatchDetail)
                    }
                }
            }
            override fun onPageSelected(position: Int) {

            }

        })
        initIndicator()
        viewPager.currentItem = 0
    }

    fun initIndicator() {
        val dotsIndicator = view!!.findViewById<WormDotsIndicator>(R.id.dots_indicator)
        dotsIndicator.setViewPager(viewPager)
    }

    fun resizeDialog(){
        context ?: return
        val size = DisplayUtils().getDisplaySize(context!!)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        val deviceeHeight = size.y
        params?.width = (deviceWidth * 0.95).toInt()
        params?.height = (deviceeHeight * 0.95).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    fun setBackgroundColorDialog() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
    }

    override fun showLoading() {
        avi_loading.visibility = View.VISIBLE
        btn_close.visibility = View.GONE
        group_root.visibility = View.GONE
        avi_loading.show(false)
    }

    override fun hideLoading() {
        avi_loading.hide()
        avi_loading.visibility = View.GONE
        btn_close.visibility = View.VISIBLE
        group_root.visibility = View.VISIBLE
    }

    private fun getPlayerImageUrlList(accessId:String, playerList: List<PlayerDTO>){
        for (player in playerList) {
            mSearchDetailPresenter?.getPlayerImage(accessId, player, playerList.size)
        }
    }

    override fun showPlayerImage(ouid: String, playerDTO: PlayerDTO, size: Int) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG, "showPlayerImage > ouid : $ouid /  ${playerDTO.imageUrl}")
        val playerListDTO : PlayerListDTO = mPlayerImgMap[ouid] ?: PlayerListDTO("", listOf())

        var playerList:ArrayList<PlayerDTO>
        playerListDTO.let {
            playerList = ArrayList((playerListDTO).playerList) ?: arrayListOf()
            playerList.add(playerDTO)
        }
        playerListDTO.playerList = playerList

        mPlayerImgMap[ouid] = playerListDTO
        LogUtil.vLog(LogUtil.TAG_UI, TAG, "showPlayerImage > size : $size / mPlayerImgMap playerList size : ${(mPlayerImgMap[ouid] as PlayerListDTO).playerList.size}")
        if (size == (mPlayerImgMap[ouid] as PlayerListDTO).playerList.size)  {
            hideLoading()
            initView()
        }
    }

    override fun showError(error: Int) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showError(...) $error")
        dismiss()
        (activity as MainActivity).showErrorPopup(error)
    }

    fun showPlayerDetailFragment(playerDTO: PlayerDTO) {
        mSearchDetailPresenter?.getRankerPlayerList(mMatchDetail.matchType, playerDTO)
    }

    override fun showPlayerDetailDialogFragment(
        playerDTO: PlayerDTO,
        rankerPlayerDTOList: List<RankerPlayerDTO>
    ) {
        updatePlayer(playerDTO) { imgUrl ->
            val playerDetailFragment = PlayerDetailDialogFragment.getInstance()
            val bundle = Bundle()
            playerDTO.imageUrl = imgUrl
            bundle.putParcelable(PlayerDetailDialogFragment().KEY_PLAYER_INFO, playerDTO)
            bundle.putParcelableArrayList(
                PlayerDetailDialogFragment().KEY_RANKER_PLAYER_INFO,
                ArrayList(rankerPlayerDTOList)
            )
            playerDetailFragment.arguments = bundle
            if (!playerDetailFragment.isAdded) {
                playerDetailFragment.show(
                    fragmentManager!!,
                    PlayerDetailDialogFragment().TAG_PLAYER_DETAIL_DIALOG
                )
            }
        }
    }

    private fun updatePlayer(player:PlayerDTO, callback: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            CrawlingUtils().getPlayerImg(player, {
                callback(it)
            }, {
                LogUtil.vLog(LogUtil.TAG_UI, TAG,"updatePlayer(...) : $it")
                showError(it)
            })
        }
    }

    fun getPlayerImgMap(): HashMap<String,PlayerListDTO> {
        return mPlayerImgMap
    }
}