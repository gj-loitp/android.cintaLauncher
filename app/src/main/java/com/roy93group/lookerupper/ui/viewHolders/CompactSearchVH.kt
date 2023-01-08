package com.roy93group.lookerupper.ui.viewHolders

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import com.roy93group.ext.C
import com.roy93group.ext.getColorPrimary
import com.roy93group.launcher.providers.feed.suggestions.SuggestionsManager
import com.roy93group.launcher.ui.feed.items.viewHolders.applyIfNotNull
import com.roy93group.lookerupper.data.results.CompactAppResult
import com.roy93group.lookerupper.data.results.CompactResult
import com.roy93group.lookerupper.data.results.SearchResult
import kotlinx.android.synthetic.main.view_search_result_compact.view.*

/**
 * Updated by Loitp on 2022.12.18
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class CompactSearchVH(
    itemView: View,
    val activity: Activity
) : SearchVH(itemView) {
    private val colorPrimary = getColorPrimary()
//    private val colorBackground = C.getColorBackground()

    override fun onBind(
        result: SearchResult,
        isDisplayAppIcon: Boolean,
        isForceColorIcon: Boolean,
    ) {
        result as CompactResult

        itemView.text.apply {
            setTextColor(colorPrimary)
            text = result.title
        }
        itemView.subtitle.apply {
            setTextColor(colorPrimary)
            applyIfNotNull(
                view = itemView.subtitle,
                value = result.subtitle,
                block = TextView::setText
            )
        }

        itemView.icon.apply {
            setImageDrawable(result.icon)
            if (isDisplayAppIcon) {
                isVisible = true
                if (isForceColorIcon) {
                    setColorFilter(colorPrimary)
                } else {
                    setColorFilter(Color.TRANSPARENT)
                }
            } else {
                isVisible = false
            }
        }

        itemView.setOnClickListener {
            if (result is CompactAppResult) {
                SuggestionsManager.onItemOpened(context = it.context, item = result.app)
            }
            result.open(it)
        }
    }
}
