package com.roy93group.launcher.ui.feed.items.viewHolders.suggestions

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R
import com.roy93group.launcher.data.items.LauncherItem
import com.roy93group.launcher.ui.drawer.viewHolders.AppViewHolder
import com.roy93group.launcher.ui.drawer.viewHolders.bindAppViewHolder

class SuggestionsAdapter(
    val activity: Activity,
) : RecyclerView.Adapter<AppViewHolder>() {

    private var items: List<LauncherItem> = emptyList()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        return AppViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_app_card, parent, false) as CardView
        )
    }

    override fun onBindViewHolder(holder: AppViewHolder, i: Int) {
        val item = items[i]
        bindAppViewHolder(
            holder = holder,
            item = item,
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<LauncherItem>) {
        this.items = items
        notifyDataSetChanged()
    }
}
