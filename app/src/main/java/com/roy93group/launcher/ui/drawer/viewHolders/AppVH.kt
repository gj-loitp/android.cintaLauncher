package com.roy93group.launcher.ui.drawer.viewHolders

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.app.C
import com.roy93group.launcher.R
import com.roy93group.launcher.data.items.App
import com.roy93group.launcher.data.items.LauncherItem
import com.roy93group.launcher.providers.feed.suggestions.SuggestionsManager
import com.roy93group.launcher.ui.LauncherActivity
import com.roy93group.launcher.ui.drawer.AppDrawerAdapter
import com.roy93group.launcher.ui.drawer.AppDrawerAdapter.Companion.APP_ITEM
import com.roy93group.launcher.ui.feed.items.viewHolders.applyIfNotNull

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class AppViewHolder(
    val cardView: CardView
) : RecyclerView.ViewHolder(cardView) {
    val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)
    val tvIconText: TextView = itemView.findViewById<TextView>(R.id.tvIconText).apply {
        setTextColor(C.COLOR_0)
    }
    val ivIconSmall: ImageView = itemView.findViewById(R.id.ivIconSmall)
    val tvLineTitle: TextView = itemView.findViewById<TextView>(R.id.tvLineTitle).apply {
        setTextColor(C.COLOR_0)
    }
    val tvLineDescription: TextView =
        itemView.findViewById<TextView>(R.id.tvLineDescription).apply {
            setTextColor(C.COLOR_0)
        }
}

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
) {
    holder.cardView.setCardBackgroundColor(Color.TRANSPARENT)

    val banner = (item as? App)?.getBanner()
    if (isDisplayAppIcon) {
        if (banner?.text == null && banner?.title == null) {
            holder.ivIconSmall.isVisible = false
            holder.ivIcon.isVisible = true
            holder.ivIcon.setImageDrawable(item.icon)
        } else {
            holder.ivIconSmall.isVisible = true
            holder.ivIcon.isVisible = false
            holder.ivIconSmall.setImageDrawable(item.icon)
        }
    } else {
        holder.ivIcon.isVisible = false
        holder.ivIconSmall.isVisible = false
    }

    holder.tvIconText.text = item.label
    applyIfNotNull(
        view = holder.tvLineTitle,
        value = banner?.title,
        block = TextView::setText
    )
    applyIfNotNull(
        view = holder.tvLineDescription,
        value = banner?.text,
        block = TextView::setText
    )
//    if (isFromSuggest) {
//        holder.tvIconText.isVisible = true
//        holder.tvLineTitle.isVisible = false
//        holder.tvLineDescription.isVisible = false
//    } else {
//        applyIfNotNull(
//            view = holder.tvLineTitle,
//            value = banner?.title,
//            block = TextView::setText
//        )
//        applyIfNotNull(
//            view = holder.tvLineDescription,
//            value = banner?.text,
//            block = TextView::setText
//        )
//    }

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
}
