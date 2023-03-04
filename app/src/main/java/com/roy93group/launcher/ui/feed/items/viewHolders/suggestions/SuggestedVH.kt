package com.roy93group.launcher.ui.feed.items.viewHolders.suggestions

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.data.feed.items.FeedItemSuggestedApps
import com.roy93group.launcher.ui.feed.items.viewHolders.FeedViewHolder
import kotlinx.android.synthetic.main.view_feed_item_suggested_apps.view.*

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class SuggestedVH(
    activity: AppCompatActivity,
    itemView: View,
) : FeedViewHolder(activity, itemView) {

    val adapter = SuggestionsAdapter(activity = activity)

    @Suppress("unused")
    val rvRecents: RecyclerView = itemView.rvRecents.apply {
        layoutManager = GridLayoutManager(
            /* context = */ itemView.context,
            /* spanCount = */1,
            /* orientation = */RecyclerView.VERTICAL,
            /* reverseLayout = */false
        )
        adapter = this@SuggestedVH.adapter
    }

    override fun onBind(
        feedItem: FeedItem,
        isDisplayAppIcon: Boolean,
        isForceColorIcon: Boolean,
    ) {
        feedItem as FeedItemSuggestedApps

        itemView.vLine.setBackgroundColor(colorPrimary)
        adapter.updateItems(
            items = feedItem.apps,
            isDisplayAppIcon = isDisplayAppIcon,
            isForceColorIcon = isForceColorIcon
        )
    }
}
