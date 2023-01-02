package com.roy93group.launcher.ui.popup

import android.widget.PopupWindow

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
}
