package com.roy93group.cintalauncher.ui.feed.items.viewHolders.home

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.cintalauncher.LauncherContext
import com.roy93group.cintalauncher.R
import com.roy93group.cintalauncher.providers.color.theme.ColorTheme
import com.roy93group.cintalauncher.providers.feed.notification.NotificationService
import com.roy93group.cintalauncher.ui.LauncherActivity
import com.roy93group.cintalauncher.ui.popup.home.HomeLongPressPopup
import io.posidon.android.conveniencelib.getNavigationBarHeight
import io.posidon.android.conveniencelib.getStatusBarHeight

class HomeViewHolder(
    val launcherActivity: LauncherActivity,
    val launcherContext: LauncherContext,
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    private val clockContainer = itemView.findViewById<View>(R.id.clock_container)
    val weekDay: TextView = clockContainer.findViewById(R.id.week_day)
    val time: TextView = clockContainer.findViewById(R.id.time)
    val date: TextView = clockContainer.findViewById(R.id.date)
    private val notificationIconsAdapter = NotificationIconsAdapter()
    private val notificationIconsContainer =
        itemView.findViewById<View>(R.id.notification_icon_container)

    @Suppress("unused")
    val notificationIconsRecycler: RecyclerView =
        itemView.findViewById<RecyclerView>(R.id.notification_icon_list).apply {
            layoutManager = LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
            adapter = notificationIconsAdapter
        }
    val notificationIconsText: TextView = itemView.findViewById(R.id.notification_icon_text)
    val vertical: LinearLayout = itemView.findViewById(R.id.vertical)

    init {
        NotificationService.setOnUpdate(javaClass.name) { itemView.post(::updateNotificationIcons) }
        clockContainer.setPadding(
            /* left = */ 0,
            /* top = */itemView.context.getStatusBarHeight(),
            /* right = */0,
            /* bottom = */0
        )
    }

    fun updateNotificationIcons() {
        val icons = NotificationService.notifications.groupBy {
            it.sourceIcon?.constantState
        }.mapNotNull { it.key?.newDrawable() }
        if (icons.isEmpty()) {
            notificationIconsContainer.isVisible = false
        } else {
            notificationIconsContainer.isVisible = true
            if (notificationIconsAdapter.updateItems(icons)) {
                notificationIconsText.text =
                    itemView.resources.getQuantityString(
                        R.plurals.x_notifications,
                        icons.size,
                        icons.size
                    )
            }
        }
    }
}

private var popupX = 0f
private var popupY = 0f

@SuppressLint("ClickableViewAccessibility")
fun bindHomeViewHolder(
    holder: HomeViewHolder
) {
    holder.updateNotificationIcons()
    holder.time.setTextColor(ColorTheme.uiTitle)
    holder.date.setTextColor(ColorTheme.uiDescription)
    holder.weekDay.setTextColor(ColorTheme.uiDescription)
    holder.notificationIconsText.setTextColor(ColorTheme.uiTitle)
    holder.itemView.setOnTouchListener { _, e ->
        when (e.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                popupX = e.rawX
                popupY = e.rawY
            }
        }
        false
    }
    holder.itemView.setOnLongClickListener {
        HomeLongPressPopup.show(
            parent = it,
            touchX = popupX,
            touchY = popupY,
            navbarHeight = holder.launcherActivity.getNavigationBarHeight(),
            settings = holder.launcherContext.settings,
            reloadColorTheme = holder.launcherActivity::reloadColorThemeSync
        )
        true
    }
}
