package com.example.figle_m.SearchList.SearchDetailView

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.ViewPager
import com.example.figle_m.R
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.databinding.FragmentSearchContainerBinding
import com.example.figle_m.utils.DisplayUtils

class SearchDetailDialogFragment : DialogFragment() {
    val TAG = javaClass.name

    open val KEY_MATCH_DETAIL_INFO = "KEY_MATCH_DETAIL_INFO"
    open val TAG_MATCH_DETAIL_DIALOG = "TAG_MATCH_DETAIL_DIALOG"

    lateinit var mMatchDetail: MatchDetailResponse

    companion object {
        @Volatile
        private var instance: SearchDetailDialogFragment? = null

        @JvmStatic
        fun getInstance(): SearchDetailDialogFragment =
            instance ?: synchronized(this) {
                instance
                    ?: SearchDetailDialogFragment().also {
                        instance = it
                    }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v(TAG,"onCreateView(...)")
        val v = inflater.inflate(R.layout.fragment_search_container, container,true)
        return v
    }

    override fun onResume() {
        super.onResume()
        context ?: return
        val size = DisplayUtils().getDisplaySize(context!!)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        val deviceeHeight = size.y
        params?.width = (deviceWidth * 0.9).toInt()
        params?.height = (deviceeHeight * 0.95).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    override fun onStart() {
        super.onStart()
        var viewPager = view!!.findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = SearchDetailDialogAdapter(context!!)
        arguments.let{
            mMatchDetail = arguments!!.getParcelable<MatchDetailResponse>(KEY_MATCH_DETAIL_INFO)!!
        }
        Log.v(TAG,"TEST : ${mMatchDetail.matchInfo}")
    }
}