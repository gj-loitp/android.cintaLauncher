package com.roy93group.launcher.ui.feedProfiles

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.utilities.LUIUtil
import com.roy93group.app.C
import com.roy93group.launcher.LauncherContext
import com.roy93group.launcher.R
import com.roy93group.launcher.data.feed.profiles.FeedProfile
import com.roy93group.launcher.ui.feed.items.viewHolders.applyIfNotNull
import kotlinx.android.synthetic.main.view_feed_filter.view.*
import kotlinx.android.synthetic.main.view_feed_filter.view.card
import kotlinx.android.synthetic.main.view_feed_filter.view.icon
import kotlinx.android.synthetic.main.view_feed_item_image.view.*

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedFilterVH {
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

    override fun onBindViewHolder(
        holder: FeedFilterVH,
        i: Int
    ) {
        val colorPrimary = C.getColorPrimary()
        val colorBackground = C.getColorBackground()

        val item = items[i]
        holder.itemView.text.apply {
            setTextColor(colorPrimary)
            applyIfNotNull(
                view = holder.itemView.text,
                value = item.name,
                block = TextView::setText
            )
        }
        holder.itemView.icon.apply {
            setColorFilter(colorPrimary)
            applyIfNotNull(
                view = holder.itemView.icon,
                value = item.icon,
                block = ImageView::setImageDrawable
            )
        }

        holder.itemView.card.apply {
            setCardBackgroundColor(colorBackground)
            if (i == (items.size - 1)) {
                LUIUtil.setMarginsDp(
                    view = this,
                    leftDp = 8,
                    topDp = 0,
                    rightDp = 8,
                    bottomDp = 0,
                )
            } else {
                LUIUtil.setMarginsDp(
                    view = this,
                    leftDp = 8,
                    topDp = 0,
                    rightDp = 0,
                    bottomDp = 0
                )
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<FeedProfile>) {
        this.items = items
        try {
            notifyItemRangeChanged(0, itemCount)
        } catch (e: Exception) {
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(vararg items: FeedProfile) {
        this.items = items.toList()
        try {
            notifyItemRangeChanged(0, itemCount)
        } catch (e: Exception) {
            notifyDataSetChanged()
        }
    }
}
