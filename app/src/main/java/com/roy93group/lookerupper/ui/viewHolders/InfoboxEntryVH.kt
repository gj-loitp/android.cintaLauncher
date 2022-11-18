package com.roy93group.lookerupper.ui.viewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R

/**
 * Updated by Loitp on 2022.12.18
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class InfoboxEntryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val label: TextView = itemView.findViewById(R.id.label)
    val value: TextView = itemView.findViewById(R.id.value)
    val separator: View = itemView.findViewById(R.id.separator)
}
