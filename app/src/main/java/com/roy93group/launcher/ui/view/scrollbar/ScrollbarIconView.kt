package com.roy93group.launcher.ui.view.scrollbar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.PopupWindow
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.launcher.storage.ScrollbarControllerSetting
import com.roy93group.launcher.storage.ScrollbarControllerSetting.scrollbarController
import com.roy93group.launcher.storage.Settings
import com.roy93group.launcher.ui.drawer.AppDrawer
import com.roy93group.launcher.ui.view.scrollbar.alphabet.AlphabetScrollbarController
import com.roy93group.launcher.ui.view.scrollbar.hue.HueScrollbarController
import io.posidon.android.conveniencelib.Device
import io.posidon.android.conveniencelib.getNavigationBarHeight
import io.posidon.android.conveniencelib.units.dp
import io.posidon.android.conveniencelib.units.toPixels
import kotlin.math.abs


/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

@SuppressLint("AppCompatCustomView")
class ScrollbarIconView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ImageView(context, attrs) {

    var appDrawer: AppDrawer? = null

    val scrollBar = Scrollbar(context)

    private var currentWindow: PopupWindow? = null

    @Scrollbar.Orientation
    private var currentOrientation = Scrollbar.HORIZONTAL

    private var counter = 0
    private val countDetectMoving = 10

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e: MotionEvent): Boolean {

        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                appDrawer?.open()
            }
            MotionEvent.ACTION_MOVE -> {
                counter++
                if (counter > countDetectMoving) {

                    if (counter % 10 == 0) {
                        C.vibrate(milliseconds = 10)
                    }

                    val d = abs(e.x / e.y)
//                val orientation = if (d > 1f) Scrollbar.HORIZONTAL else Scrollbar.VERTICAL
                    val orientation = Scrollbar.HORIZONTAL
                    if (currentWindow == null) {
                        showPopup(orientation)
                        currentOrientation = orientation
                    } else {
                        if ((d > 2f || d < 0.5f) && e.y * e.y + e.x * e.x > width * height && (e.y < 0 || currentOrientation == Scrollbar.VERTICAL) && currentOrientation != orientation) {
                            currentWindow?.dismiss()
                            showPopup(orientation)
                            currentOrientation = orientation
                        }
                        currentWindow?.let {
                            it.contentView.onTouchEvent(
                                it.contentView.makeMotionEventForPopup(
                                    e = e
                                )
                            )
                        }
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                currentWindow?.let {
                    it.contentView.onTouchEvent(
                        it.contentView.makeMotionEventForPopup(
                            e = e
                        )
                    )
                    it.dismiss()
                }
                counter = 0
            }
        }
        return true
    }

    private fun View.makeMotionEventForPopup(e: MotionEvent) = MotionEvent.obtain(
        e.downTime,
        e.eventTime,
        e.action,
        width - this@ScrollbarIconView.width + e.x,
        height - this@ScrollbarIconView.height + e.y,
        e.pressure,
        e.size,
        e.metaState,
        e.xPrecision,
        e.yPrecision,
        e.deviceId,
        e.edgeFlags,
    )

    private fun showPopup(@Scrollbar.Orientation orientation: Int) {
        currentWindow?.dismiss()
        scrollBar.orientation = orientation
        scrollBar.controller.updateTheme(context)
        scrollBar.background = ShapeDrawable(
            RoundRectShape(
                /* outerRadii = */ FloatArray(size = 8) {
                    resources.getDimension(R.dimen.round_large)
                },
                /* inset = */ null,
                /* innerRadii = */ null
            )
        ).apply {
            paint.color = C.COLOR_0
        }
        val p = 16.dp.toPixels(this)
        when (orientation) {
            Scrollbar.HORIZONTAL -> scrollBar.setPadding(p, 0, p, 0)
            Scrollbar.VERTICAL -> scrollBar.setPadding(0, p, 0, p)
        }
//        ResourcesCompat.getFont(context, R.font.jet_brains_mono)?.let {
//            scrollBar.typeface = it
//        }
//        scrollBar.typeface = ResourcesCompat.getFont(context, R.font.jet_brains_mono)
        val location = IntArray(2)
        getLocationOnScreen(location)
        currentWindow = PopupWindow(
            /* contentView = */ scrollBar,
            /* width = */ when (orientation) {
                Scrollbar.HORIZONTAL -> {
                    -Device.screenWidth(context) + (location[0] + this@ScrollbarIconView.width) * 2
                }
                else -> this@ScrollbarIconView.height
            },
            /* height = */ when (orientation) {
                Scrollbar.HORIZONTAL -> this@ScrollbarIconView.width
                else -> Device.screenHeight(context) * 2 / 3
            },
            /* focusable = */ true
        ).apply {
            setOnDismissListener {
                currentWindow = null
            }

            showAtLocation(
                /* parent = */
                this@ScrollbarIconView,
                /* gravity = */
                when (orientation) {
                    Scrollbar.HORIZONTAL -> Gravity.TOP
                    else -> Gravity.BOTTOM or Gravity.START
                },
                /* x = */
                when (orientation) {
                    Scrollbar.HORIZONTAL -> 0
                    else -> location[0]
                },
                /* y = */
                when (orientation) {
                    Scrollbar.HORIZONTAL -> location[1]
                    else -> Device.screenHeight(context) - location[1] - this@ScrollbarIconView.height + (appDrawer?.activity?.getNavigationBarHeight()
                        ?: 0)
                },
            )
        }
    }

    fun reloadController(settings: Settings) {
        when (settings.scrollbarController) {
            ScrollbarControllerSetting.SCROLLBAR_CONTROLLER_BY_HUE -> {
                if (scrollBar.controller !is HueScrollbarController) {
                    scrollBar.controller = HueScrollbarController(scrollBar)
                }
            }
            else -> {
                if (scrollBar.controller !is AlphabetScrollbarController) {
                    scrollBar.controller = AlphabetScrollbarController(scrollBar)
                }
            }
        }
    }
}
