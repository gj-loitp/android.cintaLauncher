package com.roy93group.launcher.ui.feed.items.viewHolders.suggestions

import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R
import com.roy93group.launcher.data.items.LauncherItem
import com.roy93group.launcher.providers.color.theme.ColorTheme
import com.roy93group.launcher.providers.feed.suggestions.SuggestionsManager
import com.roy93group.launcher.ui.acrylicBlur
import com.roy93group.launcher.ui.popup.appItem.ItemLongPress
import com.roy93group.launcher.ui.view.SeeThoughView

@Suppress("unused")
class SuggestionVH(
    val card: CardView
) : RecyclerView.ViewHolder(card) {
    val icon: ImageView = itemView.findViewById(R.id.icon_image)
    val label: TextView = itemView.findViewById(R.id.icon_text)
    private val blurBG: SeeThoughView = itemView.findViewById(R.id.blurBg)

    fun onBind(
        item: LauncherItem,
        navbarHeight: Int,
    ) {
        blurBG.drawable =
            BitmapDrawable(/* res = */ itemView.resources, /* bitmap = */ acrylicBlur?.insaneBlur)

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
