package com.roy93group.launcher.ui.view.scrollbar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.IntDef
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R
import com.roy93group.launcher.ui.view.scrollbar.hue.HueScrollbarController
import kotlin.math.roundToInt

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

class Scrollbar : View {
    @IntDef(VERTICAL, HORIZONTAL)
    annotation class Orientation

    companion object {
        const val VERTICAL = 0
        const val HORIZONTAL = 1
    }

    var controller: ScrollbarController = HueScrollbarController(this)

    constructor(c: Context) : super(c) {
        boldTypeface = Typeface.create(paint.typeface, Typeface.BOLD)
    }

    constructor(c: Context, a: AttributeSet?) : this(c, a, 0, 0)

    constructor(c: Context, a: AttributeSet?, da: Int) : this(c, a, da, 0)

    constructor(c: Context, a: AttributeSet?, da: Int, dr: Int) : super(c, a, da, dr) {
        if (a != null) {
            val typedArray = c.obtainStyledAttributes(a, R.styleable.TextAppearance, da, dr)
            val fontFamilyId: Int =
                typedArray.getResourceId(R.styleable.TextAppearance_android_fontFamily, 0)
            if (fontFamilyId > 0) {
                paint.typeface = ResourcesCompat.getFont(context, fontFamilyId)
            }
            typedArray.recycle()
        }
        boldTypeface = Typeface.create(paint.typeface, Typeface.BOLD)
    }

    @Orientation
    var orientation: Int = HORIZONTAL

    var onStartScroll: (View) -> Unit = {}
    var onCancelScroll: (View) -> Unit = {}

    val paint = Paint().apply {
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    var boldTypeface: Typeface
        private set

    var typeface: Typeface
        get() = paint.typeface
        set(value) {
            paint.typeface = value
            boldTypeface = Typeface.create(paint.typeface, Typeface.BOLD)
        }

    var recycler: RecyclerView? = null
        set(value) {
            try {
                field?.removeOnScrollListener(onScrollListener)
                field = value
                value?.addOnScrollListener(onScrollListener)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    @Suppress("unused")
    fun destroy() {
        recycler?.removeOnScrollListener(onScrollListener)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        controller.draw(canvas)
    }

    override fun performClick(): Boolean {
        onStartScroll(this)
        return super.performClick()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                val i = coordsToIndex(event.x, event.y)
                controller.indexer.getPositionForSection(i).let {
                    recycler?.scrollToPositionAndCenter(it)
                    controller.indexer.highlight(it)
                }
                invalidate()
            }
            MotionEvent.ACTION_DOWN -> {
                onStartScroll(this)
                parent.requestDisallowInterceptTouchEvent(true)
                val i = coordsToIndex(event.x, event.y)
                controller.indexer.getPositionForSection(i).let {
                    recycler?.scrollToPositionAndCenter(it)
                    controller.indexer.highlight(it)
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                controller.indexer.unhighlight()
            }
            MotionEvent.ACTION_CANCEL -> {
                controller.indexer.unhighlight()
                onCancelScroll(this)
            }
        }
        return true
    }

    private fun RecyclerView.scrollToPositionAndCenter(i: Int) {
        val l = layoutManager as LinearLayoutManager
        val center = height / 3
        l.scrollToPositionWithOffset(i, center)
    }

    private fun coordsToIndex(x: Float, y: Float): Int {
        if (orientation == VERTICAL) {
            val out =
                ((y - paddingTop) / (height - paddingTop - paddingBottom) * controller.indexer.sections.lastIndex).roundToInt()
            if (out < 0) return 0
            if (out > controller.indexer.sections.lastIndex) return controller.indexer.sections.lastIndex
            return out
        } else {
            val out =
                ((x - paddingLeft) / (width - paddingLeft - paddingRight) * controller.indexer.sections.lastIndex).roundToInt()
            if (out < 0) return 0
            if (out > controller.indexer.sections.lastIndex) return controller.indexer.sections.lastIndex
            return out
        }
    }

    var currentScrolledSectionStart = -1
        private set
    var currentScrolledSectionEnd = -1
        private set

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            currentScrolledSectionStart = (recyclerView.layoutManager as LinearLayoutManager?)
                ?.findFirstCompletelyVisibleItemPosition()
                ?.let {
                    if (it == -1) -1
                    else controller.indexer.getSectionForPosition(it)
                } ?: -1
            currentScrolledSectionEnd = (recyclerView.layoutManager as LinearLayoutManager?)
                ?.findLastCompletelyVisibleItemPosition()
                ?.let {
                    if (it == -1) -1
                    else controller.indexer.getSectionForPosition(it)
                } ?: -1
            invalidate()
        }
    }
}
