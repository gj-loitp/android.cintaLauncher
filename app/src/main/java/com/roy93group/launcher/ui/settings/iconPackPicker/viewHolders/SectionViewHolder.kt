package com.roy93group.launcher.ui.settings.iconPackPicker.viewHolders

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R

class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val text: TextView = itemView.findViewById(R.id.text)

    fun bind(string: String) {
        text.text = string
        text.setTextColor(
            Color.RED
        )
    }
}