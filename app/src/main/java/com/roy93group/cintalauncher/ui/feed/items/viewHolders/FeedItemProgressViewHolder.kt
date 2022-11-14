package com.roy93group.cintalauncher.ui.feed.items.viewHolders

import android.content.res.ColorStateList
import android.view.View
import android.widget.ProgressBar
import com.roy93group.cintalauncher.R
import com.roy93group.cintalauncher.data.feed.items.FeedItem
import com.roy93group.cintalauncher.data.feed.items.FeedItemWithProgress

class FeedItemProgressViewHolder(itemView: View) : FeedItemViewHolder(itemView) {
    val progress = itemView.findViewById<ProgressBar>(R.id.progress)

    override fun onBind(item: FeedItem, color: Int) {
        super.onBind(item, color)
        item as FeedItemWithProgress

        progress.max = item.max
        progress.progress = item.progress
        progress.isIndeterminate = false

        progress.progressTintList = ColorStateList.valueOf(color)
        progress.progressBackgroundTintList = ColorStateList.valueOf(color)
    }
}