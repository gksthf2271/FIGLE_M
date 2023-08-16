package com.khs.figle_m.lock

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.databinding.CviewLockBinding

class LockView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    val TAG = javaClass.simpleName
    lateinit var mBinding: CviewLockBinding
    init {
        initView(context)
    }

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = CviewLockBinding.inflate(inflater, this, true)
    }

    fun updateImageView(res:Int) {
        mBinding.imgLock.setImageDrawable(resources.getDrawable(res,null))
    }

    fun updateTextView(str:String) {
        mBinding.txtLock.text = str
    }
}