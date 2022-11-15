package com.roy93group.lookerupper.ui.viewHolders

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.roy93group.launcher.R
import com.roy93group.launcher.providers.color.theme.ColorTheme
import com.roy93group.launcher.providers.feed.suggestions.SuggestionsManager
import com.roy93group.launcher.ui.popup.appItem.ItemLongPress
import com.roy93group.launcher.ui.view.SeeThoughView
import com.roy93group.lookerupper.data.results.AppResult
import com.roy93group.lookerupper.data.results.SearchResult
import io.posidon.android.conveniencelib.getNavigationBarHeight

class AppSearchVH(
    itemView: View,
    val activity: Activity
) : SearchVH(itemView) {

    val icon: ImageView = itemView.findViewById(R.id.ivIcon)
    val label: TextView = itemView.findViewById(R.id.tvIconText)
    val card = itemView as CardView
    private val blurBG: SeeThoughView = itemView.findViewById(R.id.blurBg)

    override fun onBind(result: SearchResult) {
        result as AppResult

//        blurBG.drawable = BitmapDrawable(itemView.resources, acrylicBlur?.insaneBlur)

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
