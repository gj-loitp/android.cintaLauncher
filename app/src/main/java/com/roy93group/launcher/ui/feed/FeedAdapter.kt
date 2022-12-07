package com.roy93group.launcher.ui.feed

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.launcher.data.feed.items.*
import com.roy93group.launcher.ui.LauncherActivity
import com.roy93group.launcher.ui.feed.items.viewHolders.*
import com.roy93group.launcher.ui.feed.items.viewHolders.home.HomeViewHolder
import com.roy93group.launcher.ui.feed.items.viewHolders.home.bindHomeViewHolder
import com.roy93group.launcher.ui.feed.items.viewHolders.suggestions.SuggestedVH

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FeedAdapter(
    val launcherActivity: LauncherActivity
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HOME = 0
        private const val TYPE_PLAIN = 1
        private const val TYPE_SMALL = 2
        private const val TYPE_BIG_IMAGE = 3
        private const val TYPE_PROGRESS = 4
        private const val TYPE_MEDIA = 5
        private const val TYPE_SUGGESTED = 6
        private const val TYPE_EMPTY = 7
    }

    inline val context: Context get() = launcherActivity

    private var items: List<FeedItem> = emptyList()

    private fun getFeedItem(i: Int) = items[i - 1]

    override fun getItemCount() = if (items.isEmpty()) 2 else items.size + 1

    override fun getItemId(i: Int) =
        if (i == 0) 0 else if (items.isEmpty()) -1 else getFeedItem(i).id

    override fun getItemViewType(i: Int) = when {
        i == 0 -> TYPE_HOME
        items.isEmpty() -> TYPE_EMPTY
        else -> when (getFeedItem(i)) {
            is FeedItemSuggestedApps -> TYPE_SUGGESTED
            is FeedItemWithMedia -> TYPE_MEDIA
            is FeedItemWithBigImage -> TYPE_BIG_IMAGE
            is FeedItemSmall -> TYPE_SMALL
            is FeedItemWithProgress -> TYPE_PROGRESS
            else -> TYPE_PLAIN
        }
    }

    private var homeViewHolder: HomeViewHolder? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HOME -> HomeViewHolder(
                launcherActivity = launcherActivity,
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_feed_home, parent, false)
            ).also {
                homeViewHolder = it
            }
            TYPE_PLAIN -> FeedItemVH(
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_feed_item_plain, parent, false)
            )
            TYPE_SMALL -> FeedItemSmallVH(
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_feed_item_small, parent, false)
            )
            TYPE_BIG_IMAGE -> FeedItemImageVH(
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_feed_item_image, parent, false)
            )
            TYPE_PROGRESS -> FeedItemProgressVH(
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_feed_item_progress, parent, false)
            )
            TYPE_MEDIA -> FeedItemMediaVH(
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_feed_item_media, parent, false)
            )
            TYPE_SUGGESTED -> SuggestedVH(
                launcherActivity = launcherActivity, LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_feed_item_suggested_apps, parent, false)
            )




            TYPE_EMPTY -> EmptyFeedItemViewHolder(
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_feed_item_empty, parent, false)
            )
            else -> throw RuntimeException("Invalid view holder type")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        i: Int
    ) {
        if (i == 0) {
            return bindHomeViewHolder(
                holder = holder as HomeViewHolder,
                onClickClock = {
                    C.launchClockApp(launcherActivity)
                }, onClickCalendar = {
                    C.launchCalendar(launcherActivity)
                })
        }
        if (holder.itemViewType == TYPE_EMPTY) {
            bindEmptyFeedItemViewHolder(holder as EmptyFeedItemViewHolder)
            return
        }
        val item = getFeedItem(i)
        holder as FeedViewHolder
        holder.onBind(item)
        if (holder !is FeedItemVH) return
        val verticalPadding =
            holder.itemView.resources.getDimension(R.dimen.margin_padding_medium).toInt()
        holder.container.setPadding(
            /* left = */
            holder.container.paddingLeft,
            /* top = */
            holder.container.paddingTop,
            /* right = */
            holder.container.paddingRight,
            /* bottom = */
            if (i == itemCount - 1) verticalPadding + launcherActivity.getFeedBottomMargin() else verticalPadding,
        )
    }

    fun updateItems(items: List<FeedItem>) {
        val diffCallback = FeedDiffCallback(old = this.items, new = items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items = items
        diffResult.dispatchUpdatesTo(this)
    }

}
