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
import com.khs.data.nexon_api.response.DTO.PlayerDTO
import com.khs.data.nexon_api.response.DTO.RankerPlayerDTO
import com.khs.figle_m.R
import com.khs.figle_m.databinding.CviewRankerChartBinding
import kotlin.math.roundToInt


class RankerChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    val TAG = javaClass.simpleName
    lateinit var mBinding : CviewRankerChartBinding

    init {
        initView(context)
    }

    fun initView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = CviewRankerChartBinding.inflate(inflater, this, true)
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

        val rankerDataSet = LineDataSet(rankerEntries, "Ranker").apply {
            lineWidth = 2f
            circleRadius = 4f
            setCircleColor(resources.getColor(R.color.chart_ranker_color,null))
            fillColor = resources.getColor(R.color.chart_ranker_color_fill,null)
            color = resources.getColor(R.color.chart_ranker_color,null)
            setDrawCircleHole(true)
            setDrawCircles(true)
            setDrawHorizontalHighlightIndicator(false)
            setDrawHighlightIndicators(false)
            setDrawValues(true)
            setDrawFilled(true)
            valueTextSize = 11f
            valueTextColor = Color.WHITE
        }

        val playerDataSet = LineDataSet(playerEntries, "Player").apply {
            lineWidth = 2f
            circleRadius = 4f
            setCircleColor(resources.getColor(R.color.chart_player_color,null))
            color = resources.getColor(R.color.chart_player_color,null)
            fillColor = resources.getColor(R.color.chart_player_color_fill,null)
            setDrawCircleHole(true)
            setDrawCircles(true)
            setDrawHorizontalHighlightIndicator(false)
            setDrawHighlightIndicators(false)
            setDrawValues(true)
            setDrawFilled(true)
            valueTextSize = 11f
            valueTextColor = Color.WHITE
        }

        val lineData = LineData(rankerDataSet, playerDataSet)
        lineData.setValueTextColor(resources.getColor(R.color.search_text_color,null))
        lineData.setValueTextSize(9f)
        lineData.setDrawValues(false)   //값 표시 여부
        mBinding.chart.apply {
            data = lineData

            val xAxis = xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.textColor = Color.WHITE
            xAxis.enableGridDashedLine(8f, 24f, 0f)
            xAxis.valueFormatter = CustomVlaueFormatter(listOf<String>("","슈팅","유효슈팅","어시스트","골","패스시도","패스성공","태클","블락"))
            xAxis.isEnabled = true

            val yLAxis = axisLeft
            yLAxis.textColor = Color.WHITE

            val yRAxis = axisRight
            yRAxis.setDrawAxisLine(false)
            yRAxis.setDrawGridLines(false)

            val description = Description()
            description.text = ""

            val legend = mBinding.chart.legend //레전드 설정 (차트 밑에 색과 라벨을 나타내는 설정)
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM //하단 왼쪽에 설정
            legend.textColor = ContextCompat.getColor(context, R.color.search_text_color) // 레전드 컬러 설정

            isDoubleTapToZoomEnabled = false
            setDrawGridBackground(false)
            setDescription(description)
            description.isEnabled = false
            animateY(1500, Easing.EaseInCubic)
            invalidate()
        }
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
            val index = value.roundToInt()

            if (index < 0 || index >= mValueCount || index != value.toInt())
                return ""

            return mValues[index]
        }

        fun getValues(): List<String> {
            return mValues
        }

        private fun setValues(values: List<String>) {
            this.mValues = values
            this.mValueCount = values.size
        }
    }
}