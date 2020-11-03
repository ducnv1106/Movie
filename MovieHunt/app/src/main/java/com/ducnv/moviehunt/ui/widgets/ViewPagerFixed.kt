package com.ducnv.moviehunt.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import java.util.jar.Attributes

class ViewPagerFixed(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    private var mDowX = 0f
    private var mDowY = 0f
    private var isShowBottomBar = false

    lateinit var mListener: OnSwipeListener



    fun setOnSwipeListener(listener: OnSwipeListener) {
        mListener = listener
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        try {
            return super.onTouchEvent(ev)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        try {
            return super.onInterceptTouchEvent(ev)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev!!.action) {
            MotionEvent.ACTION_DOWN -> {
                mDowX = ev.x
                mDowY = ev.y
                isShowBottomBar=false
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                if (isShowBottomBar) {

                }
                if (!isShowBottomBar) {

                }
            }
            MotionEvent.ACTION_MOVE -> {
                val y = ev.y
                val yDelta=y-mDowY

                val x=ev.x
                val xDelta=x-mDowX

                if (yDelta>200){
                    isShowBottomBar=true
                    // if action recognized as swipe then swipe
                    mListener.onShowBottomNavigator()
                }
                if (yDelta<-200){
                    isShowBottomBar=false
                    // if action recognized as swipe then swipe
                    mListener.onHideBottomNavigator()
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    interface OnSwipeListener {
        fun onHideBottomNavigator()
        fun onShowBottomNavigator()
    }
}