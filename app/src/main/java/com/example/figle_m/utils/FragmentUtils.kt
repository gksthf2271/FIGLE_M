package com.example.figle_m.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


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