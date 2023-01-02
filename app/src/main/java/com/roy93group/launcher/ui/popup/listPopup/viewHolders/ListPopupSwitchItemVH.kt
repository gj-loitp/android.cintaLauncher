package com.roy93group.launcher.ui.popup.listPopup.viewHolders

import android.view.View
import com.roy93group.app.C
import com.roy93group.launcher.ui.feed.items.viewHolders.applyIfNotNull
import com.roy93group.launcher.ui.popup.listPopup.ListPopupItem
import kotlinx.android.synthetic.main.view_list_popup_switch_item.view.*

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ListPopupSwitchItemVH(itemView: View) : ListPopupVH(itemView) {
    override fun onBind(item: ListPopupItem) {

        itemView.text.apply {
            setTextColor(colorPrimary)
            text = item.text
        }
        itemView.description.apply {
            setTextColor(colorPrimary)
            text = item.description
            applyIfNotNull(view = this, value = item.description) { view, value ->
                view.text = value
            }
        }
        itemView.setOnClickListener {
            itemView.toggle.toggle()
        }

        itemView.toggle.apply {
            trackDrawable = C.generateTrackDrawable(colorBackground)
            thumbDrawable = C.generateThumbDrawable(
                context = itemView.context,
                color = colorPrimary
            )
            isChecked = (item.value as? Boolean) ?: false
            setOnCheckedChangeListener(item.onToggle)
        }

        itemView.icon.apply {
            setColorFilter(colorPrimary)
            applyIfNotNull(view = this, value = item.icon) { view, value ->
                view.setImageDrawable(value)
            }
        }
    }
}
