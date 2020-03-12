//package com.example.figle_m.SearchList.SearchDetailView
//
//import android.app.Dialog
//import android.content.Context
//import android.content.DialogInterface
//import android.view.LayoutInflater
//import androidx.databinding.DataBindingUtil
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import androidx.fragment.app.FragmentStatePagerAdapter
//import com.example.figle_m.R
//import com.example.figle_m.databinding.FragmentSearchContainerBinding
//
//class SearchDetailDialogFragment(
//    context: Context,
//    cancelable: Boolean,
//    cancelListener: DialogInterface.OnCancelListener?
//) : Dialog(context, cancelable, cancelListener) {
//    var mDataBinding: FragmentSearchContainerBinding? = null
//    lateinit var mFM: FragmentManager
//    init {
//        initView()
//    }
//    fun initView(context: Context){
//        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val v = inflater.inflate(R.layout.cview_detail_info, null)
//        mDataBinding = DataBindingUtil.findBinding(v)
//    }
//
//    override fun onStart() {
//        super.onStart()
//        initView()
//    }
//
//    fun initView() {
//        // The pager adapter, which provides the pages to the view pager widget.
//        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
//        mDataBinding!!.viewPager.adapter = pagerAdapter
//    }
//
//    fun setFragmentManager(fm:FragmentManager){
//
//    }
//
//    /**
//     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
//     * sequence.
//     */
//    private inner class ScreenSlidePagerAdapter(fm:FragmentManager) : FragmentStatePagerAdapter(fm) {
//        override fun getCount(): Int = NUM_PAGES
//
//        override fun getItem(position: Int): Fragment = ScreenSlidePageFragment()
//    }
//}