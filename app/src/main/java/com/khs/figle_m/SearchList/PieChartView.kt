package com.khs.figle_m.SearchList

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.khs.figle_m.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF

class PieChartView : ConstraintLayout{
    val TAG = javaClass.name

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    val colorList = intArrayOf(
        resources.getColor(R.color.search_list_win,null),
        resources.getColor(R.color.search_list_draw,null),
        resources.getColor(R.color.search_list_lose,null)
    )


    lateinit var mChartView:PieChart

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_pie_chart, this)
        initPie()
    }

    fun initPie() {
        mChartView = findViewById(R.id.pie_chart)
        mChartView.setUsePercentValues(true)
        mChartView.setExtraOffsets(5f, 10f, 5f, 5f)

        mChartView.setDragDecelerationFrictionCoef(0.95f)
        mChartView.getDescription().setEnabled(false)

        mChartView.setDrawHoleEnabled(true)
        mChartView.setHoleColor(Color.WHITE)

        mChartView.setCenterTextSize(10f)
        mChartView.setTransparentCircleColor(Color.WHITE)
        mChartView.setTransparentCircleAlpha(50)

        mChartView.setHoleRadius(38f)
        mChartView.setTransparentCircleRadius(41f)

        mChartView.setDrawCenterText(true)

        mChartView.setRotationAngle(0f)
        mChartView.setRotationEnabled(true)
        mChartView.setHighlightPerTapEnabled(true)

        mChartView.setDrawEntryLabels(true)

        mChartView.animateY(1400, Easing.EaseInOutQuad)

        val l = mChartView.getLegend()
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        l.setOrientation(Legend.LegendOrientation.VERTICAL)
        l.setDrawInside(false)
        l.setEnabled(false)

        mChartView.setEntryLabelColor(Color.WHITE)
        mChartView.setEntryLabelTextSize(12f)
    }

    fun setData(pieEntryList : ArrayList<PieEntry>, gameCount: Int) {
        val dataSet = PieDataSet(pieEntryList,"")

        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(50f, 80f)
        dataSet.selectionShift = 5f

        // add a lot of colors

        val colors = ArrayList<Int>()

        for (c in colorList)
            colors.add(c)

        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(mChartView))
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        mChartView.setData(data)
        mChartView.highlightValues(null)
        mChartView.animateY(1000, Easing.EaseInCubic)
        mChartView.setCenterText("최근 ${gameCount}전\n승률")
        mChartView.invalidate()
    }


}