package com.roy93group.launcher.ui.drawer.viewHolders

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R
import com.roy93group.launcher.providers.color.theme.ColorTheme
import com.roy93group.launcher.ui.LauncherActivity
import com.roy93group.launcher.ui.drawer.AppDrawerAdapter
import com.roy93group.launcher.ui.drawer.AppDrawerAdapter.Companion.SECTION_HEADER
import com.roy93group.launcher.ui.popup.drawer.DrawerLongPressPopup
import com.roy93group.launcher.ui.view.recycler.HighlightSectionIndexer
import io.posidon.android.conveniencelib.getNavigationBarHeight

class SectionHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val textView: TextView = itemView.findViewById(R.id.text)

    val highlightDrawable = HighlightSectionIndexer.createHighlightDrawable(
        context = itemView.context,
        accentColor = 0
    )
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
    holder.itemView.background = if (isHighlighted) holder.highlightDrawable else null
    holder.textView.text = item.label
    holder.textView.setTextColor(ColorTheme.cardTitle)
    holder.highlightDrawable.paint.color = ColorTheme.accentColor and 0xffffff or 0x55000000

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
