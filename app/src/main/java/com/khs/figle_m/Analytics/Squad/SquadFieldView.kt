package com.khs.figle_m.Analytics.Squad

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.khs.figle_m.Base.BaseActivity
import com.khs.figle_m.R
import com.khs.figle_m.Utils.DisplayUtils
import kotlinx.android.synthetic.main.cview_field.view.*

class SquadFieldView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    val TAG: String = javaClass.simpleName

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_field, this)
    }

    fun setFormation(formationData: FormationData) {

    }

    fun initLayoutRatioReset(x: Float, y: Float, width: Float, height: Float) {
        val targetViewId = R.id.group_field
        val cs = ConstraintSet()
        cs.clone(group_field)

        val size = DisplayUtils().getDisplaySize(context!!)
        val deviceWidth = size.x
        val deviceHeight = size.y

        cs.setHorizontalBias(targetViewId, x / (deviceWidth - width))
        cs.setVerticalBias(targetViewId, y / (deviceHeight - height))
        cs.constrainPercentWidth(targetViewId, width / deviceWidth)
        cs.constrainPercentHeight(targetViewId, height / deviceHeight)
        cs.applyTo(group_field)
    }

}