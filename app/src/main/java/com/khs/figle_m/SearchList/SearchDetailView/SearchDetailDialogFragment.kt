package com.khs.figle_m.SearchList.SearchDetailView

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.ViewPager
import com.khs.figle_m.PlayerDetail.DialogBaseFragment
import com.khs.figle_m.PlayerDetail.PlayerDetailDialogFragment
import com.khs.figle_m.R
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Response.DTO.RankerPlayerDTO
import com.khs.figle_m.Response.MatchDetailResponse
import com.khs.figle_m.SearchList.SearchDetailView.customView.SearchDetailDialogTopView
import com.khs.figle_m.utils.DisplayUtils
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.android.synthetic.main.fragment_search_container.*
import kotlinx.android.synthetic.main.fragment_searchlist.avi_loading
import okhttp3.HttpUrl

class SearchDetailDialogFragment : DialogBaseFragment(), SearchDetailContract.View {
    val TAG = javaClass.name

    open val KEY_MATCH_DETAIL_INFO = "KEY_MATCH_DETAIL_INFO"
    open val KEY_SEARCH_ACCESSID = "KEY_SEARCH_ACCESSID"
    open val KEY_IS_COACH_MOC = "KEY_IS_COACH_MOC"

    open val TAG_MATCH_DETAIL_DIALOG = "TAG_MATCH_DETAIL_DIALOG"

    lateinit var mMatchDetail: MatchDetailResponse
    lateinit var mSearchAccessId: String
    lateinit var mSearchDetailPresenter: SearchDetailPresenter

    lateinit var mTopView:SearchDetailDialogTopView
    lateinit var mViewPager:ViewPager
    lateinit var mBtnClose:Button

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
        Log.v(TAG,"onCreateView(...)")
        val v = inflater.inflate(R.layout.fragment_search_container, container,true)
        return v
    }

    override fun onResume() {
        super.onResume()
        mSearchDetailPresenter!!.takeView(this)
        resizeDialog()
        setBackgroundColorDialog()
    }

    override fun onPause() {
        super.onPause()
        Log.v(TAG,"onPause(...)")
        dismiss()
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
        mTopView = view!!.findViewById(R.id.topView)
        mViewPager = view!!.findViewById(R.id.viewPager)
        mBtnClose = view!!.findViewById(R.id.btn_close)
        mBtnClose.setOnClickListener {
            dismiss()
        }
        arguments.let{
            var isCoachMode = arguments!!.getBoolean(KEY_IS_COACH_MOC)!!
            mMatchDetail = arguments!!.getParcelable(KEY_MATCH_DETAIL_INFO)!!
            mSearchAccessId = arguments!!.getString(KEY_SEARCH_ACCESSID)!!
            mViewPager.adapter = SearchDetailDialogAdapter(context!!, mMatchDetail, {
                group_topInfo.background = resources.getDrawable(R.color.fragment_background,null)
                mTopView.updatePlayerInfo(it)
            })
            mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    when (position) {
                        0 -> {
                            mTopView.updateUserView(isCoachMode, mSearchAccessId, mMatchDetail)
                        }
                    }
                }
                override fun onPageSelected(position: Int) {

                }

            })
            initIndicator()
            mViewPager.currentItem = 0
        }
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

    fun getPlayerImageUrlList(playerList: List<PlayerDTO>) : List<String> {
        for (player in playerList) {
            mSearchDetailPresenter.getPlayerImage(player.spId)
        }
        return emptyList()
    }

    override fun showPlayerImage(spId:Int, url: HttpUrl) {

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
    }
}