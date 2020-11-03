package com.ducnv.moviehunt.ui.widgets.searchview

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class JJBarWithErrorIconController : JJBaseController() {
    private val mColor = "#E91E63"
    private var cx = 0f
    private var cy = 0f
    private var cr = 0f
    private val sign = 0.707f
    private val mCircleBig = 10f
    private val mRectF: RectF
    private val mRectF2: RectF
    private val mCirCleDis = 200f
    private val mFontPaint: Paint

    override fun draw(canvas: Canvas, paint: Paint) {
        canvas?.drawColor(Color.parseColor(mColor))
        when (mState) {
            STATE_ANIM_NONE -> drawNormalView(paint, canvas)
            STATE_ANIM_START -> drawStartAnimView(paint, canvas)
            STATE_ANIM_STOP -> drawStopAnimView(paint, canvas)
        }
    }


    private fun drawStopAnimView(paint: Paint, canvas: Canvas) {
        canvas.save()
        if (mPro <= 0.25) {
            canvas.drawArc(mRectF, 90F, -180F, false, paint)
            canvas.drawLine(mRectF2.left + cr, mRectF.top, mRectF.right - cr, mRectF.top, paint)
            canvas.drawLine(
                mRectF2.left + cr, mRectF.bottom, mRectF.right - cr, mRectF.bottom,
                paint
            )
            canvas.drawArc(mRectF2, 90F, 180F, false, paint)
            canvas.drawLine(
                mRectF.left + cr - sign * cr / 2,
                cy + cr * sign / 2,
                mRectF.left + cr + sign * cr / 2,
                cy - cr * sign / 2,
                paint
            )
        } else if (mPro > 0.25 && mPro <= 0.5f) {
            canvas.drawArc(mRectF, 90F, -180F, false, paint)
            canvas.drawLine(mRectF2.left + cr, mRectF.top, mRectF.right - cr, mRectF.top, paint)
            canvas.drawLine(
                mRectF2.left + cr, mRectF.bottom, mRectF.right - cr, mRectF.bottom,
                paint
            )
            canvas.drawArc(mRectF2, 90F, 180F, false, paint)
            canvas.drawArc(mRectF2, 90F, 180F, false, paint)
        } else if (mPro > 0.5f && mPro <= 0.75f) {
            canvas.drawCircle(cx, cy, cr, paint)
        } else {
            canvas.drawLine(
                cx + cr * sign,
                cy + cr * sign,
                cx + cr * sign + cr * sign *
                        (mPro - 0.75f) * 4,
                cy + cr * sign + cr * sign * (mPro - 0.75f) * 4,
                paint
            )
            canvas.drawCircle(cx, cy, cr, paint)
        }
        canvas.restore()
    }

    private fun drawStartAnimView(paint: Paint, canvas: Canvas) {
        canvas.save()
        if (mPro <= 0.25) {
            canvas.drawLine(
                cx + cr * sign, cy + cr * sign, cx + cr * sign + cr * sign *
                        (1 - mPro * 4), cy + cr * sign + cr * sign * (1 - mPro * 4), paint
            )
            canvas.drawCircle(cx, cy, cr, paint)
        } else if (mPro > 0.25 && mPro <= 0.5f) {
            mRectF.left = cx - cr + mCirCleDis * (mPro - 0.25f) * 4
            mRectF.right = cx + cr + mCirCleDis * (mPro - 0.25f) * 4
            mRectF2.left = cx - cr - mCirCleDis * (mPro - 0.25f) * 4
            mRectF2.right = cx + cr - mCirCleDis * (mPro - 0.25f) * 4
            canvas.drawArc(mRectF, 90F, -180F, false, paint)
            canvas.drawLine(mRectF2.left + cr, mRectF.top, mRectF.right - cr, mRectF.top, paint)
            canvas.drawLine(
                mRectF2.left + cr, mRectF.bottom, mRectF.right - cr, mRectF.bottom,
                paint
            )
            canvas.drawArc(mRectF2, 90F, 180F, false, paint)
        } else if (mPro > 0.5f && mPro <= 0.75f) {
            canvas.drawArc(mRectF, 90F, -180F, false, paint)
            canvas.drawLine(mRectF2.left + cr, mRectF.top, mRectF.right - cr, mRectF.top, paint)
            canvas.drawLine(
                mRectF2.left + cr, mRectF.bottom, mRectF.right - cr, mRectF.bottom,
                paint
            )
            canvas.drawArc(mRectF2, 90F, 180F, false, paint)
            canvas.drawLine(
                mRectF.left + cr - sign * cr / 2 * (mPro - 0.5f) * 4, cy + cr *
                        sign / 2 * (mPro - 0.5f) * 4, mRectF.left + cr + sign * cr / 2 + sign *
                        cr / 2 * (1 - (mPro - 0.5f) * 4), cy - cr * sign / 2 - sign * cr / 2 *
                        (1 - (mPro - 0.5f) * 4), paint
            )
        } else {
            canvas.drawArc(mRectF, 90F, -180F, false, paint)
            canvas.drawLine(mRectF2.left + cr, mRectF.top, mRectF.right - cr, mRectF.top, paint)
            canvas.drawLine(
                mRectF2.left + cr, mRectF.bottom, mRectF.right - cr, mRectF.bottom,
                paint
            )
            canvas.drawArc(mRectF2, 90F, 180F, false, paint)
            canvas.drawLine(
                mRectF.left + cr - sign * cr / 2, cy + cr * sign / 2, mRectF.left +
                        cr + sign * cr / 2, cy - cr * sign / 2, paint
            )
            canvas.drawLine(
                mRectF.left + cr - sign * cr / 2 * (mPro - 0.75f) * 4, cy - cr *
                        sign / 2 * (mPro - 0.75f) * 4, mRectF.left + cr + sign * cr / 2 + sign *
                        cr / 2 * (1 - (mPro - 0.75f) * 4), cy + cr * sign / 2 + sign * cr / 2 *
                        (1 - (mPro - 0.75f) * 4), paint
            )
        }
        canvas.restore()
    }

    private fun drawNormalView(paint: Paint, canvas: Canvas) {
        cr = width / 15.toFloat()
        cx = width / 2.toFloat()
        cy = height / 2.toFloat()
        mRectF.top = cy - cr
        mRectF.bottom = cy + cr
        mRectF2.top = cy - cr
        mRectF2.bottom = cy + cr
        paint.reset()
        paint.setAntiAlias(true)
        paint.setStrokeCap(Paint.Cap.ROUND)
        canvas.save()
        paint.setColor(Color.WHITE)
        paint.setStrokeWidth(8F)
        paint.setStyle(Paint.Style.STROKE)
        canvas.drawCircle(cx, cy, cr, paint)
        canvas.drawLine(
            cx + cr * sign, cy + cr * sign, cx + cr * 2 * sign,
            cy + cr * 2 * sign, paint
        )
        canvas.restore()
    }




    override fun startAnim() {
        if (mState === JJBaseController.Companion.STATE_ANIM_START) return
        mState = STATE_ANIM_START
        startSearchViewAnim()
    }

    override fun resetAnim() {
        if (mState === JJBaseController.Companion.STATE_ANIM_STOP) return
        mState = STATE_ANIM_STOP
        startSearchViewAnim()
    }

    init {
        mRectF = RectF()
        mRectF2 = RectF()
        mFontPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mFontPaint.setStrokeWidth(1F)
        mFontPaint.setColor(Color.WHITE)
        mFontPaint.setStyle(Paint.Style.FILL)
        mFontPaint.setTextSize(40F)
    }
}