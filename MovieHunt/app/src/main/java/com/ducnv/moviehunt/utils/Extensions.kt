package com.ducnv.moviehunt.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.ducnv.moviehunt.constants.Constants

fun Exception.safeLog() {
    if (Constants.DEBUG) printStackTrace()
}

fun View.hideKeyboardwithoutPopulate(){
    val inputMethodManager: InputMethodManager = context!!.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(
        this.windowToken, 0
    )
}
fun ViewPager2.findCurrentFragment(fragmentManager: FragmentManager): Fragment? {
    return fragmentManager.findFragmentByTag("f$currentItem")
}

fun ViewPager2.findFragmentAtPosition(
    fragmentManager: FragmentManager,
    position: Int
): Fragment? {
    return fragmentManager.findFragmentByTag("f$position")
}