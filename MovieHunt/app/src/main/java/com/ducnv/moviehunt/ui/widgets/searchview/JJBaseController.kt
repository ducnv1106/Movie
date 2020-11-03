package com.ducnv.moviehunt.ui.widgets.searchview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PathMeasure
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.IntDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.ref.WeakReference

abstract class JJBaseController {
    protected var mPro = -1f
    private var mSearchView: WeakReference<View>? = null
    protected var mPos = FloatArray(2)

    @IntDef(
        STATE_ANIM_NONE,
        STATE_ANIM_START,
        STATE_ANIM_STOP
    )
    @Retention(RetentionPolicy.SOURCE)
    annotation class State

    @State
    var mState = STATE_ANIM_NONE

    fun setSearchView(searchView: View) {
        mSearchView = WeakReference(searchView)
    }

    val searchView: View?
        get() = if (mSearchView != null) mSearchView!!.get() else null

    /**
     * 获取view的宽度
     *
     * @return
     */
    val width: Int
        get() = if (searchView != null) searchView!!.width else 0

    /**
     * 获取view的高度
     *
     * @return
     */
    val height: Int
        get() = if (searchView != null) searchView!!.height else 0

    /**
     * 绘制就交给我儿子们去做吧
     *
     * @param canvas
     * @param paint
     */
    abstract fun draw(canvas: Canvas, paint: Paint)

    /**
     * 开启搜索动画
     */
  open  fun startAnim() {}

    /**
     * 重置搜索动画
     */
   open fun resetAnim() {}

    fun startSearchViewAnim(): ValueAnimator {
        return startSearchViewAnim(
            DEFAULT_ANIM_STARTF,
            DEFAULT_ANIM_ENDF,
            DEFAULT_ANIM_TIME.toLong()
        )
    }

    fun startSearchViewAnim(
        startF: Float,
        endF: Float,
        time: Long
    ): ValueAnimator {
        return startSearchViewAnim(startF, endF, time, null)
    }

    fun startSearchViewAnim(
        startF: Float,
        endF: Float,
        time: Long,
        pathMeasure: PathMeasure?
    ): ValueAnimator {
        val valueAnimator = ValueAnimator.ofFloat(startF, endF)
        valueAnimator.duration = time
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.addUpdateListener { valueAnimator ->
            mPro = valueAnimator.animatedValue as Float
            pathMeasure?.getPosTan(mPro, mPos, null)
            searchView!!.invalidate()
        }
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
            }
        })
        if (!valueAnimator.isRunning) {
            valueAnimator.start()
        }
        mPro = 0f
        return valueAnimator
    }

    companion object {
        const val STATE_ANIM_NONE = 0
        const val STATE_ANIM_START = 1
        const val STATE_ANIM_STOP = 2
        const val DEFAULT_ANIM_TIME = 500
        const val DEFAULT_ANIM_STARTF = 0f
        const val DEFAULT_ANIM_ENDF = 1f
    }
}