package com.khs.figle_m

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
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
import com.khs.figle_m.base.BaseActivity
import com.khs.figle_m.data.DataManager
import com.khs.figle_m.databinding.ActivityMainBinding
import com.khs.figle_m.databinding.ActivityMainFinishBinding
import com.khs.figle_m.home.HomeFragment
import com.khs.figle_m.searchList.SearchHome.SearchHomeFragment
import com.khs.figle_m.utils.FragmentUtils
import com.khs.figle_m.utils.LogUtil
import com.khs.figle_m.utils.SeasonManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody


@AndroidEntryPoint
class MainActivity : BaseActivity(), InitContract.View{
    val TAG: String = javaClass.simpleName
    lateinit var mBinding: ActivityMainBinding
    val PREF_NAME = "playerNamePref"
    lateinit var mInitPresenter: InitPresenter
    private var mPopupWindow: PopupWindow? = null
    private val MSG_DISCONNECTED_NETWORK = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"is Restart app? $isRestartApp")
        if (!isRestartApp) {
            mInitPresenter.takeView(this)
            val updateJob = CoroutineScope(Dispatchers.Default).launch {
                val seasonDataUpdateJob = launch {
                    mInitPresenter.getSeasonIdList(this@MainActivity)
                }
                val playerNameUpdateJob = launch {
                    mInitPresenter.getPlayerNameList(this@MainActivity)
                }
                val seasonUpdateJob = launch {
                    SeasonManager.updateSeason(this@MainActivity)
                }

                playerNameUpdateJob.join()
                seasonDataUpdateJob.join()
                seasonUpdateJob.join()
            }
            runBlocking {
                updateJob.join()
                LogUtil.vLog(LogUtil.TAG_UI, TAG,"시즌 업데이트 종료!")
                showMainActivity(null)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mBinding.txtDisconnectedNetwork.visibility = View.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"onDestroy(...)")
        mInitPresenter.dropView()
    }

    override fun onPause() {
        super.onPause()
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

    private fun getCurrentFragment(): Fragment? {
        return FragmentUtils().currentFragment(supportFragmentManager, R.id.fragment_container)
    }

    override fun showNetworkError() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showNetworkError(...)")
        mBinding.txtDisconnectedNetwork.visibility = View.VISIBLE
    }

    override fun setProgressMax(max: Int) {
        mBinding.aviLoading.setProgressMax(max)
    }

    override fun updateProgress(progress: Int) {
        mBinding.aviLoading.updateProgress(progress)
    }

    override fun showLoading() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showLoading(...)")
        mBinding.aviLoading.visibility = View.VISIBLE
        mBinding.fragmentContainer.visibility = View.GONE
        mBinding.aviLoading.show(true)
    }

    override fun hideLoading() {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"hideLoading(...)")
        mBinding.aviLoading.hide()
        mBinding.aviLoading.visibility = View.GONE
        mBinding.fragmentContainer.visibility = View.VISIBLE
    }

    override fun showMainActivity(responseBody: ResponseBody?) {
        CoroutineScope(Dispatchers.Main).launch {
            LogUtil.vLog(LogUtil.TAG_UI, TAG, "showMainActivity(...)")
            val fm: FragmentManager = this@MainActivity.supportFragmentManager
            val homeFragment: HomeFragment = HomeFragment.getInstance()
            FragmentUtils().loadFragment(homeFragment, R.id.fragment_container, fm)
        }
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
        mPopupWindow?.let { popupWindow ->
            popupWindow.isFocusable = true
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)


            val cancel = popupView.findViewById(R.id.Cancel) as Button
            cancel.setOnClickListener { popupWindow.dismiss() }

            val ok = popupView.findViewById(R.id.Ok) as Button
            ok.setOnClickListener {
                popupWindow.dismiss()
                finish()
            }
        }
    }

    fun showErrorPopup(error: Int) {
        showErrorPopup(error, true)
    }

    fun showErrorPopup(error: Int, isFinish:Boolean) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"showErrorPopup(...) :$error , isDestroyed : $isDestroyed")
        if (!this.window.isActive || isDestroyed) return
        when (error) {
            DataManager.ERROR_NETWORK_DISCONNECTED -> {
                showNetworkErrorPopup()
            }
            DataManager.ERROR_NOT_FOUND,
            DataManager.ERROR_BAD_REQUEST -> {
                showBadRequestPopup()
            }
        }
    }

    private fun showBadRequestPopup() {
        if (isDestroyed) return
        CoroutineScope(Dispatchers.Main).launch {
            val popupBinding = ActivityMainFinishBinding.inflate(layoutInflater)
            val popupView = popupBinding.root
            mPopupWindow = PopupWindow(
                popupView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            mPopupWindow?.let { popupWindow ->
                popupWindow.isFocusable = true
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)

                val textView = popupView.findViewById<TextView>(R.id.txt_title)
                textView.text = "구단주명을 확인해주세요."

                val cancel = popupView.findViewById(R.id.Cancel) as Button
                cancel.visibility = View.GONE

                val ok = popupView.findViewById(R.id.Ok) as Button
                ok.setOnClickListener { popupWindow.dismiss() }
            }
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
