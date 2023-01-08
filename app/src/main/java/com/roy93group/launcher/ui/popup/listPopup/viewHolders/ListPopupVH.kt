package com.roy93group.launcher.ui.popup.listPopup.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.ext.C
import com.roy93group.ext.getColorBackground
import com.roy93group.ext.getColorPrimary
import com.roy93group.launcher.ui.popup.listPopup.ListPopupItem

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
abstract class ListPopupVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val colorPrimary = getColorPrimary()
    val colorBackground = getColorBackground()

    abstract fun onBind(item: ListPopupItem)
}
