package com.roy93group.cintalauncher.ui.popup.listPopup.viewHolders

import android.view.View
import android.widget.TextView
import com.roy93group.cintalauncher.R
import com.roy93group.cintalauncher.providers.color.theme.ColorTheme
import com.roy93group.cintalauncher.ui.feed.items.viewHolders.applyIfNotNull
import com.roy93group.cintalauncher.ui.popup.listPopup.ListPopupItem

class ListPopupTitleVH(itemView: View) : ListPopupVH(itemView) {

    val text: TextView = itemView.findViewById(R.id.text)
    val description: TextView = itemView.findViewById(R.id.description)
    val separator: View = itemView.findViewById(R.id.separator)

    override fun onBind(item: ListPopupItem) {
        text.text = item.text
        description.text = item.description

        text.setTextColor(
            ColorTheme.adjustColorForContrast(
                ColorTheme.cardBG,
                ColorTheme.accentColor
            )
        )
        separator.setBackgroundColor(ColorTheme.cardHint)
        itemView.setOnClickListener(item.onClick)
        applyIfNotNull(view = description, value = item.description) { view, value ->
            view.text = value
            description.setTextColor(ColorTheme.cardDescription)
        }
    }
}
