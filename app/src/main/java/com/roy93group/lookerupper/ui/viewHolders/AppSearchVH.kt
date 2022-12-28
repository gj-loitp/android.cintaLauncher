package com.roy93group.lookerupper.ui.viewHolders

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.roy93group.app.C
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
    val activity: AppCompatActivity
) : SearchVH(itemView) {

    private val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)
    private val tvIconText: TextView = itemView.findViewById<TextView>(R.id.tvIconText).apply {
        setTextColor(C.COLOR_0)
    }
    val card = itemView as CardView

    override fun onBind(
        result: SearchResult,
        isForceColorIcon: Boolean
    ) {
        result as AppResult
        tvIconText.text = result.title
        ivIcon.setImageDrawable(result.icon)
        if (isForceColorIcon) {
            ivIcon.setColorFilter(C.COLOR_0)
        } else {
            ivIcon.setColorFilter(Color.TRANSPARENT)
        }

        itemView.setOnClickListener {
            SuggestionsManager.onItemOpened(context = it.context, item = result.app)
            result.open(it)
        }

        itemView.setOnLongClickListener {
            C.vibrate(500L)
            C.launchAppOption(
                activity = activity,
                item = result.app,
                isCancelableFragment = true,
                onDismiss = {
                }
            )
            return@setOnLongClickListener false
        }
    }
}
