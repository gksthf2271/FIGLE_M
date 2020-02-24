package com.example.figle_m

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.figle_m.Adpater.SearchListAdapter
import com.example.figle_m.Response.MatchDetailResponse
import com.example.figle_m.View.UserPresenter
import com.example.figle_m.databinding.FragmentSearchlistBinding
import com.google.gson.Gson
import org.json.JSONObject

class SearchListFragment : BaseFragment() {
    val TAG: String = javaClass.name

    lateinit var mUserPresenter: UserPresenter
    var mDataBinding: FragmentSearchlistBinding? = null
    var mSearchResponseList: MutableList<MatchDetailResponse>? = null

    open val KEY_MATCH_DETAIL_LIST: String = "KEY_MATCH_DETAIL_LIST"
    override fun initPresenter() {
        mUserPresenter = UserPresenter()
    }

    companion object {
        @Volatile
        private var instance: SearchListFragment? = null

        @JvmStatic
        fun getInstance(): SearchListFragment =
            instance ?: synchronized(this) {
                instance ?: SearchListFragment().also {
                    instance = it
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_searchlist, container, false)
        mDataBinding = DataBindingUtil.getBinding<FragmentSearchlistBinding>(v)
        return v
    }

    override fun onStart() {
        super.onStart()
        arguments.let {
            JSONObject(arguments!!.getString(KEY_MATCH_DETAIL_LIST))
//            mSearchResponseList = mutableListOf(JSONObject(arguments!!.getString(KEY_MATCH_DETAIL_LIST)))
        }
        mDataBinding!!.layoutRecyclerview.adapter = SearchListAdapter(context, mSearchResponseList)
    }

    override fun onResume() {
        super.onResume()
    }

}