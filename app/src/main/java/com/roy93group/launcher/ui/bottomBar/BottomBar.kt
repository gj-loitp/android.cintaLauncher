package com.roy93group.launcher.ui.bottomBar

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Intent
import android.content.res.ColorStateList
import android.view.DragEvent
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R
import com.roy93group.launcher.providers.color.theme.ColorTheme
import com.roy93group.launcher.ui.LauncherActivity
import com.roy93group.launcher.ui.pinned.PinnedItemsAdapter
import com.roy93group.launcher.ui.view.SeeThoughView
import com.roy93group.launcher.ui.view.scrollbar.Scrollbar
import com.roy93group.launcher.ui.view.scrollbar.ScrollbarIconView
import com.roy93group.lookerupper.ui.a.SearchActivity

class BottomBar(val activity: LauncherActivity) {

    val scrollBar: Scrollbar get() = appDrawerIcon.scrollBar

    val view: CardView = activity.findViewById<CardView>(R.id.search_bar_container).apply {
        setOnClickListener {
            val context = it.context
            context.startActivity(
                Intent(
                    context,
                    SearchActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
        setOnDragListener(::onDrag)
    }
    private val searchIcon: ImageView = view.findViewById(R.id.search_bar_icon)
    val appDrawerIcon: ScrollbarIconView =
        view.findViewById<ScrollbarIconView>(R.id.app_drawer_icon).apply {
            appDrawer = activity.appDrawer
        }
    val appDrawerCloseIconContainer: CardView =
        activity.findViewById<CardView>(R.id.back_button_container)

    @SuppressLint("ClickableViewAccessibility")
    val appDrawerCloseIcon: ImageView =
        appDrawerCloseIconContainer.findViewById<ImageView>(R.id.back_button).apply {
            setOnClickListener(activity.appDrawer::close)
        }
    val blurBG: SeeThoughView = view.findViewById(R.id.search_bar_blur_bg)
    private val pinnedAdapter = PinnedItemsAdapter(
        launcherActivity = activity,
        launcherContext = activity.launcherContext
    )
    private val pinnedRecycler: RecyclerView =
        view.findViewById<RecyclerView>(R.id.pinned_recycler).apply {
            layoutManager = LinearLayoutManager(
                /* context = */ activity,
                /* orientation = */RecyclerView.HORIZONTAL,
                /* reverseLayout = */false
            )
            adapter = pinnedAdapter
        }

    fun updateColorTheme() {
        view.setCardBackgroundColor(ColorTheme.searchBarBG)
        appDrawerCloseIconContainer.setCardBackgroundColor(ColorTheme.searchBarBG)
        searchIcon.imageTintList = ColorStateList.valueOf(ColorTheme.searchBarFG)
        appDrawerCloseIcon.imageTintList = ColorStateList.valueOf(ColorTheme.searchBarFG)
        appDrawerIcon.imageTintList = ColorStateList.valueOf(ColorTheme.searchBarFG)
    }

    fun onAppsLoaded() {
        updatePinned()
    }

    private fun showDropTarget(i: Int) {
        if (i != -1) pinnedRecycler.isVisible = true
        pinnedAdapter.showDropTarget(i)
    }

    private fun getPinnedItemIndex(x: Float, y: Float): Int {
        val i =
            ((x - (view.width - pinnedRecycler.width) / 2) * pinnedAdapter.itemCount / pinnedRecycler.width).toInt()
                .coerceAtLeast(-1)
        return if (i >= pinnedAdapter.itemCount) -1 else i
    }

    private fun onDrop(v: View, i: Int, clipData: ClipData) {
        pinnedAdapter.onDrop(v = v, i = i, clipData = clipData)
    }

    private fun updatePinned() {
        pinnedAdapter.updateItems(activity.launcherContext.appManager.pinnedItems)
    }

    private fun onDrag(v: View, event: DragEvent): Boolean {
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED,
            DragEvent.ACTION_DRAG_ENTERED,
            DragEvent.ACTION_DRAG_LOCATION -> {
                val i = getPinnedItemIndex(event.x, event.y)
                val pinnedItems = activity.launcherContext.appManager.pinnedItems
                showDropTarget(if (i == -1) pinnedItems.size else i)
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                updatePinned()
                showDropTarget(-1)
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                showDropTarget(-1)
            }
            DragEvent.ACTION_DROP -> {
                val i = getPinnedItemIndex(event.x, event.y)
                if (i == -1)
                    return false
                onDrop(v, i, event.clipData)
            }
        }
        return true
    }
}