package com.khs.figle_m

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.PersistableBundle
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.khs.figle_m.Base.BaseActivity
import com.khs.figle_m.Data.DataManager
import com.khs.figle_m.Home.HomeFragment
import com.khs.figle_m.SearchList.SearchHome.SearchHomeFragment
import com.khs.figle_m.SearchList.SearchListFragment
import com.khs.figle_m.Utils.FragmentUtils
import com.khs.figle_m.Utils.LogUtil
import com.khs.figle_m.Utils.SeasonManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody


class MainActivity : BaseActivity(), InitContract.View, Handler.Callback{
    val TAG: String = javaClass.simpleName
    val PREF_NAME = "playerNamePref"
    lateinit var mInitPresenter: InitPresenter
    private var mPopupWindow: PopupWindow? = null

    private val MSG_DISCONNECTED_NETWORK = 0
    private val mHandler:Handler = Handler(this)

    companion object {
        @Volatile
        private var instance: MainActivity? = null

        @JvmStatic
        fun getInstance(): MainActivity =
            instance ?: synchronized(this) {
                instance
                    ?: MainActivity().also {
                        instance = it
                    }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"is Restart app? $isRestartApp")
        if (!isRestartApp) {
            mInitPresenter.takeView(this)
            mInitPresenter.getSeasonIdList(this)
            mInitPresenter.getPlayerNameList(this)
            CoroutineScope(Dispatchers.Default).launch {
                SeasonManager.updateSeason(this@MainActivity)
            }
        }
        DataManager.getInstance().init(this)
    }

    override fun onResume() {
        super.onResume()
        txt_disconnected_network.visibility = View.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"onDestroy(...)")
        mInitPresenter.dropView()
    }

    override fun onPause() {
        super.onPause()
        DataManager.getInstance().init(null)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val currentFragment = getCurrentFragment()
            currentFragment ?: return super.onKeyDown(keyCode, event)

            when (currentFragment) {
                is SearchHomeFragment -> {
                    FragmentUtils().loadFragment(HomeFragment(), R.id.fragment_container, supportFragmentManager)
                }
                is HomeFragment -> {
                    showFinishPopup()
                }
                else -> {
                    supportFragmentManager.popBackStack()
                }
            }
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun handleMessage(msg: Message): Boolean {
        when(msg.what) {
            MSG_DISCONNECTED_NETWORK -> {
                LogUtil.vLog(LogUtil.TAG_UI, TAG,"received MSG_DISCONNECTED NETWORK!")
                if (mPopupWindow != null && mPopupWindow!!.isShowing) {
                    mPopupWindow!!.dismiss()
                }
                finish()
            }
        }
        return false
    }

    private fun getCurrentFragment(): Fragment? {
        return FragmentUtils().currentFragment(supportFragmentManager, R.id.fragment_container)
    }

    override fun showNetworkError() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showNetworkError(...)")
        txt_disconnected_network.visibility = View.VISIBLE
//        mHandler.sendEmptyMessageDelayed(MSG_DISCONNECTED_NETWORK, 3000)
    }

    override fun setProgressMax(max: Int) {
        avi_loading.setProgressMax(max)
    }

    override fun updateProgress(progress: Int) {
        avi_loading.updateProgress(progress)
    }

    override fun showLoading() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showLoading(...)")
        avi_loading.visibility = View.VISIBLE
        fragment_container.visibility = View.GONE
        avi_loading.show(true)
    }

    override fun hideLoading() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"hideLoading(...)")
        avi_loading.hide()
        avi_loading.visibility = View.GONE
        fragment_container.visibility = View.VISIBLE
    }

    override fun showMainActivity(responseBody: ResponseBody) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showMainActivity(...)")
        val fm: FragmentManager = this.supportFragmentManager
        val homeFragment: HomeFragment = HomeFragment.getInstance()
        FragmentUtils().loadFragment(homeFragment, R.id.fragment_container,fm)
    }

    override fun showError(error: Int) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showError : $error")
        showErrorPopup(error)
    }

    override fun initPresenter() {
        mInitPresenter = InitPresenter()
    }

    private fun showFinishPopup() {
        val popupView = layoutInflater.inflate(R.layout.activity_main_finish, null)
        mPopupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        mPopupWindow!!.setFocusable(true)
        mPopupWindow!!.showAtLocation(popupView, Gravity.CENTER, 0, 0)


        val cancel = popupView.findViewById(R.id.Cancel) as Button
        cancel.setOnClickListener { mPopupWindow!!.dismiss() }

        val ok = popupView.findViewById(R.id.Ok) as Button
        ok.setOnClickListener {
            mPopupWindow!!.dismiss()
            finish()
        }
    }

    fun showErrorPopup(error: Int) {
        showErrorPopup(error, true)
    }

    fun showErrorPopup(error: Int, isFinish:Boolean) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showErrorPopup(...) :$error , isDestroyed : $isDestroyed")
        if (!this.window.isActive || isDestroyed) return
        when (error) {
            DataManager().ERROR_NETWORK_DISCONNECTED -> {
//                if (isFinish) mHandler.sendEmptyMessageDelayed(MSG_DISCONNECTED_NETWORK, 3000)
                showNetworkErrorPopup()
            }
            DataManager().ERROR_NOT_FOUND,
            DataManager().ERROR_BAD_REQUEST -> {
                showBadRequestPopup()
            }
        }
    }

    private fun showBadRequestPopup() {
        if (isDestroyed) return
        CoroutineScope(Dispatchers.Main).launch {
            val popupView = layoutInflater.inflate(R.layout.activity_main_finish, null)
            mPopupWindow = PopupWindow(
                popupView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            mPopupWindow!!.setFocusable(true)
            mPopupWindow!!.showAtLocation(popupView, Gravity.CENTER, 0, 0)

            val textView = popupView.findViewById<TextView>(R.id.txt_title)
            textView.text = "구단주명을 확인해주세요."

            val cancel = popupView.findViewById(R.id.Cancel) as Button
            cancel.visibility = View.GONE

            val ok = popupView.findViewById(R.id.Ok) as Button
            ok.setOnClickListener { mPopupWindow!!.dismiss() }
        }
    }

    private fun showNetworkErrorPopup() {
        if (mPopupWindow != null && mPopupWindow!!.isShowing) return
        if (!this.window.isActive || this.isFinishing || isDestroyed) return
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showNetworkErrorPopup(...)")
        CoroutineScope(Dispatchers.Main).launch {
            val popupView = layoutInflater.inflate(R.layout.cview_network_error, null)
            val popupWindow = PopupWindow(
                popupView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            ).apply {
                isFocusable = true
                showAtLocation(popupView, Gravity.CENTER, 0, 0)
            }

            val textView = popupView.findViewById<TextView>(R.id.txt_title).apply {
                text = "네트워크 상태를 확인해주세요."
            }

            val cancel = popupView.findViewById(R.id.btn_setting) as Button
            cancel.setOnClickListener {
                popupWindow.dismiss()
                val intent = Intent(Settings.ACTION_SETTINGS)
                startActivityForResult(intent, 0)
            }

            val ok = popupView.findViewById(R.id.btn_finish) as Button
            ok.setOnClickListener {
                popupWindow.dismiss()
                finishAffinity()
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                System.exit(0)
            }
        }
    }

    fun firebaseCrashlyticsTest() {
        val crashButton = Button(this)
        crashButton.text = "Test Crash"
        crashButton.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }

        addContentView(crashButton, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))
    }


//    fun restartApp(context: Context) {
//        val i = context.packageManager
//            .getLaunchIntentForPackage(baseContext.packageName)
//        i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        startActivity(i)
//
//    }
}
