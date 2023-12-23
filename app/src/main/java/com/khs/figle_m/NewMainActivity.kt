package com.khs.figle_m

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.khs.figle_m.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewMainActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName
    lateinit var mBinding: ActivityMainBinding
    private val mMainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        mMainViewModel.updateSeasonDB()
        mMainViewModel.updatePlayerDB()
    }
}