package com.roy93group.cintalauncher.ui.settings.iconPackPicker.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.cintalauncher.R
import com.roy93group.cintalauncher.providers.color.theme.ColorTheme
import com.roy93group.cintalauncher.ui.settings.iconPackPicker.IconPackPickerActivity

class IconPackViewHolder(itemView: View, val type: Int) : RecyclerView.ViewHolder(itemView) {
    val icon: ImageView = itemView.findViewById(R.id.icon)
    val text: TextView = itemView.findViewById(R.id.text)

    fun bind(iconPack: IconPackPickerActivity.IconPack) {
        text.text = iconPack.label
        icon.setImageDrawable(iconPack.icon)
        text.setTextColor(ColorTheme.uiDescription)
    }
}
