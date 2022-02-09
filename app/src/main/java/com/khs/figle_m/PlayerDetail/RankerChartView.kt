package com.khs.figle_m.PlayerDetail

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.khs.figle_m.R
import com.khs.figle_m.Response.DTO.PlayerDTO
import com.khs.figle_m.Response.DTO.RankerPlayerDTO
import kotlinx.android.synthetic.main.cview_ranker_chart.view.*


class RankerChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    val TAG = javaClass.name

    init {
        initView(context)
    }

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cview_ranker_chart, this)
    }

    fun setData(playerDTO: PlayerDTO?, rankerPlayerDTO: RankerPlayerDTO?) {
        val rankerEntries = arrayListOf<Entry>()
        val playerEntries = arrayListOf<Entry>()

        val playerInfo = playerDTO as PlayerDTO
        val rankerInfo = rankerPlayerDTO as RankerPlayerDTO

        rankerEntries.add(Entry(1.00f, rankerInfo.status.shoot))
        rankerEntries.add(Entry(2.00f, rankerInfo.status.effectiveShoot))
        rankerEntries.add(Entry(3.00f, rankerInfo.status.assist))
        rankerEntries.add(Entry(4.00f, rankerInfo.status.goal))
        rankerEntries.add(Entry(5.00f, rankerInfo.status.passTry))
        rankerEntries.add(Entry(6.00f, rankerInfo.status.passSuccess))
        rankerEntries.add(Entry(7.00f, rankerInfo.status.tackle))
        rankerEntries.add(Entry(8.00f, rankerInfo.status.block))
//        rankerEntries.add(Entry(9.00f, rankerInfo.status.dribble))
        playerEntries.add(Entry(1.00f, playerInfo.status.shoot.toFloat()))
        playerEntries.add(Entry(2.00f, playerInfo.status.effectiveShoot.toFloat()))
        playerEntries.add(Entry(3.00f, playerInfo.status.assist.toFloat()))
        playerEntries.add(Entry(4.00f, playerInfo.status.goal.toFloat()))
        playerEntries.add(Entry(5.00f, playerInfo.status.passTry.toFloat()))
        playerEntries.add(Entry(6.00f, playerInfo.status.passSuccess.toFloat()))
        playerEntries.add(Entry(7.00f, playerInfo.status.tackle.toFloat()))
        playerEntries.add(Entry(8.00f, playerInfo.status.block.toFloat()))
//        playerEntries.add(Entry(9.00f, playerInfo.status.dribble.toFloat()))

        val rankerDataSet = LineDataSet(rankerEntries, "Ranker")
        val playerDataSet = LineDataSet(playerEntries, "Player")

        rankerDataSet.lineWidth = 2f
        rankerDataSet.circleRadius = 4f
        rankerDataSet.setCircleColor(resources.getColor(R.color.chart_ranker_color,null))
        rankerDataSet.fillColor = resources.getColor(R.color.chart_ranker_color_fill,null)
        rankerDataSet.color = resources.getColor(R.color.chart_ranker_color,null)
        rankerDataSet.setDrawCircleHole(true)
        rankerDataSet.setDrawCircles(true)
        rankerDataSet.setDrawHorizontalHighlightIndicator(false)
        rankerDataSet.setDrawHighlightIndicators(false)
        rankerDataSet.setDrawValues(true)
        rankerDataSet.setDrawFilled(true)
        rankerDataSet.setValueTextSize(11f)
        rankerDataSet.setValueTextColor(Color.WHITE)

        playerDataSet.lineWidth = 2f
        playerDataSet.circleRadius = 4f
        playerDataSet.setCircleColor(resources.getColor(R.color.chart_player_color,null))
        playerDataSet.color = resources.getColor(R.color.chart_player_color,null)
        playerDataSet.fillColor = resources.getColor(R.color.chart_player_color_fill,null)
        playerDataSet.setDrawCircleHole(true)
        playerDataSet.setDrawCircles(true)
        playerDataSet.setDrawHorizontalHighlightIndicator(false)
        playerDataSet.setDrawHighlightIndicators(false)
        playerDataSet.setDrawValues(true)
        playerDataSet.setDrawFilled(true)
        playerDataSet.setValueTextSize(11f)
        playerDataSet.setValueTextColor(Color.WHITE)

        val lineData = LineData(rankerDataSet, playerDataSet)
        lineData.setValueTextColor(resources.getColor(R.color.search_text_color,null))
        lineData.setValueTextSize(9f)
        lineData.setDrawValues(false)   //값 표시 여부
        chart.data = lineData

        val xAxis = chart.getXAxis()
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setTextColor(Color.WHITE)
        xAxis.enableGridDashedLine(8f, 24f, 0f)
        xAxis.setValueFormatter(CustomVlaueFormatter(listOf<String>("","슈팅","유효슈팅","어시스트","골","패스시도","패스성공","태클","블락")))
        xAxis.isEnabled = true

        val yLAxis = chart.getAxisLeft()
        yLAxis.setTextColor(Color.WHITE)

        val yRAxis = chart.getAxisRight()
        yRAxis.setDrawAxisLine(false)
        yRAxis.setDrawGridLines(false)

        val description = Description()
        description.setText("")

        val legend = chart.getLegend() //레전드 설정 (차트 밑에 색과 라벨을 나타내는 설정)
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM //하단 왼쪽에 설정
        legend.setTextColor(ContextCompat.getColor(context, R.color.search_text_color)) // 레전드 컬러 설정


        chart.setDoubleTapToZoomEnabled(false)
        chart.setDrawGridBackground(false)
        chart.setDescription(description)
        chart.description.isEnabled = false
        chart.animateY(1500, Easing.EaseInCubic)
        chart.invalidate()
    }

    inner class CustomVlaueFormatter : ValueFormatter {
        var mValues = listOf<String>()
        var mValueCount = 0

        constructor(){
        }

        constructor(values: List<String>) {
            if (values != null)
                setValues(values)
        }

        constructor(values : Collection<String>) {
            if (values != null)
                setValues(values.toList())
        }

        override fun getFormattedValue(value: Float): String {
            var index = Math.round(value)

            if (index < 0 || index >= mValueCount || index != value.toInt())
                return ""

            return mValues[index]
        }

        fun getValues(): List<String> {
            return mValues
        }

        fun setValues(values: List<String>) {
            this.mValues = values
            this.mValueCount = values.size
        }
    }
}