package com.roy93group.launcher.ui.feed.items.viewHolders.suggestions

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.ext.getDisplayAppIcon
import com.roy93group.ext.getForceColorIcon
import com.roy93group.launcher.R
import com.roy93group.launcher.data.items.LauncherItem
import com.roy93group.launcher.ui.drawer.viewHolders.AppViewHolder
import com.roy93group.launcher.ui.drawer.viewHolders.bindAppViewHolder

/**
 * Updated by Loitp on 2022.12.17
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class SuggestionsAdapter(
    val activity: AppCompatActivity,
) : RecyclerView.Adapter<AppViewHolder>() {

    private var items: List<LauncherItem> = emptyList()
    private var isDisplayAppIcon = activity.getDisplayAppIcon()
    private var isForceColorIcon = activity.getForceColorIcon()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AppViewHolder {
        return AppViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_app_card, parent, false) as CardView
        )
    }

    override fun onBindViewHolder(
        holder: AppViewHolder,
        i: Int
    ) {
        val item = items[i]
        bindAppViewHolder(
            activity = activity,
            holder = holder,
            item = item,
            isFromSuggest = true,
            isDisplayAppIcon = isDisplayAppIcon,
            isForceColorIcon = isForceColorIcon,
            isLastItem = false,
            index = i,
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(
        items: List<LauncherItem>,
        isDisplayAppIcon: Boolean,
        isForceColorIcon: Boolean,
    ) {
        this.items = items
        this.isDisplayAppIcon = isDisplayAppIcon
        this.isForceColorIcon = isForceColorIcon
        try {
            notifyItemRangeChanged(0, itemCount)
        } catch (e: Exception) {
            notifyDataSetChanged()
        }
    }
}
