package com.roy93group.launcher.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R
import com.roy93group.launcher.data.feed.items.FeedItemAction
import com.roy93group.launcher.ui.feed.items.viewHolders.applyIfNotNull

/**
 * Updated by Loitp on 2022.12.16
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ActionsAdapter(
    private var actions: Array<FeedItemAction>
) : RecyclerView.Adapter<ActionsAdapter.ActionViewHolder>() {

    override fun getItemCount(): Int = actions.size

    class ActionViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val ivActionIcon: ImageView = itemView.findViewById(R.id.ivActionIcon)
        val tvActionText: TextView = itemView.findViewById(R.id.tvActionText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        return ActionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_feed_item_action, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActionViewHolder, i: Int) {
        val action = actions[i]
        holder.tvActionText.text = action.text
        applyIfNotNull(
            view = holder.ivActionIcon,
            value = action.icon,
            block = ImageView::setImageDrawable
        )
        holder.itemView.setOnClickListener(action.onTap)
    }
}
