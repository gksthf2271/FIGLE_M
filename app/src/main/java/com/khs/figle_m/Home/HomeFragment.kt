package com.khs.figle_m.Home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.Toast
import com.khs.figle_m.Base.BaseFragment
import com.khs.figle_m.MainActivity
import com.khs.figle_m.R
import com.khs.figle_m.Ranking.RankingActivity
import com.khs.figle_m.Response.UserResponse
import com.khs.figle_m.SearchList.SearchHome.SearchHomeFragment
import com.khs.figle_m.Utils.FragmentUtils
import com.khs.figle_m.Utils.LogUtil
import com.khs.figle_m.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment(), UserContract.View, Handler.Callback {
    val TAG: String = javaClass.simpleName
    lateinit var mBinding: FragmentHomeBinding
    val MSG_SHOW_USER_LIST : Int = 0
    val MSG_SHOW_MATCH_DETAIL_LIST : Int = 1
    val MSG_SHOW_MATCH_ID_LIST : Int = 2
    val mHandler: Handler = Handler(this)

    val RESULT_REQUEST_CODE = 1000
    val KEY_SEARCH = "KEY_SEARCH"
    val KEY_SEARCH_TEAM_PRICE = "KEY_SEARCH_TEAM_PRICE"

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
                LogUtil.vLog(LogUtil.TAG_UI, TAG,"MSG_SHOW_USER_LIST result ::: " + msg.obj.toString())
                val searchHomeFragment = SearchHomeFragment()
                val bundle = Bundle()
                bundle.putParcelable(
                    SearchHomeFragment.getInstance().KEY_SEARCH_USER_INFO,
                    mUserResponse
                )
                searchHomeFragment.arguments = bundle
                FragmentUtils().loadFragment(
                    searchHomeFragment,
                    R.id.fragment_container, parentFragmentManager
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
            LogUtil.vLog(LogUtil.TAG_UI, TAG,"userResponse is null")
            return
        }
        val msgUserList:Message = Message()
        msgUserList.what = MSG_SHOW_USER_LIST
        msgUserList.obj = userResponse
        mHandler.sendMessage(msgUserList)
    }

    override fun showLoading() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showLoading(...)")
    }

    override fun hideLoading() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"hideLoading(...)")
    }

    override fun showError(error: Int) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showError(...) : $error")
        (activity as MainActivity).showErrorPopup(error)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initView()
        mBinding.btnRanking.setOnClickListener {
            val intent = Intent(context, RankingActivity::class.java)
            startActivityForResult(intent, RESULT_REQUEST_CODE)
        }
    }

    lateinit var mEditView :EditText
    lateinit var mCloseBtn : Button
    fun initView() {
        mUserPresenter.takeView(this)
        mBinding.editSearch.mBinding.editView
        mEditView = mBinding.editSearch.mBinding.editView
        mCloseBtn = mBinding.editSearch.mBinding.btnSearchReset
        mEditView.imeOptions = IME_ACTION_SEARCH

        mEditView.setOnEditorActionListener { view, actionId, keyEvent ->
            if (view.hasFocus() && actionId == EditorInfo.IME_ACTION_SEARCH) {
                //TODO hideKeywordHistoryView()
                val searchText = (view as EditText).text.toString()
                if (searchText.isNullOrEmpty()) {
                    Toast.makeText(context, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
                    return@setOnEditorActionListener false
                }
                search(mEditView.text.toString())
                view.clearFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        mEditView.addTextChangedListener(textWatcher)

//        btn_search.setOnClickListener(View.OnClickListener {
//            LogUtil.vLog(LogUtil.TAG_UI, TAG,"clickSearchBtn")
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

    private fun search(searchString: String, teamPrice : String) {
        mSearchString = searchString
        mUserPresenter.getUserDatailList(searchString, teamPrice)
    }

    private fun search(searchString: String) {
        search(searchString, "")
    }

    override fun onDestroy() {
        super.onDestroy()
        PopupWindow().dismiss()
        mUserPresenter.dropView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"onActivityResult(...) requestCode : ${requestCode} , resultCode : $resultCode , data : $data")
        when(requestCode) {
            RESULT_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data ?: return
                    search(data.getStringExtra(KEY_SEARCH)!!, data.getStringExtra(KEY_SEARCH_TEAM_PRICE)!!)
                } else if(resultCode == Activity.RESULT_CANCELED) {

                }
            }
        }
    }
}