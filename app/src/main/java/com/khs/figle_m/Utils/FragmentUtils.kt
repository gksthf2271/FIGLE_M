package com.khs.figle_m.Utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class FragmentUtils{

    constructor()

    fun loadFragment(fragment: Fragment, container_id:Int, fragmentManager: FragmentManager) {
        loadFragment(fragment, container_id, fragmentManager, false)
    }

    fun loadFragment(fragment: Fragment, container_id:Int, fragmentManager: FragmentManager, isAdded: Boolean) {
        val className: String = fragment.javaClass.simpleName
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        if (isAdded) {
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.add(container_id, fragment, className)
        } else {
            fragmentTransaction.replace(container_id, fragment, className)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }

    fun currentFragment(fragmentManager: FragmentManager, container_id: Int): Fragment? {
        return fragmentManager.findFragmentById(container_id)
    }

}