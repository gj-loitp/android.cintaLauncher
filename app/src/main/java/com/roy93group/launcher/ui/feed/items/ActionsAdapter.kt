package com.roy93group.launcher.ui.feed.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.launcher.R
import com.roy93group.launcher.data.feed.items.FeedItemAction
import com.roy93group.launcher.ui.feed.items.viewHolders.applyIfNotNull

class ActionsAdapter(
    private var actions: Array<FeedItemAction>,
    private var actionButtonTextColor: Int
) : RecyclerView.Adapter<ActionsAdapter.ActionViewHolder>() {

    override fun getItemCount(): Int = actions.size

    class ActionViewHolder(actionButtonTextColor: Int, itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.ivActionIcon)
        val text: TextView = itemView.findViewById<TextView>(R.id.tvActionText).apply {
            setTextColor(actionButtonTextColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        return ActionViewHolder(
            actionButtonTextColor, LayoutInflater.from(parent.context)
                .inflate(R.layout.view_feed_item_action, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActionViewHolder, i: Int) {
        val action = actions[i]
        holder.text.text = action.text
        applyIfNotNull(view = holder.icon, value = action.icon, block = ImageView::setImageDrawable)
        holder.itemView.setOnClickListener(action.onTap)
    }
}
