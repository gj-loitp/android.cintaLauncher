package com.roy93group.launcher.ui.popup.listPopup.viewHolders

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.RippleDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.roy93group.launcher.R
import com.roy93group.launcher.providers.color.theme.ColorTheme
import com.roy93group.launcher.ui.feed.items.viewHolders.applyIfNotNull
import com.roy93group.launcher.ui.popup.listPopup.ListPopupItem

class ListPopupItemVH(itemView: View) : ListPopupVH(itemView) {
    val icon: ImageView = itemView.findViewById(R.id.icon)
    val text: TextView = itemView.findViewById(R.id.text)
    val description: TextView = itemView.findViewById(R.id.description)
    val ripple = RippleDrawable(ColorStateList.valueOf(0), null, ColorDrawable(0xffffffff.toInt()))

    init {
        itemView.background = ripple
    }

    override fun onBind(item: ListPopupItem) {
        text.text = item.text
        description.text = item.description

        text.setTextColor(ColorTheme.cardTitle)
        itemView.setOnClickListener(item.onClick)
        ripple.setColor(ColorStateList.valueOf(ColorTheme.accentColor and 0xffffff or 0x33000000))

        applyIfNotNull(view = description, value = item.description) { view, value ->
            view.text = value
            description.setTextColor(ColorTheme.cardDescription)
        }
        applyIfNotNull(view = icon, value = item.icon) { view, value ->
            view.setImageDrawable(value)
            view.imageTintList = ColorStateList.valueOf(ColorTheme.cardDescription)
        }
    }
}
