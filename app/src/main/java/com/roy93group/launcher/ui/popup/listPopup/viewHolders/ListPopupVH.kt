package com.roy93group.launcher.ui.popup.listPopup.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.app.C
import com.roy93group.launcher.ui.popup.listPopup.ListPopupItem

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
abstract class ListPopupVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val colorPrimary = C.getColorPrimary()
    val colorBackground = C.getColorBackground()

    abstract fun onBind(item: ListPopupItem)
}
