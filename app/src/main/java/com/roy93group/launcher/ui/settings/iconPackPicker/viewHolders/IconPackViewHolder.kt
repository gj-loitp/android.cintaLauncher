package com.roy93group.launcher.ui.settings.iconPackPicker.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.launcher.ui.settings.iconPackPicker.IconPackPickerActivity

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class IconPackViewHolder(
    itemView: View,
    val type: Int
) : RecyclerView.ViewHolder(itemView) {
    val icon: ImageView = itemView.findViewById(R.id.icon)
    val text: TextView = itemView.findViewById<TextView>(R.id.text).apply {
        setTextColor(C.COLOR_PRIMARY_2)
    }

    fun bind(iconPack: IconPackPickerActivity.IconPack) {
        text.text = iconPack.label
        icon.setImageDrawable(iconPack.icon)
    }
}
