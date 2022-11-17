package com.roy93group.launcher.ui.popup.listPopup.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
class ListPopupItemVH(itemView: View) : ListPopupVH(itemView) {
    val icon: ImageView = itemView.findViewById(R.id.icon)
    val text: TextView = itemView.findViewById(R.id.text)
    val description: TextView = itemView.findViewById(R.id.description)
//    val ripple = RippleDrawable(ColorStateList.valueOf(0), null, ColorDrawable(0xffffffff.toInt()))

//    init {
//        itemView.background = ripple
//    }

    override fun onBind(item: ListPopupItem) {
        text.text = item.text
        description.text = item.description

//        text.setTextColor(Color.YELLOW)
        itemView.setOnClickListener(item.onClick)
//        ripple.setColor(ColorStateList.valueOf(Color.YELLOW and 0xffffff or 0x33000000))

        applyIfNotNull(view = description, value = item.description) { view, value ->
            view.text = value
//            description.setTextColor(Color.YELLOW)
        }
        applyIfNotNull(view = icon, value = item.icon) { view, value ->
            view.setImageDrawable(value)
//            view.imageTintList = ColorStateList.valueOf(Color.YELLOW)
        }
    }
}
