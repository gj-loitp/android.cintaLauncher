package com.roy93group.launcher.ui.feed.items.viewHolders.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.app.C
import com.roy93group.launcher.R
import kotlinx.android.synthetic.main.view_feed_home_notification_icon.view.*

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class NotificationIconsAdapter : RecyclerView.Adapter<NotificationIconsAdapter.IconViewHolder>() {
    private val colorPrimary = C.getColorPrimary()
    private var isForceColorIcon = C.getForceColorIcon()
    private var items = emptyList<Drawable>()

    class IconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

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
        holder.itemView.image.apply {
            setImageDrawable(items[i])
            if (isForceColorIcon) {
                setColorFilter(colorPrimary)
            } else {
                setColorFilter(Color.TRANSPARENT)
            }
        }

    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(
        items: List<Drawable>,
        isForceColorIcon: Boolean
    ): Boolean {
        val numberChanged = this.items.size != items.size
        this.items = items
        this.isForceColorIcon = isForceColorIcon
        try {
            notifyItemRangeChanged(/* positionStart = */ 0, /* itemCount = */ itemCount)
        } catch (e: Exception) {
            notifyDataSetChanged()
        }
        return numberChanged
    }
}
