package com.roy93group.launcher.ui.drawer.viewHolders

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.app.C
import com.roy93group.launcher.ui.LauncherActivity
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

private val colorPrimary = C.getColorPrimary()
private val colorBackground = C.getColorBackground()

@SuppressLint("ClickableViewAccessibility")
fun bindSectionHeaderViewHolder(
    holder: SectionHeaderViewHolder,
    item: SectionHeaderItem,
    isHighlighted: Boolean,
    launcherActivity: LauncherActivity,
    isDisplayAppIcon: Boolean,
) {
    holder.itemView.text.text = item.label
    if (isHighlighted) {
        holder.itemView.setBackgroundColor(colorPrimary)
        holder.itemView.text.setTextColor(colorBackground)
    } else {
        holder.itemView.setBackgroundColor(Color.TRANSPARENT)
        holder.itemView.text.setTextColor(colorPrimary)
    }

//    var x = 0f
//    var y = 0f
//    holder.itemView.setOnTouchListener { _, e ->
//        when (e.action and MotionEvent.ACTION_MASK) {
//            MotionEvent.ACTION_DOWN -> {
//                x = e.rawX
//                y = e.rawY
//            }
//        }
//        false
//    }
}
