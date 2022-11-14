package com.roy93group.lookerupper.ui.viewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.cintalauncher.R

class InfoboxEntryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val label: TextView = itemView.findViewById(R.id.label)
    val value: TextView = itemView.findViewById(R.id.value)
    val separator: View = itemView.findViewById(R.id.separator)
}
