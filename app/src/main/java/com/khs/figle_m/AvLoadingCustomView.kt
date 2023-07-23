package com.khs.figle_m

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.ContentLoadingProgressBar
import com.khs.figle_m.Utils.LogUtil
import kotlinx.android.synthetic.main.cview_loading_view.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AvLoadingCustomView: ConstraintLayout {
    val TAG = javaClass.simpleName
    val DEBUG: Boolean = false
    lateinit var view: View
    var mMax: Int = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    fun initView(context: Context) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"initView")
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.cview_loading_view, this)
    }

    fun backroundColorVisible(isVisible : Boolean) {
        if (isVisible) {
            root_view.background = context.getDrawable(R.color.loading_background)
        } else {
            root_view.background = context.getDrawable(R.color.search_loading_color)
        }
    }

    fun show(isStart: Boolean) {
        LogUtil.dLog(LogUtil.TAG_UI, TAG,"show LoadingView")
        CoroutineScope(Dispatchers.Main).launch {
            view.findViewById<ContentLoadingProgressBar>(R.id.loading_view).show()
        }

        if (isStart) {
            txt_data_based_on_nexon.visibility = View.VISIBLE
        } else {
            if (isShownLoadingView()) return
            txt_data_based_on_nexon.visibility = View.INVISIBLE
            txt_progress.visibility = View.INVISIBLE
            progress_horizontal.visibility = View.INVISIBLE
        }
    }

    fun hide(){
        LogUtil.dLog(LogUtil.TAG_UI, TAG,"hide LoadingView")
        CoroutineScope(Dispatchers.Main).launch {
            view.findViewById<ContentLoadingProgressBar>(R.id.loading_view).hide()
        }
    }

    fun setProgressMax(max:Int) {
        LogUtil.vLog(LogUtil.TAG_UI, TAG,"setProgressMax : $max")
        mMax = max
        progress_horizontal.max = mMax
    }

    fun updateProgress(progress: Int) {
        LogUtil.dLog(LogUtil.TAG_UI, TAG,"progress : $progress")
        CoroutineScope(Dispatchers.Main).launch {
            loading_view.visibility = View.INVISIBLE
            progress_horizontal.visibility = View.VISIBLE
            txt_progress.visibility = View.VISIBLE
            progress_horizontal.progress = progress
        }
    }

    fun isShownLoadingView() : Boolean{
        return view.let {
            loading_view.visibility == View.VISIBLE
        }
    }
}
