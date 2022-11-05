package com.khs.figle_m.Lock

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.R
import kotlinx.android.synthetic.main.cview_lock.view.*

class LockView  : ConstraintLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    val TAG = javaClass.simpleName

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_lock, this)
    }

    fun updateImageView(res:Int) {
        img_lock.setImageDrawable(resources.getDrawable(res,null))
    }

    fun updateTextView(str:String) {
        txt_lock.text = str
    }
}