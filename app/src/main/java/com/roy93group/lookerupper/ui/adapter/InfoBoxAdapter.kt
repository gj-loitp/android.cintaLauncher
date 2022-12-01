package com.roy93group.lookerupper.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R
import com.roy93group.lookerupper.ui.viewHolders.InfoboxEntryVH

/**
 * Updated by Loitp on 2022.12.18
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class InfoBoxAdapter : RecyclerView.Adapter<InfoboxEntryVH>() {

    private var listEntry: List<Pair<String, String>> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        InfoboxEntryVH(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_search_result_answer_info_box_entry, parent, false)
        )

    override fun onBindViewHolder(holder: InfoboxEntryVH, i: Int) {
        val e = listEntry[i]
        holder.label.text = e.first
        holder.value.text = e.second
    }

    override fun getItemCount() = listEntry.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateEntries(entries: List<Pair<String, String>>) {
        this.listEntry = entries
        notifyDataSetChanged()
    }
}
