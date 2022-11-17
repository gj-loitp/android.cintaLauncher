package com.roy93group.launcher.ui.feed.items.viewHolders.suggestions

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R
import com.roy93group.launcher.data.items.LauncherItem
import com.roy93group.launcher.providers.color.theme.ColorTheme
import com.roy93group.launcher.providers.feed.suggestions.SuggestionsManager
import com.roy93group.launcher.ui.popup.appItem.ItemLongPress

@Suppress("unused")
class SuggestionVH(
    val card: CardView
) : RecyclerView.ViewHolder(card) {
    private val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)
    private val tvIconText: TextView = itemView.findViewById(R.id.tvIconText)

    fun onBind(
        item: LauncherItem,
        navbarHeight: Int,
    ) {

        val backgroundColor = ColorTheme.tintWithColor(Color.RED, item.getColor())
        card.setCardBackgroundColor(backgroundColor)
        tvIconText.text = item.label
        tvIconText.setTextColor(ColorTheme.titleColorForBG(backgroundColor))
        ivIcon.setImageDrawable(item.icon)

        itemView.setOnClickListener {
            SuggestionsManager.onItemOpened(it.context, item)
            item.open(it.context.applicationContext, it)
        }
        itemView.setOnLongClickListener {
            ItemLongPress.onItemLongPress(
                view = it,
                backgroundColor = backgroundColor,
                textColor = ColorTheme.titleColorForBG(backgroundColor),
                item = item,
                navbarHeight = navbarHeight,
            )
            true
        }
    }
}
