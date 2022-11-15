package com.roy93group.launcher.ui.feed.items.viewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R
import com.roy93group.launcher.providers.color.theme.ColorTheme

class EmptyFeedItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val text: TextView = itemView.findViewById(R.id.text)
}

fun bindEmptyFeedItemViewHolder(
    holder: EmptyFeedItemViewHolder
) {
    holder.text.text = holder.itemView.context.getString(R.string.no_feed_items)
    holder.text.setTextColor(ColorTheme.uiDescription)
}
