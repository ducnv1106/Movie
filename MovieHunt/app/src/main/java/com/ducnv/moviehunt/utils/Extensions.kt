package com.ducnv.moviehunt.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.ducnv.moviehunt.constants.Constants
import kotlin.math.ln
import kotlin.math.pow

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

fun Int.format(): String {
    // source: https://stackoverflow.com/questions/9769554/how-to-convert-number-into-k-thousands-m-million-and-b-billion-suffix-in-jsp?lq=1
    if (this < 1000) return toString()
    val exp = ln(this.toDouble()).div(ln(1000.0)).toInt()
    return String.format("%.2f %c", this.div(1000.0.pow(exp)), "kMGTPE"[exp - 1])
}

fun Int.formatHourMinutes(): String {
    val hours = this.div(60)
    val minutes = this % 60
    return String.format("%d: %02d: 00", hours, minutes)
}