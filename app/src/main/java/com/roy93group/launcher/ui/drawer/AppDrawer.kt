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
import androidx.recyclerview.widget.RecyclerView
import cdflynn.android.library.turn.TurnLayoutManager
import com.loitp.core.utilities.LUIUtil
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.launcher.data.items.App
import com.roy93group.launcher.ui.LauncherActivity
import com.roy93group.launcher.ui.popup.appItem.ItemLongPress
import com.roy93group.launcher.ui.view.scrollbar.Scrollbar
import com.roy93group.views.BottomSheetCustomizeAppDrawer
import io.posidon.android.conveniencelib.getStatusBarHeight
import io.posidon.android.conveniencelib.onEnd

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class AppDrawer(
    val launcherActivity: LauncherActivity
) {

    private val flAppDrawerContainer: View =
        launcherActivity.findViewById(R.id.flAppDrawerContainer)
    private val adapter = AppDrawerAdapter(launcherActivity)
    private val rvApp: RecyclerView = flAppDrawerContainer.findViewById(R.id.rvApp)
    private var layoutManager: TurnLayoutManager? = null

    private var seekRadiusValue = C.getSeekRadiusValue()
    private var seekPeekValue = C.getSeekPeekValue()
    private var orientationValue = C.getOrientationValue()
    private var gravityValue = C.getGravityValue()
    private var isCheckedValue = C.getChecked()

    @SuppressLint("ClickableViewAccessibility")
    fun init() {
        LUIUtil.setScrollChange(
            recyclerView = rvApp,
            onTop = {
                C.goToSearchScreen(launcherActivity)
            },
            onBottom = {},
            onScrolled = {}
        )

        val tmpGravity = if (gravityValue == 0) {
            TurnLayoutManager.Gravity.START
        } else {
            TurnLayoutManager.Gravity.END
        }
        val tmpOrientationValue = if (orientationValue == 0) {
            TurnLayoutManager.HORIZONTAL
        } else {
            TurnLayoutManager.VERTICAL
        }
        layoutManager = TurnLayoutManager(
            /* context = */ flAppDrawerContainer.context,
            /* gravity = */ tmpGravity,
            /* orientation = */ tmpOrientationValue,
            /* radius = */ seekRadiusValue,
            /* peekDistance = */ seekPeekValue,
            /* rotate = */ isCheckedValue
        )
        rvApp.layoutManager = layoutManager
        rvApp.adapter = adapter
    }

    private var appSections: List<List<App>>? = null

    fun update(
        scrollBar: Scrollbar,
        appSections: List<List<App>>
    ) {
        this.appSections = appSections
        adapter.updateAppSections(
            appSections = appSections,
            activity = launcherActivity,
            controller = scrollBar.controller
        )
        scrollBar.postInvalidate()
        flAppDrawerContainer.postInvalidate()
        scrollBar.recycler = this@AppDrawer.rvApp
    }

    val isOpen get() = flAppDrawerContainer.isVisible

    private var currentValueAnimator: ValueAnimator? = null

    fun open() {
        launcherActivity.bottomBar.cvBackButtonContainer.isVisible = true
        launcherActivity.bottomBar.cvSetting.isVisible = true
        if (isOpen) return
        ItemLongPress.currentPopup?.dismiss()
        val sbh = launcherActivity.getStatusBarHeight()
        rvApp.setPadding(
            /* left = */ rvApp.paddingLeft,
            /* top = */
            sbh,
            /* right = */
            rvApp.paddingRight,
            /* bottom = */
            launcherActivity.bottomBar.cvSearchBarContainer.measuredHeight + launcherActivity.bottomBar.cvSearchBarContainer.marginBottom + launcherActivity.bottomBar.cvSearchBarContainer.marginTop
        )
        flAppDrawerContainer.isVisible = true
        launcherActivity.rvFeed.stopScroll()
        launcherActivity.feedProfiles.rvFeedFilters.animate()
            .alpha(0f)
            .setDuration(100)
            .setInterpolator(DecelerateInterpolator())
            .onEnd {
                launcherActivity.rvFeed.isVisible = false
            }
        launcherActivity.rvFeed.animate()
            .alpha(0f)
            .scaleX(1.1f)
            .scaleY(1.1f)
            .setStartDelay(0)
            .setDuration(100)
            .setInterpolator(AccelerateInterpolator())
            .onEnd {
                launcherActivity.rvFeed.isInvisible = true
            }
        flAppDrawerContainer.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setStartDelay(0)
            .setDuration(100)
            .setInterpolator(DecelerateInterpolator())
            .onEnd {
                flAppDrawerContainer.isVisible = true
            }
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

    fun close() {
        launcherActivity.bottomBar.cvBackButtonContainer.isVisible = false
        launcherActivity.bottomBar.cvSetting.isVisible = false
        if (!isOpen) return
        ItemLongPress.currentPopup?.dismiss()
        launcherActivity.feedProfiles.rvFeedFilters.isVisible = true
        launcherActivity.feedProfiles.rvFeedFilters.animate()
            .alpha(1f)
            .setDuration(100)
            .setInterpolator(DecelerateInterpolator())
            .onEnd {
                launcherActivity.rvFeed.isVisible = true
            }
        launcherActivity.rvFeed.isInvisible = false
        launcherActivity.rvFeed.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setStartDelay(0)
            .setDuration(100)
            .setInterpolator(DecelerateInterpolator())
            .onEnd {
                launcherActivity.rvFeed.isInvisible = false
            }
        flAppDrawerContainer.animate()
            .alpha(0f)
            .scaleX(1.1f)
            .scaleY(1.1f)
            .setStartDelay(0)
            .setDuration(100)
            .setInterpolator(AccelerateInterpolator())
            .onEnd {
                flAppDrawerContainer.isVisible = false
            }
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

    fun customizeAppDrawer() {
        if (layoutManager == null) {
            return
        }
        val fragment = BottomSheetCustomizeAppDrawer(
            seekRadiusValue = seekRadiusValue,
            seekPeekValue = seekPeekValue,
            gravityValue = gravityValue,
            orientationValue = orientationValue,
            isCheckedValue = isCheckedValue,
            isCancelableFragment = true,
            onDismiss = {
                //do nothing
            },
            onSeekRadiusValue = { value ->
                seekRadiusValue = value
                layoutManager?.setRadius(seekRadiusValue)
                C.setSeekRadiusValue(seekRadiusValue)
            },
            onSeekPeekValue = { value ->
                seekPeekValue = value
                layoutManager?.setPeekDistance(seekPeekValue)
                C.setSeekPeekValue(seekPeekValue)
            },
            onOrientation = { value ->
                orientationValue = value
                if (orientationValue == 0) {
                    layoutManager?.orientation = TurnLayoutManager.HORIZONTAL
                } else {
                    layoutManager?.orientation = TurnLayoutManager.VERTICAL
                }
                C.setOrientationValue(orientationValue)
            },
            onGravity = { value ->
                gravityValue = value
                if (gravityValue == 0) {
                    layoutManager?.setGravity(TurnLayoutManager.Gravity.START)
                } else {
                    layoutManager?.setGravity(TurnLayoutManager.Gravity.END)
                }
                C.setGravityValue(gravityValue)
            },
            onRotate = { value ->
                isCheckedValue = value
                layoutManager?.setRotate(isCheckedValue)
                C.setChecked(isCheckedValue)
            },
            onResetAllValue = {
                seekRadiusValue = 0
                seekPeekValue = 0
                orientationValue = 1
                gravityValue = 0
                isCheckedValue = false

                layoutManager?.apply {
                    setRadius(seekRadiusValue)
                    setPeekDistance(seekPeekValue)
                    orientation = if (orientationValue == 0) {
                        TurnLayoutManager.HORIZONTAL
                    } else {
                        TurnLayoutManager.VERTICAL
                    }
                    if (gravityValue == 0) {
                        setGravity(TurnLayoutManager.Gravity.START)
                    } else {
                        setGravity(TurnLayoutManager.Gravity.END)
                    }
                    setRotate(isCheckedValue)
                }

                C.setSeekRadiusValue(seekRadiusValue)
                C.setSeekPeekValue(seekPeekValue)
                C.setOrientationValue(orientationValue)
                C.setGravityValue(gravityValue)
                C.setChecked(isCheckedValue)
            }
        )
        fragment.show(launcherActivity.supportFragmentManager, fragment.tag)
    }
}
