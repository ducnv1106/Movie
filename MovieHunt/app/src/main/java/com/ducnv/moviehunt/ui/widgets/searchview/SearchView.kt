package com.ducnv.moviehunt.ui.widgets.searchview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class SearchView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var mPaint: Paint? = null
    private var mPath: Path? = null
    private var mController: JJBaseController? = JJBarWithErrorIconController()

    private fun init() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint!!.strokeWidth = 4f
        mPath = Path()
    }

    fun setController(controller: JJBaseController?) {
        mController = controller
        mController!!.setSearchView(this)
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mController!!.draw(canvas, mPaint!!)
    }

    fun startAnim() {
        if (mController != null) mController!!.startAnim()
    }

    fun resetAnim() {
        if (mController != null) mController!!.resetAnim()
    }

    init {
        init()
    }
}