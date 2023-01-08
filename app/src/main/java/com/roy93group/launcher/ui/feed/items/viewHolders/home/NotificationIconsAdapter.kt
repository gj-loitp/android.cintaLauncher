package com.roy93group.launcher.ui.feed.items.viewHolders.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.ext.*
import com.roy93group.launcher.R
import kotlinx.android.synthetic.main.view_feed_home_notification_icon.view.*

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class NotificationIconsAdapter(
    val context: Context
) : RecyclerView.Adapter<NotificationIconsAdapter.IconViewHolder>() {
    private val colorPrimary = getColorPrimary()
    private val colorBackground = getColorBackground()
    private var isForceColorIcon = context.getForceColorIcon()
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
                if (colorBackground == COLOR_15) {
                    setColorFilter(colorPrimary)
                } else {
                    setColorFilter(Color.TRANSPARENT)
                }
            }
        }

    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(
        items: List<Drawable>,
        isForceColorIcon: Boolean
    ) {
        this.items = items
        this.isForceColorIcon = isForceColorIcon
        try {
            notifyItemRangeChanged(/* positionStart = */ 0, /* itemCount = */ itemCount)
        } catch (e: Exception) {
            notifyDataSetChanged()
        }
    }
}
