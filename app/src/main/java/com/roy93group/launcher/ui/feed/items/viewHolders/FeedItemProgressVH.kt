package com.roy93group.launcher.ui.feed.items.viewHolders

import android.content.res.ColorStateList
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.data.feed.items.FeedItemWithProgress
import kotlinx.android.synthetic.main.view_feed_item_progress.view.*

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FeedItemProgressVH(
    activity: AppCompatActivity,
    itemView: View,
) : FeedItemVH(activity, itemView) {

    override fun onBind(
        feedItem: FeedItem,
        isDisplayAppIcon: Boolean,
        isForceColorIcon: Boolean
    ) {
        super.onBind(feedItem, isDisplayAppIcon, isForceColorIcon)
        feedItem as FeedItemWithProgress

        itemView.progress.apply {
            max = feedItem.max
            progress = feedItem.progress
            isIndeterminate = false
            progressTintList = ColorStateList.valueOf(colorPrimary)
            progressBackgroundTintList = ColorStateList.valueOf(colorPrimary)
        }
    }
}
