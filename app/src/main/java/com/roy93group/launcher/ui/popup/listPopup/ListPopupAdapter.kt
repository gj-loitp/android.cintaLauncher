package com.roy93group.launcher.ui.popup.listPopup

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R
import com.roy93group.launcher.ui.popup.listPopup.viewHolders.ListPopupItemVH
import com.roy93group.launcher.ui.popup.listPopup.viewHolders.ListPopupSwitchItemVH
import com.roy93group.launcher.ui.popup.listPopup.viewHolders.ListPopupTitleVH
import com.roy93group.launcher.ui.popup.listPopup.viewHolders.ListPopupVH

class ListPopupAdapter : RecyclerView.Adapter<ListPopupVH>() {

    override fun getItemViewType(i: Int): Int {
        return when {
            items[i].onToggle != null -> 2
            items[i].isTitle -> 1
            else -> 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPopupVH {
        return when (viewType) {
            1 -> ListPopupTitleVH(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_popup_title, parent, false)
            )
            2 -> ListPopupSwitchItemVH(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_popup_switch_item, parent, false)
            )
            else -> ListPopupItemVH(
                LayoutInflater.from(parent.context).inflate(R.layout.list_popup_item, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: ListPopupVH, i: Int) {
        holder.onBind(items[i])
    }

    override fun getItemCount() = items.size

    private var items: List<ListPopupItem> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<ListPopupItem>) {
        this.items = items
        notifyDataSetChanged()
    }
}
