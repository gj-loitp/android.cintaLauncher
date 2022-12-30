package com.roy93group.launcher.ui.popup.listPopup.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.launcher.ui.feed.items.viewHolders.applyIfNotNull
import com.roy93group.launcher.ui.popup.listPopup.ListPopupItem

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ListPopupSwitchItemVH(itemView: View) : ListPopupVH(itemView) {

    val icon: ImageView = itemView.findViewById(R.id.icon)
    val text: TextView = itemView.findViewById(R.id.text)
    val description: TextView = itemView.findViewById(R.id.description)
    private val toggle: SwitchCompat = itemView.findViewById(R.id.toggle)

    override fun onBind(item: ListPopupItem) {
        text.text = item.text
        description.text = item.description

        itemView.setOnClickListener {
            toggle.toggle()
        }

        toggle.trackDrawable = C.generateTrackDrawable(C.getColorBackground())
        toggle.thumbDrawable = C.generateThumbDrawable(
            context = itemView.context,
            color = C.getColorPrimary()
        )

        applyIfNotNull(view = description, value = item.description) { view, value ->
            view.text = value
        }
        applyIfNotNull(view = icon, value = item.icon) { view, value ->
            view.setImageDrawable(value)
        }
        toggle.isChecked = (item.value as? Boolean) ?: false
        toggle.setOnCheckedChangeListener(item.onToggle)
    }
}
