package com.khs.figle_m.Home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import com.khs.figle_m.Base.BaseFragment
import com.khs.figle_m.MainActivity
import com.khs.figle_m.R
import com.khs.figle_m.Response.UserResponse
import com.khs.figle_m.SearchList.SearchListFragment
import com.khs.figle_m.utils.FragmentUtils
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(), UserContract.View, Handler.Callback {
    val TAG: String = javaClass.name

    val MSG_SHOW_USER_LIST : Int = 0
    val MSG_SHOW_MATCH_DETAIL_LIST : Int = 1
    val MSG_SHOW_MATCH_ID_LIST : Int = 2
    val mHandler: Handler = Handler(this)

    lateinit var mUserPresenter: UserPresenter

    lateinit var mSearchString: String
    lateinit var mUserResponse: UserResponse

    override fun initPresenter() {
        mUserPresenter = UserPresenter()
    }

    companion object {
        @Volatile
        private var instance: HomeFragment? = null

        @JvmStatic
        fun getInstance(): HomeFragment =
            instance ?: synchronized(this) {
                instance
                    ?: HomeFragment().also {
                    instance = it
                }
            }
    }

    override fun onResume() {
        super.onResume()
        mEditView ?: return
        mEditView.text = null
    }

    override fun handleMessage(msg: Message): Boolean {
        when (msg.what) {
            MSG_SHOW_USER_LIST -> {
                mUserResponse = msg.obj as (UserResponse)
                Log.v(TAG, "MSG_SHOW_USER_LIST result ::: " + msg.obj.toString())

                mUserResponse.accessId ?: return false
                fragmentManager ?: return false

                val searchListFragment = SearchListFragment()
                val bundle = Bundle()
                bundle.putParcelable(
                    SearchListFragment.getInstance().KEY_SEARCH_USER_INFO,
                    mUserResponse
                )
                searchListFragment.arguments = bundle
                FragmentUtils().loadFragment(
                    searchListFragment,
                    R.id.fragment_container, fragmentManager!!
                )
                return true
            }
//            MSG_SHOW_MATCH_ID_LIST -> {
//                fragmentManager ?: return false
//                val responseBody: ResponseBody = msg.obj as ResponseBody
//                var result:String = responseBody.string()
//                val stringList:List<String> = result.removeSurrounding("[","]").replace("\"","").split(",")
//
//                val searchListFragment = SearchListFragment()
//                val bundle = Bundle()
//                bundle.putStringArray(SearchListFragment.getInstance().KEY_MATCH_ID_LIST,stringList.toTypedArray())
//                bundle.putParcelable(SearchListFragment.getInstance().KEY_SEARCH_USER_INFO, mUserResponse)
//                searchListFragment.arguments = bundle
//                FragmentUtils().loadFragment(searchListFragment,
//                    R.id.fragment_container, fragmentManager!!)
//                return true
//            }
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

    override fun showLoading() {
        Log.v(TAG,"showLoading(...)")
    }

    override fun hideLoading() {
        Log.v(TAG,"hideLoading(...)")
    }

    override fun showError(error: Int) {
        Log.v(TAG,"showError(...) : $error")
        (activity as MainActivity).showErrorPopup(error)
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

    lateinit var mEditView :EditText
    lateinit var mCloseBtn : Button
    fun initView() {
        if (mUserPresenter == null) return
        mUserPresenter!!.takeView(this)
        mEditView = edit_search.findViewById<EditText>(R.id.edit_view)
        mCloseBtn = edit_search.findViewById<Button>(R.id.btn_search_reset)
        mEditView.imeOptions = IME_ACTION_SEARCH

        mEditView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                search(mEditView.text.toString())
             return@OnKeyListener true
            }
            false
        })

        mEditView.addTextChangedListener(textWatcher)

//        btn_search.setOnClickListener(View.OnClickListener {
//            Log.v(TAG,"clickSearchBtn")
//            search(mEditView.text.toString())
//        })
    }

    private val textWatcher = object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s == null || "".equals(s) || (s != null && s.isEmpty())) {
                mCloseBtn.visibility = View.INVISIBLE
            } else {
                mCloseBtn.visibility = View.VISIBLE
                mCloseBtn.setOnClickListener {
                    mEditView.text = null
                }
            }
        }
    }

    fun search(searchString: String) {
        mSearchString = searchString
        mUserPresenter!!.getUserDatailList(searchString)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mUserPresenter == null) return
        PopupWindow().dismiss()
        mUserPresenter!!.dropView()
    }
}