//package com.khs.figle_m.Analytics
//
//import android.content.Context
//import android.util.AttributeSet
//import android.view.LayoutInflater
//import androidx.constraintlayout.widget.ConstraintLayout
//import com.khs.figle_m.R
//import com.khs.figle_m.Response.DTO.MatchInfoDTO
//import com.khs.figle_m.Response.DTO.PlayerDTO
//
//class FiledView : ConstraintLayout, AnalyticsContract.View{
//    val TAG: String = javaClass.name
//
//    constructor(context: Context) : this(context, null)
//    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
//    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
//        initView(context)
//    }
//
//    fun initView(context: Context) {
//        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        inflater.inflate(R.layout.cview_field, this)
//    }
//
//    fun setMatchList(matchList:List<MatchInfoDTO>){
//        matchList.let {
//
//        }
//    }
//
//    override fun showLoading() {
//    }
//
//    override fun hideLoading(isError: Boolean) {
//    }
//
//    override fun showPlayerList(playerList: List<PlayerDTO>) {
//    }
//
//    override fun showError(error: Int) {
//    }
//}