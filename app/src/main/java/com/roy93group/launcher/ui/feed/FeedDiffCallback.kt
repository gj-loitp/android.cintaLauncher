package com.roy93group.launcher.ui.feed

import androidx.recyclerview.widget.DiffUtil
import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.data.feed.items.FeedItemSuggestedApps

class FeedDiffCallback(
    private val old: List<FeedItem>,
    private val new: List<FeedItem>
) :
    DiffUtil.Callback() {

    private fun getOld(i: Int) = old[i - 1]

    private fun getNew(i: Int) = new[i - 1]

    override fun getOldListSize() = if (old.isEmpty()) 2 else old.size + 1

    override fun getNewListSize() = if (new.isEmpty()) 2 else new.size + 1

    override fun areItemsTheSame(oldI: Int, newI: Int): Boolean {
        if (oldI == 0) return newI == 0
        if (newI == 0) return oldI == 0
        if (old.isEmpty()) return new.isEmpty()
        if (new.isEmpty()) return old.isEmpty()
        return getOld(oldI).uid == getNew(newI).uid
    }

    override fun areContentsTheSame(oldI: Int, newI: Int): Boolean {
        if (oldI == 0 && newI == 0) return true
        if (old.isEmpty()) return new.isEmpty()
        if (new.isEmpty()) return old.isEmpty()
        val o = getOld(oldI)
        val n = getNew(newI)
        return o.color == n.color
                && o.title == n.title
                && o.description == n.description
                && o.source == n.source
                && o.instant == n.instant
                && o.isDismissible == n.isDismissible
                && (oldI == oldListSize - 1) == (newI == newListSize - 1)
                && (o as? FeedItemSuggestedApps)?.apps.isNullOrEmpty() && (n as? FeedItemSuggestedApps)?.apps.isNullOrEmpty()
    }
}
