package com.roy93group.lookerupper.ui.viewHolders

import android.app.Activity
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.roy93group.cintalauncher.R
import com.roy93group.cintalauncher.providers.color.theme.ColorTheme
import com.roy93group.cintalauncher.providers.feed.suggestions.SuggestionsManager
import com.roy93group.cintalauncher.ui.acrylicBlur
import com.roy93group.cintalauncher.ui.popup.appItem.ItemLongPress
import com.roy93group.cintalauncher.ui.view.SeeThoughView
import com.roy93group.lookerupper.data.results.AppResult
import com.roy93group.lookerupper.data.results.SearchResult
import io.posidon.android.conveniencelib.getNavigationBarHeight

class AppSearchViewHolder(
    itemView: View,
    val activity: Activity
) : SearchViewHolder(itemView) {

    val icon = itemView.findViewById<ImageView>(R.id.icon_image)!!
    val label = itemView.findViewById<TextView>(R.id.icon_text)!!
    val card = itemView as CardView
    val blurBG = itemView.findViewById<SeeThoughView>(R.id.blur_bg)!!

    override fun onBind(result: SearchResult) {
        result as AppResult

        blurBG.drawable = BitmapDrawable(itemView.resources, acrylicBlur?.insaneBlur)

        val backgroundColor = ColorTheme.tintWithColor(ColorTheme.cardBG, result.getColor())
        card.setCardBackgroundColor(backgroundColor)
        label.text = result.title
        label.setTextColor(ColorTheme.titleColorForBG(backgroundColor))
        icon.setImageDrawable(result.icon)

        itemView.setOnClickListener {
            SuggestionsManager.onItemOpened(it.context, result.app)
            result.open(it)
        }
        itemView.setOnLongClickListener {
            ItemLongPress.onItemLongPress(
                it,
                backgroundColor,
                ColorTheme.titleColorForBG(backgroundColor),
                result.app,
                activity.getNavigationBarHeight(),
            )
            true
        }
    }
}