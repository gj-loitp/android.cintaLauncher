package com.roy93group.launcher.ui.feed.items.viewHolders.home

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.ext.setSafeOnClickListener
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.launcher.providers.feed.notification.NotificationService
import com.roy93group.launcher.ui.LauncherActivity
import com.roy93group.views.LinearLayoutManagerWrapper
import io.posidon.android.conveniencelib.getStatusBarHeight
import kotlinx.android.synthetic.main.view_feed_home.view.*

class HomeViewHolder(
    val launcherActivity: LauncherActivity,
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {
    private var isForceColorIcon = C.getForceColorIcon()
    private var colorPrimary = C.getColorPrimary()
    private val notificationIconsAdapter = NotificationIconsAdapter()

    private val rvNotificationIconList: RecyclerView = itemView.rvNotificationIconList.apply {
        layoutManager = LinearLayoutManagerWrapper(
            /* context = */ itemView.context,
            /* orientation = */RecyclerView.HORIZONTAL,
            /* reverseLayout = */false
        )
        adapter = notificationIconsAdapter
    }

    init {
        NotificationService.setOnUpdate(javaClass.name) {
            itemView.post {
                updateNotificationIcons(
                    isForceColorIcon = isForceColorIcon,
                    colorPrimary = colorPrimary
                )
            }
        }
        itemView.llClockContainer.setPadding(
            /* left = */ 0,
            /* top = */itemView.context.getStatusBarHeight(),
            /* right = */0,
            /* bottom = */0
        )
    }

    fun updateNotificationIcons(
        isForceColorIcon: Boolean,
        colorPrimary: Int
    ) {
        val icons = NotificationService.notifications.groupBy {
            it.sourceIcon?.constantState
        }.mapNotNull {
            it.key?.newDrawable()
        }
        itemView.tvNotificationIconText.setTextColor(colorPrimary)
        if (icons.isEmpty()) {
            itemView.tvNotificationIconText.isVisible = false
            rvNotificationIconList.isVisible = false
        } else {
            itemView.tvNotificationIconText.isVisible = true
            rvNotificationIconList.isVisible = true

            this.isForceColorIcon = isForceColorIcon
            notificationIconsAdapter.updateItems(
                items = icons,
                isForceColorIcon = isForceColorIcon
            )
            itemView.tvNotificationIconText.text = itemView.resources.getQuantityString(
                /* id = */ R.plurals.x_notifications,
                /* quantity = */ icons.size,
                /* ...formatArgs = */ icons.size
            )
        }
    }
}

private var popupX = 0f
private var popupY = 0f

@SuppressLint("ClickableViewAccessibility")
fun bindHomeViewHolder(
    holder: HomeViewHolder,
    isForceColorIcon: Boolean,
    onClickClock: ((Unit) -> Unit),
    onClickCalendar: ((Unit) -> Unit)
) {
    val colorPrimary = C.getColorPrimary()
//    val colorBackground = C.getColorBackground()

    holder.updateNotificationIcons(isForceColorIcon = isForceColorIcon, colorPrimary = colorPrimary)

    holder.itemView.tvTime.apply {
        setTextColor(colorPrimary)
        setSafeOnClickListener {
            onClickClock.invoke(Unit)
        }
    }
    holder.itemView.tvWeekDay.apply {
        setTextColor(colorPrimary)
        setSafeOnClickListener {
            onClickCalendar.invoke(Unit)
        }
    }
    holder.itemView.tvDate.apply {
        setTextColor(colorPrimary)
        setSafeOnClickListener {
            onClickCalendar.invoke(Unit)
        }
    }

    holder.itemView.setOnTouchListener { _, e ->
        when (e.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                popupX = e.rawX
                popupY = e.rawY
            }
        }
        false
    }

}
