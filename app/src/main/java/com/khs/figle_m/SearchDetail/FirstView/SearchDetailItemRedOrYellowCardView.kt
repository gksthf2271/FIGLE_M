package com.khs.figle_m.SearchDetail.FirstView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.db.williamchart.extensions.getDrawable
import com.khs.figle_m.R
import com.khs.figle_m.databinding.CviewCardBinding
import com.khs.figle_m.databinding.CviewDetailCardInfoBinding

class SearchDetailItemRedOrYellowCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private var mBinding : CviewDetailCardInfoBinding
    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = CviewDetailCardInfoBinding.inflate(inflater, this, true)
    }

    fun addLeftCard(redCount:Int, yellowCount: Int) {
        mBinding.groupLeft.apply {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            if (redCount == 0 && yellowCount == 0) {
                val textView: TextView = CviewDetailCardInfoBinding.inflate(inflater, this@apply, false).txtTitle
                addView(textView)
                return
            }
            for(i in 1..redCount) {
                val imageView: ImageView = CviewCardBinding.inflate(inflater, this@apply, false).imgCard
                imageView.background = getDrawable(R.mipmap.red)
                addView(imageView)
            }

            for(i in 1..yellowCount) {
                val imageView: ImageView = CviewCardBinding.inflate(inflater, this@apply, false).imgCard
                imageView.background = getDrawable(R.mipmap.yellow)
                addView(imageView)
            }
        }
    }

    fun addRightCard(redCount:Int, yellowCount:Int) {
        val rootView = findViewById<LinearLayout>(R.id.group_Right)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (redCount == 0 && yellowCount == 0) {
            val textView: TextView = inflater.inflate(R.layout.cview_detail_card_empty,rootView,false) as TextView
            rootView.addView(textView)
            return
        }
        for(i in 1..redCount) {
            val imageView: ImageView = inflater.inflate(R.layout.cview_card,rootView,false) as ImageView
            imageView.background = getDrawable(R.mipmap.red)
            rootView.addView(imageView)
        }

        for(i in 1..yellowCount) {
            val imageView: ImageView = inflater.inflate(R.layout.cview_card,rootView,false) as ImageView
            imageView.background = getDrawable(R.mipmap.yellow)
            rootView.addView(imageView)
        }
    }

    fun setTitleText(text:String) {
        findViewById<TextView>(R.id.txt_title).text = text
    }
}