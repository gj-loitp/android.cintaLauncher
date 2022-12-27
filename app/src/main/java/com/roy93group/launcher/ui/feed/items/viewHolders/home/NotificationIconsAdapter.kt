package com.roy93group.launcher.ui.feed.items.viewHolders.home

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class NotificationIconsAdapter : RecyclerView.Adapter<NotificationIconsAdapter.IconViewHolder>() {

    private var items = emptyList<Drawable>()

    class IconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IconViewHolder {
        return IconViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    /* resource = */ R.layout.view_feed_home_notification_icon,
                    /* root = */parent,
                    /* attachToRoot = */false
                )
        )
    }

    override fun onBindViewHolder(
        holder: IconViewHolder,
        i: Int
    ) {
        holder.image.setImageDrawable(items[i])
    }

    override fun getItemCount() = items.size

    fun updateItems(items: List<Drawable>): Boolean {
        val numberChanged = this.items.size != items.size
        this.items = items
        notifyItemRangeChanged(/* positionStart = */ 0, /* itemCount = */ itemCount)
        return numberChanged
    }
}
