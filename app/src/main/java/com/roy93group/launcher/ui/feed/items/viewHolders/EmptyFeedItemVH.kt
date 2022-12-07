package com.roy93group.launcher.ui.feed.items.viewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.app.C
import com.roy93group.launcher.R

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class EmptyFeedItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val text: TextView = itemView.findViewById<TextView>(R.id.text).apply {
        setTextColor(C.COLOR_PRIMARY_2)
    }
}

fun bindEmptyFeedItemViewHolder(
    holder: EmptyFeedItemViewHolder
) {
    holder.text.text = holder.itemView.context.getString(R.string.no_feed_items)
}
