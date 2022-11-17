package com.roy93group.launcher.ui.popup.listPopup

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.Keep

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
class ListPopupItem(
    val text: String,
    val description: String? = null,
    val icon: Drawable? = null,
    val isTitle: Boolean = false,
    val value: Any? = null,
    val onToggle: ((View, Boolean) -> Unit)? = null,
    val onClick: ((View) -> Unit)? = null,
)
