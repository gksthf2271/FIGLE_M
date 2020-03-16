package com.example.figle_m.SearchList.SearchDetailView

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.ViewPager
import com.example.figle_m.R
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.utils.DisplayUtils

class SearchDetailDialogFragment : DialogFragment() {
    val TAG = javaClass.name

    open val KEY_MATCH_DETAIL_INFO = "KEY_MATCH_DETAIL_INFO"
    open val KEY_SEARCH_ACCESSID = "KEY_SEARCH_ACCESSID"
    open val TAG_MATCH_DETAIL_DIALOG = "TAG_MATCH_DETAIL_DIALOG"

    lateinit var mMatchDetail: MatchDetailResponse
    lateinit var mSearchAccessId: String

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
        resizeDialog()
        setBackgroundColorDialog()
    }

    override fun onStart() {
        super.onStart()
        var viewPager = view!!.findViewById<ViewPager>(R.id.viewPager)
        val btnClose = view!!.findViewById<Button>(R.id.btn_close)
        btnClose.setOnClickListener {
            dismiss()
        }
        arguments.let{
            mMatchDetail = arguments!!.getParcelable<MatchDetailResponse>(KEY_MATCH_DETAIL_INFO)!!
            mSearchAccessId = arguments!!.getString(KEY_SEARCH_ACCESSID)!!
            viewPager.adapter = SearchDetailDialogAdapter(context!!, mMatchDetail)
            viewPager.currentItem = 0
        }
    }

    fun resizeDialog(){
        context ?: return
        val size = DisplayUtils().getDisplaySize(context!!)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        val deviceeHeight = size.y
        params?.width = (deviceWidth * 0.9).toInt()
        params?.height = (deviceeHeight * 0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    fun setBackgroundColorDialog() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
    }
}