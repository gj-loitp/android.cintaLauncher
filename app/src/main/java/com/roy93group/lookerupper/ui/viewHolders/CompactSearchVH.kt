package com.roy93group.lookerupper.ui.viewHolders

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.launcher.providers.feed.suggestions.SuggestionsManager
import com.roy93group.launcher.ui.feed.items.viewHolders.applyIfNotNull
import com.roy93group.lookerupper.data.results.CompactAppResult
import com.roy93group.lookerupper.data.results.CompactResult
import com.roy93group.lookerupper.data.results.SearchResult

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

    val icon: ImageView = itemView.findViewById(R.id.icon)
    val text: TextView = itemView.findViewById<TextView>(R.id.text).apply {
        setTextColor(C.getColorPrimary())
    }
    private val subtitle: TextView = itemView.findViewById<TextView>(R.id.subtitle).apply {
        setTextColor(C.getColorPrimary())
    }

    override fun onBind(
        result: SearchResult,
        isDisplayAppIcon: Boolean,
        isForceColorIcon: Boolean,
    ) {
        result as CompactResult

        icon.setImageDrawable(result.icon)
        if (isDisplayAppIcon) {
            icon.isVisible = true
            if (isForceColorIcon) {
                icon.setColorFilter(C.getColorPrimary())
            } else {
                icon.setColorFilter(Color.TRANSPARENT)
            }
        } else {
            icon.isVisible = false
        }

        text.text = result.title
        applyIfNotNull(view = subtitle, value = result.subtitle, block = TextView::setText)

        itemView.setOnClickListener {
            if (result is CompactAppResult) {
                SuggestionsManager.onItemOpened(it.context, result.app)
            }
            result.open(it)
        }
    }
}
