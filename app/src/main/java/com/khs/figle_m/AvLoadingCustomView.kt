package com.khs.figle_m

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.wang.avi.AVLoadingIndicatorView
import kotlinx.android.synthetic.main.cview_loading_view.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AvLoadingCustomView: ConstraintLayout {
    val TAG = javaClass.name
    lateinit var view: View
    var mMax: Int = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    fun initView(context: Context) {
        Log.v(TAG,"initView")
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.cview_loading_view, this)
    }

    fun show(isStart: Boolean) {
        Log.v(TAG,"show LoadingView")
        CoroutineScope(Dispatchers.Main).launch {
            view.findViewById<AVLoadingIndicatorView>(R.id.loading_view).smoothToShow()
        }

        if (isStart) {
            txt_data_based_on_nexon.visibility = View.VISIBLE
        } else {
            txt_data_based_on_nexon.visibility = View.INVISIBLE
            txt_progress.visibility = View.INVISIBLE
            progress_horizontal.visibility = View.INVISIBLE
        }
    }

    fun hide(){
        Log.v(TAG,"hide LoadingView")
        CoroutineScope(Dispatchers.Main).launch {
            view.findViewById<AVLoadingIndicatorView>(R.id.loading_view).smoothToHide()
        }
    }

    fun setProgressMax(max:Int) {
        Log.v(TAG,"setProgressMax : $max")
        mMax = max
        progress_horizontal.max = mMax
    }

    fun updateProgress(progress: Int) {
        Log.v(TAG,"progress : $progress")
        CoroutineScope(Dispatchers.Main).launch {
            loading_view.visibility = View.INVISIBLE
            progress_horizontal.visibility = View.VISIBLE
            txt_progress.visibility = View.VISIBLE
            progress_horizontal.progress = progress
        }
    }
}
