package com.roy93group.launcher.ui.feed.items.viewHolders

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.ProgressBar
import com.roy93group.launcher.R
import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.data.feed.items.FeedItemWithProgress

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FeedItemProgressVH(itemView: View) : FeedItemVH(itemView) {
    private val progress: ProgressBar = itemView.findViewById(R.id.progress)

    override fun onBind(item: FeedItem, color: Int) {
        super.onBind(item, color)
        item as FeedItemWithProgress

        progress.apply {
            max = item.max
            progress = item.progress
            isIndeterminate = false
            progressTintList = ColorStateList.valueOf(Color.WHITE)
            progressBackgroundTintList = ColorStateList.valueOf(Color.WHITE)
        }
    }
}
