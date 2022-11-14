package com.roy93group.cintalauncher.ui.settings.iconPackPicker.viewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.cintalauncher.R
import com.roy93group.cintalauncher.providers.color.theme.ColorTheme

class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val text: TextView = itemView.findViewById(R.id.text)

    fun bind(string: String) {
        text.text = string
        text.setTextColor(
            ColorTheme.adjustColorForContrast(
                ColorTheme.uiBG,
                ColorTheme.accentColor
            )
        )
    }
}