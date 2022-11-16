package com.roy93group.lookerupper.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R
import com.roy93group.launcher.providers.color.theme.ColorTheme
import com.roy93group.lookerupper.ui.viewHolders.InfoboxEntryVH

class InfoBoxAdapter : RecyclerView.Adapter<InfoboxEntryVH>() {

    private var entries: List<Pair<String, String>> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        InfoboxEntryVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_search_result_answer_info_box_entry, parent, false)
        )

    override fun onBindViewHolder(holder: InfoboxEntryVH, i: Int) {
        val e = entries[i]
        holder.label.text = e.first
        holder.value.text = e.second

        holder.label.setTextColor(ColorTheme.cardTitle)
        holder.value.setTextColor(ColorTheme.cardDescription)
        holder.separator.setBackgroundColor(ColorTheme.hintColorForBG(ColorTheme.cardBG))
    }

    override fun getItemCount() = entries.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateEntries(entries: List<Pair<String, String>>) {
        this.entries = entries
        notifyDataSetChanged()
    }
}
