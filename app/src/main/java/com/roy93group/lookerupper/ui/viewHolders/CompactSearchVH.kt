package com.roy93group.lookerupper.ui.viewHolders

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
    val activity: Activity,
    private val isOnCard: Boolean
) : SearchVH(itemView) {

    val icon: ImageView = itemView.findViewById(R.id.icon)
    val text: TextView = itemView.findViewById(R.id.text)
    private val subtitle: TextView = itemView.findViewById(R.id.subtitle)

    override fun onBind(result: SearchResult) {
        result as CompactResult
        icon.setImageDrawable(result.icon)
        text.text = result.title
        applyIfNotNull(view = subtitle, value = result.subtitle, block = TextView::setText)
//        text.setTextColor(if (isOnCard) Color.RED else Color.GREEN)
//        subtitle.setTextColor(if (isOnCard) Color.RED else Color.RED)
        itemView.setOnClickListener {
            if (result is CompactAppResult) {
                SuggestionsManager.onItemOpened(it.context, result.app)
            }
            result.open(it)
        }
//        itemView.setOnLongClickListener(result.onLongPress?.let { { v -> it(v, activity) } })
    }
}
