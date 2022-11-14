package com.roy93group.cintalauncher.ui.feed.items.viewHolders.suggestions

import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.cintalauncher.R
import com.roy93group.cintalauncher.providers.color.theme.ColorTheme
import com.roy93group.cintalauncher.data.items.LauncherItem
import com.roy93group.cintalauncher.providers.feed.suggestions.SuggestionsManager
import com.roy93group.cintalauncher.ui.acrylicBlur
import com.roy93group.cintalauncher.ui.popup.appItem.ItemLongPress
import com.roy93group.cintalauncher.ui.view.SeeThoughView

class SuggestionViewHolder(
    val card: CardView
) : RecyclerView.ViewHolder(card) {
    val icon = itemView.findViewById<ImageView>(R.id.icon_image)!!
    val label = itemView.findViewById<TextView>(R.id.icon_text)!!
    val blurBG = itemView.findViewById<SeeThoughView>(R.id.blur_bg)!!

    fun onBind(
        item: LauncherItem,
        navbarHeight: Int,
    ) {
        blurBG.drawable = BitmapDrawable(itemView.resources, acrylicBlur?.insaneBlur)

        val backgroundColor = ColorTheme.tintWithColor(ColorTheme.cardBG, item.getColor())
        card.setCardBackgroundColor(backgroundColor)
        label.text = item.label
        label.setTextColor(ColorTheme.titleColorForBG(backgroundColor))
        icon.setImageDrawable(item.icon)

        itemView.setOnClickListener {
            SuggestionsManager.onItemOpened(it.context, item)
            item.open(it.context.applicationContext, it)
        }
        itemView.setOnLongClickListener {
            ItemLongPress.onItemLongPress(
                it,
                backgroundColor,
                ColorTheme.titleColorForBG(backgroundColor),
                item,
                navbarHeight,
            )
            true
        }
    }
}