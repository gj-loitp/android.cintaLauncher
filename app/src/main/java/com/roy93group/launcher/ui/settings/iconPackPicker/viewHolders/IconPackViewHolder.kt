package com.roy93group.launcher.ui.settings.iconPackPicker.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.ext.setDrawableTint
import com.roy93group.ext.C
import com.roy93group.ext.getColorPrimary
import com.roy93group.launcher.ui.settings.iconPackPicker.IconPackPickerActivity
import kotlinx.android.synthetic.main.view_icon_packs_item.view.*

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
    fun bind(iconPack: IconPackPickerActivity.IconPack) {
        val colorPrimary = getColorPrimary()

        itemView.text.apply {
            setTextColor(colorPrimary)
            this.setDrawableTint(colorPrimary)
            text = iconPack.label
        }
        itemView.icon.setImageDrawable(iconPack.icon)
    }
}
