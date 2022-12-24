package com.roy93group.launcher.ui.feedProfiles

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.utilities.LUIUtil
import com.roy93group.launcher.LauncherContext
import com.roy93group.launcher.R
import com.roy93group.launcher.data.feed.profiles.FeedProfile
import com.roy93group.launcher.ui.feed.items.viewHolders.applyIfNotNull

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FeedProfileAdapter(
    private val launcherContext: LauncherContext
) : RecyclerView.Adapter<FeedFilterVH>() {

    private var items = emptyList<FeedProfile>()
    private var selection = -1

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedFilterVH {
        return FeedFilterVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_feed_filter, parent, false)
        ).apply {
            itemView.setOnClickListener {
                val oldSelection = selection
                selection = bindingAdapterPosition
                if (oldSelection != selection) {
                    notifyItemChanged(oldSelection)
                    notifyItemChanged(selection)
                    launcherContext.feed.setProfile(items[bindingAdapterPosition])
                }
            }
        }
    }

    override fun onBindViewHolder(holder: FeedFilterVH, i: Int) {
        val item = items[i]
        applyIfNotNull(view = holder.text, value = item.name, block = TextView::setText)
        applyIfNotNull(view = holder.icon, value = item.icon, block = ImageView::setImageDrawable)

        if (i == (items.size - 1)) {
            LUIUtil.setMarginsDp(
                view = holder.card,
                leftDp = 8,
                topDp = 0,
                rightDp = 8,
                bottomDp = 0,
            )
        } else {
            LUIUtil.setMarginsDp(
                view = holder.card,
                leftDp = 8,
                topDp = 0,
                rightDp = 0,
                bottomDp = 0
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<FeedProfile>) {
        this.items = items
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(vararg items: FeedProfile) {
        this.items = items.toList()
        notifyDataSetChanged()
    }
}
