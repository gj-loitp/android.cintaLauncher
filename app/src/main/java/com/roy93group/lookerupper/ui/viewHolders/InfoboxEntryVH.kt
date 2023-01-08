package com.roy93group.lookerupper.ui.viewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.ext.C
import com.roy93group.ext.getColorPrimary
import kotlinx.android.synthetic.main.view_search_result_answer_info_box_entry.view.*

/**
 * Updated by Loitp on 2022.12.18
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class InfoboxEntryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val colorPrimary = getColorPrimary()

    val label: TextView = itemView.label.apply {
        setTextColor(colorPrimary)
    }
    val value: TextView = itemView.value.apply {
        setTextColor(colorPrimary)
    }

}
