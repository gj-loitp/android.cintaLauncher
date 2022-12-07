package com.roy93group.launcher.ui.feed.items.viewHolders.suggestions

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.launcher.data.feed.items.FeedItem
import com.roy93group.launcher.data.feed.items.FeedItemSuggestedApps
import com.roy93group.launcher.ui.LauncherActivity
import com.roy93group.launcher.ui.feed.items.viewHolders.FeedViewHolder

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class SuggestedVH(
    val launcherActivity: LauncherActivity,
    itemView: View,
) : FeedViewHolder(itemView) {

    val adapter = SuggestionsAdapter(launcherActivity)

    val vLine: View = itemView.findViewById<View>(R.id.vLine).apply {
        setBackgroundColor(C.COLOR_PRIMARY_2)
    }
    val rvRecents: RecyclerView = itemView.findViewById<RecyclerView>(R.id.rvRecents).apply {
        layoutManager = GridLayoutManager(
            /* context = */ itemView.context,
            /* spanCount = */1,
            /* orientation = */RecyclerView.VERTICAL,
            /* reverseLayout = */false
        )
        adapter = this@SuggestedVH.adapter
    }

    override fun onBind(item: FeedItem) {
        item as FeedItemSuggestedApps
        adapter.updateItems(item.apps)
    }
}
