package com.roy93group.lookerupper.ui.viewHolders

import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.loitp.core.ext.vibrate
import com.roy93group.app.C
import com.roy93group.launcher.providers.feed.suggestions.SuggestionsManager
import com.roy93group.lookerupper.data.results.AppResult
import com.roy93group.lookerupper.data.results.SearchResult
import kotlinx.android.synthetic.main.view_smart_suggestion.view.*

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

    private val colorPrimary = C.getColorPrimary()
    private val colorBackground = C.getColorBackground()

    override fun onBind(
        result: SearchResult,
        isDisplayAppIcon: Boolean,
        isForceColorIcon: Boolean,
    ) {
        result as AppResult

        itemView.card.setBackgroundColor(colorBackground)
        itemView.tvIconText.apply {
            setTextColor(colorPrimary)
            text = result.title
        }
        itemView.ivIcon.apply {
            if (isDisplayAppIcon) {
                isVisible = true
                setImageDrawable(result.icon)
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
            SuggestionsManager.onItemOpened(context = it.context, item = result.app)
            result.open(it)
        }

        itemView.setOnLongClickListener {
            it.context.vibrate(500L)
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
