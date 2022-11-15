package com.roy93group.launcher.ui.feedProfiles

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.LauncherContext
import com.roy93group.launcher.R
import com.roy93group.launcher.data.feed.profiles.FeedProfile
import com.roy93group.launcher.providers.color.theme.ColorTheme
import com.roy93group.launcher.ui.feed.items.viewHolders.applyIfNotNull

class FeedProfileAdapter(
    private val launcherContext: LauncherContext
) : RecyclerView.Adapter<FeedFilterVH>() {

    private var items = emptyList<FeedProfile>()
    private var selection = -1

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedFilterVH {
        return FeedFilterVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.feed_filter, parent, false)
        ).apply {
            itemView.setOnClickListener {
                val oldSelection = selection
                selection = adapterPosition
                if (oldSelection != selection) {
                    notifyItemChanged(oldSelection)
                    notifyItemChanged(selection)
                    launcherContext.feed.setProfile(items[adapterPosition])
                }
            }
        }
    }

    override fun onBindViewHolder(holder: FeedFilterVH, i: Int) {
        val item = items[i]
        applyIfNotNull(view = holder.text, value = item.name, block = TextView::setText)
        applyIfNotNull(view = holder.icon, value = item.icon, block = ImageView::setImageDrawable)
        val bgColor = if (selection == i) ColorTheme.accentColor else ColorTheme.searchBarBG
        val fgColor = ColorTheme.textColorForBG(bgColor)
        holder.card.setCardBackgroundColor(bgColor)
        holder.text.setTextColor(fgColor)
        holder.icon.imageTintList = ColorStateList.valueOf(fgColor)
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

    @SuppressLint("NotifyDataSetChanged")
    fun updateColorTheme() {
        notifyDataSetChanged()
    }
}
