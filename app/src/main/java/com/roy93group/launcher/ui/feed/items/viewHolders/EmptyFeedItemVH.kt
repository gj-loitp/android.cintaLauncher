package com.roy93group.launcher.ui.feed.items.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.ext.getColorPrimary
import com.roy93group.launcher.R
import kotlinx.android.synthetic.main.view_feed_item_empty.view.*

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class EmptyFeedItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

fun bindEmptyFeedItemViewHolder(
    holder: EmptyFeedItemViewHolder
) {
    holder.itemView.text.apply {
        setTextColor(getColorPrimary())
        text = holder.itemView.context.getString(R.string.no_feed_items)
    }

}
