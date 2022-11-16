package com.roy93group.launcher.ui.drawer

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R
import com.roy93group.launcher.data.items.App
import com.roy93group.launcher.ui.LauncherActivity
import com.roy93group.launcher.ui.popup.appItem.ItemLongPress
import com.roy93group.launcher.ui.popup.drawer.DrawerLongPressPopup
import com.roy93group.launcher.ui.view.scrollbar.Scrollbar
import io.posidon.android.conveniencelib.getNavigationBarHeight
import io.posidon.android.conveniencelib.getStatusBarHeight
import io.posidon.android.conveniencelib.onEnd
import kotlin.math.abs

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class AppDrawer(
    val activity: LauncherActivity
) {

    companion object {
        const val COLUMNS = 3
        const val WIDTH_TO_HEIGHT = 5f / 4f
    }

    val view: View = activity.findViewById(R.id.flAppDrawerContainer)
    private val adapter = AppDrawerAdapter(activity)
    private val recycler: RecyclerView = view.findViewById(R.id.rvApp)
    private var popupX = 0f
    private var popupY = 0f

    @SuppressLint("ClickableViewAccessibility")
    fun init() {
        recycler.layoutManager =
            GridLayoutManager(
                /* context = */ view.context,
                /* spanCount = */COLUMNS,
                /* orientation = */RecyclerView.VERTICAL,
                /* reverseLayout = */false
            ).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(i: Int): Int {
                        return when (adapter.getItemViewType(i)) {
                            AppDrawerAdapter.APP_ITEM -> 1
                            AppDrawerAdapter.SECTION_HEADER -> COLUMNS
                            else -> -1
                        }
                    }
                }
            }
        recycler.adapter = adapter

        val onLongPress = Runnable {
            recycler.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            DrawerLongPressPopup.show(
                parent = recycler,
                touchX = popupX,
                touchY = popupY,
                navbarHeight = activity.getNavigationBarHeight(),
                settings = activity.settings,
                reloadApps = activity::loadApps
            )
        }
        var lastRecyclerViewDownTouchEvent: MotionEvent? = null
        recycler.setOnTouchListener { v, event ->
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    popupX = event.rawX
                    popupY = event.rawY
                    if (recycler.findChildViewUnder(event.x, event.y) == null) {
                        v.handler.removeCallbacks(onLongPress)
                        lastRecyclerViewDownTouchEvent = event
                        v.handler.postDelayed(
                            onLongPress,
                            ViewConfiguration.getLongPressTimeout().toLong()
                        )
                    }
                }
                MotionEvent.ACTION_MOVE -> if (lastRecyclerViewDownTouchEvent != null) {
                    val xDelta = abs(popupX - event.x)
                    val yDelta = abs(popupY - event.y)
                    if (xDelta >= 10 || yDelta >= 10) {
                        v.handler.removeCallbacks(onLongPress)
                        lastRecyclerViewDownTouchEvent = null
                    }
                }
                MotionEvent.ACTION_CANCEL,
                MotionEvent.ACTION_UP -> {
                    v.handler.removeCallbacks(onLongPress)
                    lastRecyclerViewDownTouchEvent = null
                }
            }
            false
        }
    }

    private var appSections: List<List<App>>? = null

    fun update(
        scrollBar: Scrollbar,
        appSections: List<List<App>>
    ) {
        this.appSections = appSections
        adapter.updateAppSections(
            appSections = appSections,
            activity = activity,
            controller = scrollBar.controller
        )
        scrollBar.postInvalidate()
        view.postInvalidate()
        scrollBar.recycler = this@AppDrawer.recycler
    }

//    fun updateColorTheme() {
//        view.background = ColorDrawable(ColorTheme.uiBG and 0xffffff or 0xbb000000.toInt())
//        adapter.notifyItemRangeChanged(0, adapter.itemCount)
//    }

    val isOpen get() = view.isVisible

    private var currentValueAnimator: ValueAnimator? = null

    fun open(v: View) {
        activity.bottomBar.appDrawerCloseIconContainer.isVisible = true
        if (isOpen) return
        ItemLongPress.currentPopup?.dismiss()
        val sbh = v.context.getStatusBarHeight()
        recycler.setPadding(
            recycler.paddingLeft,
            sbh,
            recycler.paddingRight,
            activity.bottomBar.view.measuredHeight + activity.bottomBar.view.marginBottom + activity.bottomBar.view.marginTop
        )
        view.isVisible = true
        activity.feedRecycler.stopScroll()
        activity.feedProfiles.feedFilterRecycler.animate()
            .alpha(0f)
            .setDuration(100)
            .setInterpolator(DecelerateInterpolator())
            .onEnd { activity.feedRecycler.isVisible = false }
        activity.feedRecycler.animate()
            .alpha(0f)
            .scaleX(1.1f)
            .scaleY(1.1f)
            .setStartDelay(0)
            .setDuration(100)
            .setInterpolator(AccelerateInterpolator())
            .onEnd { activity.feedRecycler.isInvisible = true }
        view.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setStartDelay(0)
            .setDuration(100)
            .setInterpolator(DecelerateInterpolator())
            .onEnd { view.isVisible = true }
        val s = currentValueAnimator?.animatedValue as Float? ?: 0f
        currentValueAnimator?.cancel()
        currentValueAnimator = ValueAnimator.ofFloat(s, 3f).apply {
            interpolator = DecelerateInterpolator()
            duration = 200
            onEnd {
                currentValueAnimator = null
            }
            start()
        }
    }

    @Suppress("unused")
    fun close(v: View? = null) {
        activity.bottomBar.appDrawerCloseIconContainer.isVisible = false
        if (!isOpen) return
        ItemLongPress.currentPopup?.dismiss()
        activity.feedProfiles.feedFilterRecycler.isVisible = true
        activity.feedProfiles.feedFilterRecycler.animate()
            .alpha(1f)
            .setDuration(100)
            .setInterpolator(DecelerateInterpolator())
            .onEnd { activity.feedRecycler.isVisible = true }
        activity.feedRecycler.isInvisible = false
        activity.feedRecycler.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setStartDelay(0)
            .setDuration(100)
            .setInterpolator(DecelerateInterpolator())
            .onEnd { activity.feedRecycler.isInvisible = false }
        view.animate()
            .alpha(0f)
            .scaleX(1.1f)
            .scaleY(1.1f)
            .setStartDelay(0)
            .setDuration(100)
            .setInterpolator(AccelerateInterpolator())
            .onEnd { view.isVisible = false }
        val s = currentValueAnimator?.animatedValue as Float? ?: 3f
        currentValueAnimator?.cancel()
        currentValueAnimator = ValueAnimator.ofFloat(s, 0f).apply {
            interpolator = AccelerateInterpolator()
            duration = 135
            onEnd {
                currentValueAnimator = null
            }
            start()
        }
    }
}
