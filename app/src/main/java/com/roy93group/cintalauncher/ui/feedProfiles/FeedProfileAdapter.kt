package com.roy93group.cintalauncher.ui.feedProfiles

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.cintalauncher.LauncherContext
import com.roy93group.cintalauncher.R
import com.roy93group.cintalauncher.providers.color.theme.ColorTheme
import com.roy93group.cintalauncher.data.feed.profiles.FeedProfile
import com.roy93group.cintalauncher.ui.feed.items.viewHolders.applyIfNotNull

class FeedProfileAdapter(
    val launcherContext: LauncherContext
) : RecyclerView.Adapter<FeedFilterViewHolder>() {

    private var items = emptyList<FeedProfile>()
    private var selection = -1

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedFilterViewHolder {
        return FeedFilterViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.feed_filter, parent, false)).apply {
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

    override fun onBindViewHolder(holder: FeedFilterViewHolder, i: Int) {
        val item = items[i]
        applyIfNotNull(holder.text, item.name, TextView::setText)
        applyIfNotNull(holder.icon, item.icon, ImageView::setImageDrawable)
        val bgColor = if (selection == i) ColorTheme.accentColor else ColorTheme.searchBarBG
        val fgColor = ColorTheme.textColorForBG(bgColor)
        holder.card.setCardBackgroundColor(bgColor)
        holder.text.setTextColor(fgColor)
        holder.icon.imageTintList = ColorStateList.valueOf(fgColor)
    }

    fun updateItems(items: List<FeedProfile>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun updateItems(vararg items: FeedProfile) {
        this.items = items.toList()
        notifyDataSetChanged()
    }

    fun updateColorTheme() {
        notifyDataSetChanged()
    }
}