package com.roy93group.launcher.ui.popup

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import io.posidon.android.conveniencelib.Device

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object PopupUtils {

    private var currentPopup: PopupWindow? = null

    fun dismissCurrent() = currentPopup?.dismiss()

    fun setCurrent(popup: PopupWindow) {
        dismissCurrent()
        popup.setOnDismissListener {
            currentPopup = null
        }
        currentPopup = popup
    }

    /**
     * @return Triple(x, y, gravity)
     */
    fun getPopupLocationFromView(
        view: View,
        navbarHeight: Int,
    ): Triple<Int, Int, Int> {

        val location = IntArray(2).also {
            view.getLocationOnScreen(it)
        }

        return getPopupLocation(
            context = view.context,
            x = location[0],
            y = location[1],
            width = view.measuredWidth,
            height = view.measuredHeight,
            navbarHeight = navbarHeight,
            offsetX = 0,
            offsetY = 0
        )
    }

    /**
     * @return Triple(x, y, gravity)
     */
    private fun getPopupLocation(
        context: Context,
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        navbarHeight: Int,
        offsetX: Int,
        offsetY: Int,
    ): Triple<Int, Int, Int> {

        var gravity: Int

        val screenWidth = Device.screenWidth(context)
        val screenHeight = Device.screenHeight(context)

        val x = if (x > screenWidth / 2) {
            gravity = Gravity.END
            screenWidth - x - width
        } else {
            gravity = Gravity.START
            x
        } + offsetX

        val y = if (y < screenHeight / 2) {
            gravity = gravity or Gravity.TOP
            y + height
        } else {
            gravity = gravity or Gravity.BOTTOM
            screenHeight - y + navbarHeight
        } + offsetY

        return Triple(first = x, second = y, third = gravity)
    }
}
