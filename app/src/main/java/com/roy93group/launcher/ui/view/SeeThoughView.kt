package com.roy93group.launcher.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import io.posidon.android.conveniencelib.Device

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

class SeeThoughView : View {
    constructor(c: Context) : super(c)
    constructor(
        c: Context,
        a: AttributeSet?
    ) : this(c = c, a = a, da = 0, dr = 0)

    constructor(
        c: Context,
        a: AttributeSet?,
        da: Int
    ) : this(c = c, a = a, da = da, dr = 0)

    constructor(
        c: Context,
        a: AttributeSet?,
        da: Int,
        dr: Int
    ) : super(c, a, da, dr)

    var drawable: Drawable? = null
        set(value) {
            field = value
            invalidate()
        }

    private val lastScreenLocation = IntArray(2)

    init {
        viewTreeObserver.addOnPreDrawListener {
            invalidate()
            true
        }
    }

    override fun isDirty(): Boolean {
        return super.isDirty() || run {
            val location = IntArray(2)
            getLocationOnScreen(location)
            !lastScreenLocation.contentEquals(location)
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val d = drawable
        if (d != null) {
            val w = Device.screenWidth(context)
            val h = Device.screenHeight(context)
            getLocationOnScreen(lastScreenLocation)
            val l = lastScreenLocation[0]
            val t = lastScreenLocation[1]
            d.setBounds(-l, -t, w - l, h - t)
            d.draw(canvas)
        }
    }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        setMeasuredDimension(
            calculate(minSize = suggestedMinimumWidth, measureSpec = widthMeasureSpec),
            calculate(minSize = suggestedMinimumHeight, measureSpec = heightMeasureSpec),
        )
    }

    private fun calculate(
        minSize: Int,
        measureSpec: Int
    ): Int {
        var result = minSize
        val mode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        when (mode) {
            MeasureSpec.UNSPECIFIED -> result = minSize
            MeasureSpec.AT_MOST -> result = minSize
            MeasureSpec.EXACTLY -> result = specSize
        }
        return result
    }
}
