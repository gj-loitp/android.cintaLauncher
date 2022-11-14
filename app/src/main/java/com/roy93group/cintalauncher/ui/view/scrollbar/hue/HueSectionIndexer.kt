package com.roy93group.cintalauncher.ui.view.scrollbar.hue

import com.roy93group.cintalauncher.data.items.App
import com.roy93group.cintalauncher.ui.drawer.AppDrawerAdapter
import com.roy93group.cintalauncher.ui.drawer.viewHolders.AppItem
import com.roy93group.cintalauncher.ui.view.recycler.HighlightSectionIndexer

class HueSectionIndexer(
    private val scrollbarController: HueScrollbarController
) : HighlightSectionIndexer {

    private var savedSections = emptyArray<Float>()
    private var adapter: AppDrawerAdapter? = null

    override fun getSections(): Array<Float> = savedSections
    override fun getSectionForPosition(i: Int): Int {
        if (adapter == null) return -1
        val hue = (adapter!!.items[i] as? AppItem ?: return -1).item.hsl[0]
        return savedSections.indexOfFirst {
            val d = hue - it
            d >= 0f && d < scrollbarController.step
        }
    }
    override fun getPositionForSection(i: Int): Int {
        if (adapter == null) return -1
        return adapter!!.items.indexOfFirst { (it as? AppItem ?: return -1).item.hsl[0] == savedSections[i] }
    }

    fun updateSections(adapter: AppDrawerAdapter, appSections: List<List<App>>) {
        this.adapter = adapter
        savedSections = Array(appSections.size) { appSections[it][0].hsl[0] }
    }

    private var highlightI = -1
    override fun getHighlightI() = highlightI

    override fun highlight(i: Int) {
        val oldI = highlightI
        highlightI = i
        if (oldI != i) {
            adapter?.notifyDataSetChanged()
        }
    }

    override fun unhighlight() {
        highlightI = -1
        adapter?.notifyDataSetChanged()
    }

    override fun isDimmed(app: App): Boolean =
        highlightI != -1 && adapter != null && run {
            val d = app.hsl[0] - (adapter!!.items[highlightI] as? AppItem ?: return false).item.hsl[0]
            !(d >= 0f && d < scrollbarController.step)
        }
}