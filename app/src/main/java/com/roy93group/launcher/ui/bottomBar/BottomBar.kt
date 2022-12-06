package com.roy93group.launcher.ui.bottomBar

import android.content.ClipData
import android.content.Intent
import android.view.DragEvent
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LScreenUtil
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.launcher.ui.LauncherActivity
import com.roy93group.launcher.ui.pinned.PinnedItemsAdapter
import com.roy93group.launcher.ui.popup.drawer.DrawerLongPressPopup
import com.roy93group.launcher.ui.view.scrollbar.Scrollbar
import com.roy93group.launcher.ui.view.scrollbar.ScrollbarIconView
import com.roy93group.lookerupper.ui.a.SearchActivity
import io.posidon.android.conveniencelib.getNavigationBarHeight

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class BottomBar(val launcherActivity: LauncherActivity) {

    val scrollBar: Scrollbar get() = appDrawerIcon.scrollBar

    val cvSearchBarContainer: CardView =
        launcherActivity.findViewById<MaterialCardView>(R.id.cvSearchBarContainer).apply {
            setCardBackgroundColor(C.COLOR_PRIMARY_2)
            strokeColor = C.COLOR_PRIMARY
            setOnClickListener {
                val context = it.context
                context.startActivity(
                    Intent(
                        context,
                        SearchActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
                LActivityUtil.tranIn(context)
            }
            setOnDragListener(::onDrag)
        }

    val appDrawerIcon: ScrollbarIconView =
        cvSearchBarContainer.findViewById<ScrollbarIconView>(R.id.appDrawerIcon).apply {
            appDrawer = launcherActivity.appDrawer
        }
    val cvBackButtonContainer: FloatingActionButton =
        launcherActivity.findViewById<FloatingActionButton>(R.id.fabBack).apply {
            this.setOnClickListener {
                launcherActivity.appDrawer.close()
            }
        }
    val cvSetting: FloatingActionButton =
        launcherActivity.findViewById<FloatingActionButton>(R.id.fabSetting).apply {
            this.setOnClickListener {
                DrawerLongPressPopup.show(
                    launcherActivity = launcherActivity,
                    parent = this,
                    touchX = LScreenUtil.screenWidth / 2f,
                    touchY = LScreenUtil.screenHeight / 2f,
                    navbarHeight = launcherActivity.getNavigationBarHeight(),
                    settings = launcherActivity.settings,
                    reloadApps = launcherActivity::loadApps
                )
            }
        }

    private val pinnedAdapter = PinnedItemsAdapter(
        launcherActivity = launcherActivity,
        launcherContext = launcherActivity.launcherContext
    )
    private val pinnedRecycler: RecyclerView =
        cvSearchBarContainer.findViewById<RecyclerView>(R.id.rvPinned).apply {
            layoutManager = LinearLayoutManager(
                /* context = */ launcherActivity,
                /* orientation = */RecyclerView.HORIZONTAL,
                /* reverseLayout = */false
            )
            adapter = pinnedAdapter
        }

    fun onAppsLoaded() {
        updatePinned()
    }

    private fun showDropTarget(i: Int) {
        if (i != -1) pinnedRecycler.isVisible = true
        pinnedAdapter.showDropTarget(i)
    }

    private fun getPinnedItemIndex(
        x: Float,
        y: Float
    ): Int {
        val i =
            ((x - (cvSearchBarContainer.width - pinnedRecycler.width) / 2) * pinnedAdapter.itemCount / pinnedRecycler.width).toInt()
                .coerceAtLeast(-1)
        return if (i >= pinnedAdapter.itemCount) -1 else i
    }

    private fun onDrop(
        v: View,
        i: Int,
        clipData: ClipData
    ) {
        pinnedAdapter.onDrop(v = v, i = i, clipData = clipData)
    }

    private fun updatePinned() {
        pinnedAdapter.updateItems(launcherActivity.launcherContext.appManager.pinnedItems)
    }

    private fun onDrag(
        v: View,
        event: DragEvent
    ): Boolean {
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED,
            DragEvent.ACTION_DRAG_ENTERED,
            DragEvent.ACTION_DRAG_LOCATION -> {
                val i = getPinnedItemIndex(event.x, event.y)
                val pinnedItems = launcherActivity.launcherContext.appManager.pinnedItems
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
                onDrop(v = v, i = i, clipData = event.clipData)
            }
        }
        return true
    }
}
