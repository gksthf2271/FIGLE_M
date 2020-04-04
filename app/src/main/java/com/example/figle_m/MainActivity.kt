package com.example.figle_m

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.figle_m.Base.BaseActivity
import com.example.figle_m.Home.HomeFragment
import com.example.figle_m.SearchList.SearchListFragment
import com.example.figle_m.utils.FragmentUtils
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody

class MainActivity : BaseActivity(), InitContract.View{
    val TAG: String = javaClass.name
    open val PREF_NAME = "playerNamePref"
    private lateinit var mInitPresenter: InitPresenter
    private lateinit var mPopupWindow: PopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        mInitPresenter!!.takeView(this)
        mInitPresenter.getPlayerNameList(applicationContext)
    }

    override fun onDestroy() {
        super.onDestroy()
        mInitPresenter!!.dropView()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getCurrentFragment() is SearchListFragment) {
                FragmentUtils().loadFragment(
                    HomeFragment(),
                    R.id.fragment_container,
                    supportFragmentManager
                )
            }
            if (getCurrentFragment() is HomeFragment) {
                showFinishPopup()
            }
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    fun getCurrentFragment(): Fragment {
        return FragmentUtils().currentFragment(supportFragmentManager!!, R.id.fragment_container)!!
    }

    override fun setProgressMax(max: Int) {
        avi_loading.setProgressMax(max)
    }

    override fun updateProgress(progress: Int) {
        avi_loading.updateProgress(progress)
    }

    override fun showLoading() {
        Log.v(TAG,"showLoading(...)")
        avi_loading.visibility = View.VISIBLE
        fragment_container.visibility = View.GONE
        avi_loading.show(true)
    }

    override fun hideLoading() {
        Log.v(TAG,"hideLoading(...)")
        avi_loading.hide()
        avi_loading.visibility = View.GONE
        fragment_container.visibility = View.VISIBLE
    }

    override fun showMainActivity(responseBody: ResponseBody) {
        Log.v(TAG,"showMainActivity(...)")
        val fm: FragmentManager = this.supportFragmentManager
        val homeFragment: HomeFragment = HomeFragment.getInstance()
        FragmentUtils().loadFragment(homeFragment, R.id.fragment_container,fm)
    }

    override fun showError(error: String) {
        Log.v(TAG,"showError : $error")
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
        mPopupWindow.setFocusable(true)
        mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)


        val cancel = popupView.findViewById(R.id.Cancel) as Button
        cancel.setOnClickListener { mPopupWindow.dismiss() }

        val ok = popupView.findViewById(R.id.Ok) as Button
        ok.setOnClickListener { finish() }
    }
}
