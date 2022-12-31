package com.roy93group.launcher.ui.drawer.viewHolders

import android.graphics.Color
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.app.C
import com.roy93group.launcher.data.items.App
import com.roy93group.launcher.data.items.LauncherItem
import com.roy93group.launcher.providers.feed.suggestions.SuggestionsManager
import com.roy93group.launcher.ui.LauncherActivity
import com.roy93group.launcher.ui.drawer.AppDrawerAdapter
import com.roy93group.launcher.ui.drawer.AppDrawerAdapter.Companion.APP_ITEM
import com.roy93group.launcher.ui.feed.items.viewHolders.applyIfNotNull
import kotlinx.android.synthetic.main.view_app_card.view.*

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class AppViewHolder(
    val cardView: CardView
) : RecyclerView.ViewHolder(cardView)

class AppItem(val item: App) : AppDrawerAdapter.DrawerItem {
    override fun getItemViewType() = APP_ITEM
    override val label: String
        get() = item.label
}

fun bindAppViewHolder(
    launcherActivity: LauncherActivity,
    holder: AppViewHolder,
    item: LauncherItem,
    isFromSuggest: Boolean,
    isDisplayAppIcon: Boolean,
    isForceColorIcon: Boolean,
    isLastItem: Boolean,
) {
    val colorPrimary = C.getColorPrimary()
    val colorBackground = C.getColorBackground()

    holder.cardView.setCardBackgroundColor(colorBackground)

    val banner = (item as? App)?.getBanner()

    holder.itemView.ivIcon.apply {
        if (isDisplayAppIcon) {
            setImageDrawable(item.icon)
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

    holder.itemView.tvIconText.apply {
        text = item.label
        setTextColor(colorPrimary)
    }
    holder.itemView.tvLineTitle.apply {
        setTextColor(colorPrimary)
        applyIfNotNull(
            view = this,
            value = banner?.title,
            block = TextView::setText
        )
    }

    holder.itemView.tvLineDescription.apply {
        setTextColor(colorPrimary)
        if (banner?.text?.trim().isNullOrEmpty()) {
            isVisible = false
        } else {
            isVisible = true
            applyIfNotNull(
                view = this,
                value = banner?.text,
                block = TextView::setText
            )
        }
    }

    holder.itemView.setOnClickListener {
        SuggestionsManager.onItemOpened(context = it.context, item = item)
        item.open(context = it.context.applicationContext, view = it)
    }

    holder.itemView.setOnLongClickListener {
        C.vibrate(500L)
        C.launchAppOption(
            activity = launcherActivity,
            item = item,
            isCancelableFragment = true,
            onDismiss = {
            }
        )
        return@setOnLongClickListener false
    }

    holder.itemView.vSpace.isVisible = isLastItem
}
