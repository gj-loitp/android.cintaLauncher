package com.roy93group.cintalauncher.ui.popup.listPopup.viewHolders

import android.view.View
import android.widget.TextView
import com.roy93group.cintalauncher.R
import com.roy93group.cintalauncher.providers.color.theme.ColorTheme
import com.roy93group.cintalauncher.ui.feed.items.viewHolders.applyIfNotNull
import com.roy93group.cintalauncher.ui.popup.listPopup.ListPopupItem

class ListPopupTitleViewHolder(itemView: View) : ListPopupViewHolder(itemView) {

    val text = itemView.findViewById<TextView>(R.id.text)
    val description = itemView.findViewById<TextView>(R.id.description)
    val separator = itemView.findViewById<View>(R.id.separator)

    override fun onBind(item: ListPopupItem) {
        text.text = item.text
        description.text = item.description

        text.setTextColor(ColorTheme.adjustColorForContrast(ColorTheme.cardBG, ColorTheme.accentColor))
        separator.setBackgroundColor(ColorTheme.cardHint)

        itemView.setOnClickListener(item.onClick)

        applyIfNotNull(description, item.description) { view, value ->
            view.text = value
            description.setTextColor(ColorTheme.cardDescription)
        }
    }
}