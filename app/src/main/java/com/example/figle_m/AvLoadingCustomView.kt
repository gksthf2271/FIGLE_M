package com.example.figle_m

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.wang.avi.AVLoadingIndicatorView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AvLoadingCustomView: ConstraintLayout {
    val TAG = javaClass.name
    lateinit var view: View
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

    fun show() {
        Log.v(TAG,"show LoadingView")
        CoroutineScope(Dispatchers.Main).launch {
            view.findViewById<AVLoadingIndicatorView>(R.id.loading_view).smoothToShow()
        }
    }

    fun hide(){
        Log.v(TAG,"hide LoadingView")
        CoroutineScope(Dispatchers.Main).launch {
            view.findViewById<AVLoadingIndicatorView>(R.id.loading_view).smoothToHide()
        }
    }
}
