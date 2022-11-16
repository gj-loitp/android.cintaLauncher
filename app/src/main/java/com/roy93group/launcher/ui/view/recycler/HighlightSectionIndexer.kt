package com.roy93group.launcher.ui.view.recycler

import android.widget.SectionIndexer
import com.roy93group.launcher.data.items.App

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
interface HighlightSectionIndexer : SectionIndexer {
    fun highlight(i: Int)
    fun unhighlight()
    fun isDimmed(app: App): Boolean
    fun getHighlightI(): Int
}
