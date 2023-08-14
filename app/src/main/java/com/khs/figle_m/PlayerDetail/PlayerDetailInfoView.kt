package com.khs.figle_m.PlayerDetail

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.databinding.CviewPlayerDetailBottomBinding

class PlayerDetailInfoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    val TAG = javaClass.simpleName
    lateinit var mBinding : CviewPlayerDetailBottomBinding

    init {
        initView(context)
    }

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = CviewPlayerDetailBottomBinding.inflate(inflater, this, true)
    }

    fun setTitleList(titleList: List<String>) {
        if (titleList.size != 4) return
        mBinding.txtTitle1.text = titleList[0]
        mBinding.txtTitle2.text = titleList[1]
        mBinding.txtTitle3.text = titleList[2]
        mBinding.txtTitle4.text = titleList[3]
    }

    fun setDataList(dataList: List<String>) {
        if (dataList.size != 4) return
        mBinding.txtValue1.text = dataList[0]
        mBinding.txtValue2.text = dataList[1]
        mBinding.txtValue3.text = dataList[2]
        mBinding.txtValue4.text = dataList[3]
    }

    fun setValueTextSize(size:Float) {
        mBinding.txtValue1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)
        mBinding.txtValue2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)
        mBinding.txtValue3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)
        mBinding.txtValue4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)

    }

    fun setTitleTextSize(size:Float) {
        mBinding.txtTitle1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)
        mBinding.txtTitle2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)
        mBinding.txtTitle3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)
        mBinding.txtTitle4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)
    }
}