package com.roy93group.launcher.ui.drawer.viewHolders

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.ext.getColorBackground
import com.roy93group.ext.getColorPrimary
import com.roy93group.launcher.ui.drawer.AppDrawerAdapter
import com.roy93group.launcher.ui.drawer.AppDrawerAdapter.Companion.SECTION_HEADER
import kotlinx.android.synthetic.main.view_app_drawer_section_header.view.*

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class SectionHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class SectionHeaderItem(override val label: String) : AppDrawerAdapter.DrawerItem {
    override fun getItemViewType() = SECTION_HEADER
}

@SuppressLint("ClickableViewAccessibility")
fun bindSectionHeaderViewHolder(
    holder: SectionHeaderViewHolder,
    item: SectionHeaderItem,
    isHighlighted: Boolean,
) {
    val colorPrimary = getColorPrimary()
    val colorBackground = getColorBackground()

    holder.itemView.text.text = item.label
    if (isHighlighted) {
        holder.itemView.setBackgroundColor(colorPrimary)
        holder.itemView.text.setTextColor(colorBackground)
    } else {
        holder.itemView.setBackgroundColor(Color.TRANSPARENT)
        holder.itemView.text.setTextColor(colorPrimary)
    }
}
