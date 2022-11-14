package com.roy93group.cintalauncher.ui.feed.items.viewHolders.suggestions

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.cintalauncher.R
import com.roy93group.cintalauncher.providers.color.theme.ColorTheme
import com.roy93group.cintalauncher.data.feed.items.FeedItem
import com.roy93group.cintalauncher.data.feed.items.FeedItemSuggestedApps
import com.roy93group.cintalauncher.ui.LauncherActivity
import com.roy93group.cintalauncher.ui.feed.items.viewHolders.FeedViewHolder

class SuggestedViewHolder(
    val launcherActivity: LauncherActivity,
    itemView: View,
) : FeedViewHolder(itemView) {

    val separator = itemView.findViewById<View>(R.id.separator)!!

    val adapter = SuggestionsAdapter(launcherActivity)
    val recycler = itemView.findViewById<RecyclerView>(R.id.recents_recycler)!!.apply {
        layoutManager = GridLayoutManager(itemView.context, 3, RecyclerView.VERTICAL, false)
        adapter = this@SuggestedViewHolder.adapter
    }

    override fun onBind(item: FeedItem, color: Int) {
        item as FeedItemSuggestedApps
        separator.setBackgroundColor(ColorTheme.uiHint and 0x00ffffff or 0x24ffffff)
        adapter.updateItems(item.apps)
    }
}