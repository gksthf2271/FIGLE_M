package com.example.figle_m

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import com.example.figle_m.Data.DataManager
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.Response.UserResponse
import com.example.figle_m.View.UserPresenter
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.ResponseBody

class HomeFragment : BaseFragment(), UserContract.View, Handler.Callback {
    val TAG: String = javaClass.name

    val MSG_SHOW_USER_LIST : Int = 0
    val MSG_SHOW_MATCH_DETAIL_LIST : Int = 1
    val MSG_SHOW_MATCH_ID_LIST : Int = 2
    val mHandler: Handler = Handler(this)

    lateinit var mUserPresenter: UserPresenter

    override fun initPresenter() {
        Log.v(TAG, "initPresenter(...)")
        mUserPresenter = UserPresenter()
    }

    companion object {
        @Volatile
        private var instance: HomeFragment? = null

        @JvmStatic
        fun getInstance(): HomeFragment =
            instance ?: synchronized(this) {
                instance ?: HomeFragment().also {
                    instance = it
                }
            }
    }

    override fun handleMessage(msg: Message): Boolean {
        when (msg.what) {
            MSG_SHOW_USER_LIST -> {
                val userResponse: UserResponse = msg.obj as (UserResponse)
                Log.v(TAG, "MSG_SHOW_USER_LIST result ::: " + msg.obj.toString())

                userResponse.accessId ?: return false

                mUserPresenter!!.getMatchId(
                    userResponse.accessId!!,
                    DataManager.matchType.normalMatch.matchType,
                    DataManager.getInstance().offset,
                    DataManager.getInstance().limit
                )
                return true
            }
            MSG_SHOW_MATCH_DETAIL_LIST -> {
                Log.v(TAG, "MSG_SHOW_MATCH_DETAIL_LIST result ::: " + msg.obj.toString())
                return true
            }
            MSG_SHOW_MATCH_ID_LIST -> {
                val responseBody: ResponseBody = msg.obj as ResponseBody
                var result:String = responseBody.string()
                val stringList:List<String> = result.removeSurrounding("[","]").replace("\"","").split(",")

                Log.v(TAG, "MSG_SHOW_MATCH_ID_LIST result ::: " + stringList[0])
                mUserPresenter!!.getMatchDetailList(stringList[0])
                return true
            }
            else -> {
                return false
            }
        }
        return false
    }

    @SuppressLint("SetTextI18n")
    override fun showUserList(userResponse: UserResponse?) {
        if (userResponse == null) {
            Log.d(TAG, "userResponse is null")
            return
        }
        val msg_show_user_list:Message = Message()
        msg_show_user_list.what = MSG_SHOW_USER_LIST
        msg_show_user_list.obj = userResponse
        mHandler.sendMessage(msg_show_user_list)
    }

    @SuppressLint("SetTextI18n")
    override fun showMatchDetailList(matchDetailResponse: MatchDetailResponse?) {
        if (matchDetailResponse == null) {
            Log.d(TAG, "matchDetailResponse is null")
            return
        }
        val msg_show_match_detail_list:Message = Message()
        msg_show_match_detail_list.what = MSG_SHOW_MATCH_DETAIL_LIST
        msg_show_match_detail_list.obj = matchDetailResponse
        mHandler.sendMessage(msg_show_match_detail_list)
    }

    @SuppressLint("SetTextI18n")
    override fun showMatchIdList(userMatchIdResponse: ResponseBody?) {
        if (userMatchIdResponse == null) {
            Log.d(TAG, "userMatchIdResponse is null")
            return
        }
        val msg_show_match_id_list:Message = Message()
        msg_show_match_id_list.what = MSG_SHOW_MATCH_ID_LIST
        msg_show_match_id_list.obj = userMatchIdResponse
        mHandler.sendMessage(msg_show_match_id_list)
    }

    override fun showLoading() {
        Log.v(TAG,"showLoading(...)")
    }

    override fun hideLoading() {
        Log.v(TAG,"hideLoading(...)")
    }

    override fun showError(error: String) {
        Log.v(TAG,"showError(...)")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_home, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    fun initView() {
        if (mUserPresenter == null) return
        mUserPresenter!!.takeView(this)
        edit_search.imeOptions = IME_ACTION_SEARCH

        edit_search.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                search(edit_search.text.toString())
             return@OnKeyListener true
            }
            false
        })
        btn_search.setOnClickListener(View.OnClickListener {
            Log.v(TAG,"clickSearchBtn")
            search(edit_search.text.toString())
        })
    }

    fun search(searchString: String) {
        mUserPresenter!!.getUserDatailList(searchString)
//        mUserPresenter!!.getMatchDetailList("5e48262ba9458a223fd64791")
    }

//    @OnClick(R.id.btn_search)
//    fun clickSearchBtn(view: View) {
//        Log.v(TAG,"clickSearchBtn")
//        val searchText: String? = getSearchText()
//        if (searchText == null) {
//            Log.v(TAG,"searchText is null")
//            return
//        }
//        if (mUserPresenter == null) return
//        mUserPresenter!!.getUserDatailList(searchText)
//    }
//
//    fun getSearchText(): String?{
//        return edit_search.text.toString()
//    }

    override fun onDestroy() {
        super.onDestroy()
        if (mUserPresenter == null) return
        mUserPresenter!!.dropView()
    }
}