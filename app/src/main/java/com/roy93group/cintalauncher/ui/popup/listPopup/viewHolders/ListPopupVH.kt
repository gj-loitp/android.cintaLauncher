package com.roy93group.cintalauncher.ui.popup.listPopup.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.cintalauncher.ui.popup.listPopup.ListPopupItem

abstract class ListPopupVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(item: ListPopupItem)
}
