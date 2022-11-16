package com.roy93group.launcher.ui.drawer.viewHolders

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loitpcore.core.utilities.LAppResource
import com.roy93group.launcher.R
import com.roy93group.launcher.ui.LauncherActivity
import com.roy93group.launcher.ui.drawer.AppDrawerAdapter
import com.roy93group.launcher.ui.drawer.AppDrawerAdapter.Companion.SECTION_HEADER
import com.roy93group.launcher.ui.popup.drawer.DrawerLongPressPopup
import io.posidon.android.conveniencelib.getNavigationBarHeight

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class SectionHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val text: TextView = itemView.findViewById(R.id.text)
}

class SectionHeaderItem(override val label: String) : AppDrawerAdapter.DrawerItem {
    override fun getItemViewType() = SECTION_HEADER
}

@SuppressLint("ClickableViewAccessibility")
fun bindSectionHeaderViewHolder(
    holder: SectionHeaderViewHolder,
    item: SectionHeaderItem,
    isHighlighted: Boolean,
    launcherActivity: LauncherActivity
) {
    holder.text.text = item.label
    if (isHighlighted) {
        holder.itemView.setBackgroundColor(LAppResource.getColor(R.color.white))
        holder.text.setTextColor(LAppResource.getColor(R.color.colorPrimary))
    } else {
        holder.itemView.setBackgroundColor(LAppResource.getColor(R.color.transparent))
        holder.text.setTextColor(LAppResource.getColor(R.color.white))
    }

    var x = 0f
    var y = 0f
    holder.itemView.setOnTouchListener { _, e ->
        when (e.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                x = e.rawX
                y = e.rawY
            }
        }
        false
    }
    holder.itemView.setOnLongClickListener {
        DrawerLongPressPopup.show(
            parent = holder.itemView,
            touchX = x,
            touchY = y,
            navbarHeight = launcherActivity.getNavigationBarHeight(),
            settings = launcherActivity.settings,
            reloadApps = launcherActivity::loadApps
        )
        true
    }
}
