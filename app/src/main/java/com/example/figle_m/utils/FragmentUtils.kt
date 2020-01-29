package com.example.figle_m.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.annotation.NonNull



open class FragmentUtils{

    constructor()

    open fun loadFragment(fragment: Fragment, container_id:Int, fragmentManager: FragmentManager) {
        val className: String = fragment.javaClass.name
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(container_id, fragment, className)
        fragmentTransaction.commit()
    }

    open fun currentFragment(fragmentManager: FragmentManager, container_id: Int): Fragment? {
        return fragmentManager.findFragmentById(container_id)
    }

}