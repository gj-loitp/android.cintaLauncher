package com.roy93group.launcher.ui.feed.items.viewHolders

import android.content.res.ColorStateList
import android.view.View
import android.widget.ProgressBar
import com.roy93group.launcher.R
import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.data.feed.items.FeedItemWithProgress

class FeedItemProgressVH(itemView: View) : FeedItemVH(itemView) {
    private val progress: ProgressBar = itemView.findViewById(R.id.progress)

    override fun onBind(item: FeedItem, color: Int) {
        super.onBind(item, color)
        item as FeedItemWithProgress

        progress.apply {
            max = item.max
            progress = item.progress
            isIndeterminate = false
            progressTintList = ColorStateList.valueOf(color)
            progressBackgroundTintList = ColorStateList.valueOf(color)
        }
    }
}
