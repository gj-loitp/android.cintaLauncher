package com.roy93group.launcher.ui.feed.items.viewHolders

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.data.feed.items.FeedItem

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
abstract class FeedViewHolder(
    itemView: View
) :
    RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(
        item: FeedItem,
        isDisplayAppIcon: Boolean,
        isForceColorIcon: Boolean,
    )
}

inline fun <T : View, R> applyIfNotNull(
    view: T,
    value: R,
    block: (T, R) -> Unit
) {
    if (value == null) {
        view.isVisible = false
    } else {
        view.isVisible = true
        block(view, value)
    }
}