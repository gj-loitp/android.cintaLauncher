package io.posidon.android.cintalauncher.ui.view

import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.widget.SectionIndexer
import posidon.android.conveniencelib.dp

interface HighlightSectionIndexer : SectionIndexer {

    fun highlight(i: Int)
    fun unhighlight()

    companion object {
        fun createHighlightDrawable(context: Context, accentColor: Int): ShapeDrawable {
            val bg = ShapeDrawable()
            val r = context.dp(12)
            bg.shape = RoundRectShape(floatArrayOf(r, r, r, r, r, r, r, r), null, null)
            bg.paint.color = accentColor and 0xffffff or 0x55000000
            return bg
        }
    }
}