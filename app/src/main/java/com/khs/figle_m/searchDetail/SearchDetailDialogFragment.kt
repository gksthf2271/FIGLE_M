package com.khs.figle_m.searchDetail

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.viewpager.widget.ViewPager
import com.khs.data.nexon_api.response.CustomDTO.PlayerListDTO
import com.khs.data.nexon_api.response.DTO.PlayerDTO
import com.khs.data.nexon_api.response.DTO.RankerPlayerDTO
import com.khs.data.nexon_api.response.MatchDetailResponse
import com.khs.figle_m.data.DataManager
import com.khs.figle_m.MainActivity
import com.khs.figle_m.playerDetail.DialogBaseFragment
import com.khs.figle_m.playerDetail.PlayerDetailDialogFragment
import com.khs.figle_m.R
import com.khs.figle_m.utils.CrawlingUtils
import com.khs.figle_m.utils.DisplayUtils
import com.khs.figle_m.utils.LogUtil
import com.khs.figle_m.utils.NetworkUtils
import com.khs.figle_m.databinding.FragmentSearchContainerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchDetailDialogFragment : DialogBaseFragment(),
    SearchDetailContract.View {
    private val TAG = javaClass.simpleName
    private lateinit var mBinding : FragmentSearchContainerBinding
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
        mBinding = FragmentSearchContainerBinding.inflate(inflater, container, true)
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        context?.let {
            if (!NetworkUtils().checkNetworkStatus(it)) {
                dismiss()
                (activity as MainActivity).showErrorPopup(DataManager.ERROR_NETWORK_DISCONNECTED, false)
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
        arguments?.let{
            mIsCoachMode = it.getBoolean(KEY_IS_COACH_MODE)
            mMatchDetail = it.getSerializable(KEY_MATCH_DETAIL_INFO) as MatchDetailResponse
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
        mBinding.apply {
            mBinding.btnClose.setOnClickListener {
                dismiss()
            }
            viewPager.adapter = SearchDetailDialogAdapter(requireContext(), mMatchDetail) {
                groupTopInfo.background = context?.getDrawable(R.color.fragment_background)
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
    }

    private fun initIndicator() {
        mBinding.dotsIndicator.attachTo(mBinding.viewPager)
    }

    private fun resizeDialog(){
        requireContext().let { context ->
            val size = DisplayUtils.getDisplaySize(context)
            val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
            val deviceWidth = size.x
            val deviceHeight = size.y
            params?.apply {
                width = (deviceWidth * 0.95).toInt()
                height = (deviceHeight * 0.95).toInt()
                dialog?.window?.attributes = this as WindowManager.LayoutParams
            }
        }
    }

    private fun setBackgroundColorDialog() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
    }

    override fun showLoading() {
        mBinding.apply {
            aviLoading.visibility = View.VISIBLE
            btnClose.visibility = View.GONE
            groupRoot.visibility = View.GONE
            aviLoading.show(false)
        }
    }

    override fun hideLoading() {
        mBinding.apply {
            aviLoading.hide()
            aviLoading.visibility = View.GONE
            btnClose.visibility = View.VISIBLE
            groupRoot.visibility = View.VISIBLE
        }
    }

    private fun getPlayerImageUrlList(accessId:String, playerList: List<PlayerDTO>){
        for (player in playerList) {
            mSearchDetailPresenter?.getPlayerImage(accessId, player, playerList.size)
        }
    }

    override fun showPlayerImage(accessId: String, playerDTO: PlayerDTO, size: Int) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG, "showPlayerImage > accessId : $accessId /  ${playerDTO.imageUrl}")
        val playerListDTO : PlayerListDTO = mPlayerImgMap[accessId] ?: PlayerListDTO("", listOf())

        var playerList:ArrayList<PlayerDTO>
        playerListDTO.let {
            playerList = ArrayList((playerListDTO).playerList) ?: arrayListOf()
            playerList.add(playerDTO)
        }
        playerListDTO.playerList = playerList

        mPlayerImgMap[accessId] = playerListDTO
        LogUtil.vLog(LogUtil.TAG_UI, TAG, "showPlayerImage > size : $size / mPlayerImgMap playerList size : ${(mPlayerImgMap[accessId] as PlayerListDTO).playerList.size}")
        if (size == (mPlayerImgMap[accessId] as PlayerListDTO).playerList.size)  {
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
            val bundle = Bundle().apply {
                playerDTO.imageUrl = imgUrl
                putSerializable(PlayerDetailDialogFragment().KEY_PLAYER_INFO, playerDTO)
                putSerializable(
                    PlayerDetailDialogFragment().KEY_RANKER_PLAYER_INFO,
                    ArrayList(rankerPlayerDTOList)
                )
            }
            playerDetailFragment.arguments = bundle
            if (!playerDetailFragment.isAdded) {
                fragmentManager?.let {
                    playerDetailFragment.show(it, PlayerDetailDialogFragment().TAG_PLAYER_DETAIL_DIALOG)
                }
            }
        }
    }

    private fun updatePlayer(player: PlayerDTO, callback: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            CrawlingUtils.getPlayerImg(player, {
                callback(it)
            }, {
                LogUtil.vLog(LogUtil.TAG_UI, TAG,"updatePlayer(...) : $it")
                showError(it)
            })
        }
    }

    fun getPlayerImgMap(): HashMap<String, PlayerListDTO> {
        return mPlayerImgMap
    }
}