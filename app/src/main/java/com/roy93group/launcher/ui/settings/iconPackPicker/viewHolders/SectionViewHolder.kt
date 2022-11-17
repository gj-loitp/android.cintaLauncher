package com.roy93group.launcher.ui.settings.iconPackPicker.viewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val text: TextView = itemView.findViewById(R.id.text)

    fun bind(string: String) {
        text.text = string
    }
}
