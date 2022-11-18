package com.roy93group.lookerupper.ui.viewHolders

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.roy93group.launcher.R
import com.roy93group.launcher.providers.feed.suggestions.SuggestionsManager
import com.roy93group.lookerupper.data.results.AppResult
import com.roy93group.lookerupper.data.results.SearchResult

/**
 * Updated by Loitp on 2022.12.18
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class AppSearchVH(
    itemView: View,
    val activity: Activity
) : SearchVH(itemView) {

    private val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)
    private val tvIconText: TextView = itemView.findViewById(R.id.tvIconText)
    val card = itemView as CardView

    override fun onBind(result: SearchResult) {
        result as AppResult

//        val backgroundColor = ColorTheme.tintWithColor(Color.GREEN, result.getColor())
//        card.setCardBackgroundColor(backgroundColor)
        tvIconText.text = result.title
//        tvIconText.setTextColor(ColorTheme.titleColorForBG(backgroundColor))
        ivIcon.setImageDrawable(result.icon)

        itemView.setOnClickListener {
            SuggestionsManager.onItemOpened(it.context, result.app)
            result.open(it)
        }
//        itemView.setOnLongClickListener {
//            ItemLongPress.onItemLongPress(
//                it,
//                backgroundColor,
//                ColorTheme.titleColorForBG(backgroundColor),
//                result.app,
//                activity.getNavigationBarHeight(),
//            )
//            true
//        }
    }
}
