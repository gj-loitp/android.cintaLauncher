package com.roy93group.launcher.ui.settings.iconPackPicker.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.app.C
import kotlinx.android.synthetic.main.view_icon_packs_section.view.*

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(string: String) {

        itemView.text.apply {
            setTextColor(C.getColorPrimary())
            text = string
        }
    }
}
