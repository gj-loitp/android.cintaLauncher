package com.roy93group.launcher.ui.view.scrollbar.alphabet

import android.annotation.SuppressLint
import com.roy93group.launcher.data.items.App
import com.roy93group.launcher.ui.drawer.AppDrawerAdapter
import com.roy93group.launcher.ui.view.recycler.HighlightSectionIndexer

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

class AlphabetSectionIndexer : HighlightSectionIndexer {
    private var savedSections = emptyArray<Char>()
    private var adapter: AppDrawerAdapter? = null

    override fun getSections(): Array<Char> = savedSections

    override fun getSectionForPosition(i: Int): Int =
        adapter?.let { savedSections.indexOf(it.items[i].label[0].uppercaseChar()) } ?: 0

    override fun getPositionForSection(i: Int): Int {
        return adapter?.items?.indexOfFirst { it.label[0] == savedSections[i] } ?: 0
    }

    fun updateSections(
        adapter: AppDrawerAdapter,
        appSections: List<List<App>>
    ) {
        this.adapter = adapter
        savedSections = Array(appSections.size) { appSections[it][0].label[0].uppercaseChar() }
    }

    private var highlightI = -1
    override fun getHighlightI() = highlightI

    @SuppressLint("NotifyDataSetChanged")
    override fun highlight(i: Int) {
        val oldI = highlightI
        highlightI = i
        if (oldI != i) {
            //do not use notifyItemRangeChanged here
            adapter?.notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun unhighlight() {
        highlightI = -1
        //do not use notifyItemRangeChanged here
        adapter?.notifyDataSetChanged()
    }

    override fun isDimmed(app: App): Boolean =
        highlightI != -1 && adapter != null && adapter!!.items[highlightI].label[0] != app.label[0].uppercaseChar()
}
