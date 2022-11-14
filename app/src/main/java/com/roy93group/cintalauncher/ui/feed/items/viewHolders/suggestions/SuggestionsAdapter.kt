package com.roy93group.cintalauncher.ui.feed.items.viewHolders.suggestions

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.cintalauncher.R
import com.roy93group.cintalauncher.data.items.LauncherItem
import com.roy93group.cintalauncher.ui.drawer.viewHolders.AppViewHolder
import com.roy93group.cintalauncher.ui.drawer.viewHolders.bindAppViewHolder

class SuggestionsAdapter(
    val activity: Activity,
) : RecyclerView.Adapter<AppViewHolder>() {

    private var items: List<LauncherItem> = emptyList()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        return AppViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.app_card, parent, false) as CardView
        )
    }

    override fun onBindViewHolder(holder: AppViewHolder, i: Int) {
        val item = items[i]
        bindAppViewHolder(
            holder = holder,
            item = item,
            isDimmed = false,
            activity = activity,
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<LauncherItem>) {
        this.items = items
        notifyDataSetChanged()
    }
}
