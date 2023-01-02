package com.roy93group.launcher.ui.popup.listPopup.viewHolders

import android.view.View
import com.roy93group.launcher.ui.feed.items.viewHolders.applyIfNotNull
import com.roy93group.launcher.ui.popup.listPopup.ListPopupItem
import kotlinx.android.synthetic.main.view_list_popup_title.view.*

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ListPopupTitleVH(itemView: View) : ListPopupVH(itemView) {

    override fun onBind(item: ListPopupItem) {
        itemView.text.apply {
            text = item.text
            setTextColor(colorPrimary)
        }
        itemView.description.apply {
            text = item.description
            setTextColor(colorPrimary)
            applyIfNotNull(view = this, value = item.description) { view, value ->
                view.text = value
            }
        }

        itemView.setOnClickListener(item.onClick)
    }
}
