package com.khs.figle_m

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.utils.LogUtil
import com.khs.figle_m.databinding.CviewLoadingViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AvLoadingCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    val TAG = javaClass.simpleName
    lateinit var mBinding: CviewLoadingViewBinding
    val DEBUG: Boolean = false
    var mMax: Int = 0

    init {
        initView(context)
    }

    fun initView(context: Context) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"initView")
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = CviewLoadingViewBinding.inflate(inflater, this, true)
    }

    fun backgroundColorVisible(isVisible : Boolean) {
        if (isVisible) {
            mBinding.rootView.background = context.getDrawable(R.color.loading_background)
        } else {
            mBinding.rootView.background = context.getDrawable(R.color.search_loading_color)
        }
    }

    fun show(isStart: Boolean) {
        LogUtil.dLog(LogUtil.TAG_UI, TAG,"show LoadingView")
        CoroutineScope(Dispatchers.Main).launch {
            mBinding.loadingView.show()
        }

        if (isStart) {
            mBinding.txtDataBasedOnNexon.visibility = View.VISIBLE
        } else {
            if (isShownLoadingView()) return
            mBinding.txtDataBasedOnNexon.visibility = View.INVISIBLE
            mBinding.txtProgress.visibility = View.INVISIBLE
            mBinding.progressHorizontal.visibility = View.INVISIBLE
        }
    }

    fun hide(){
        LogUtil.dLog(LogUtil.TAG_UI, TAG,"hide LoadingView")
        CoroutineScope(Dispatchers.Main).launch {
            mBinding.loadingView.hide()
        }
    }

    fun setProgressMax(max:Int) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"setProgressMax : $max")
        mMax = max
        mBinding.progressHorizontal.max = mMax
    }

    fun updateProgress(progress: Int) {
        LogUtil.dLog(LogUtil.TAG_UI, TAG,"progress : $progress")
        CoroutineScope(Dispatchers.Main).launch {
            mBinding.loadingView.visibility = View.INVISIBLE
            mBinding.progressHorizontal.visibility = View.VISIBLE
            mBinding.txtProgress.visibility = View.VISIBLE
            mBinding.progressHorizontal.progress = progress
        }
    }

    fun isShownLoadingView() : Boolean{
        return mBinding.loadingView.visibility == View.VISIBLE
    }
}
