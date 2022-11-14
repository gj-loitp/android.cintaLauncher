package com.roy93group.cintalauncher.ui.feedProfiles

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.roy93group.cintalauncher.R

class FeedFilterVH(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    val text: TextView = itemView.findViewById(R.id.text)
    val icon: ImageView = itemView.findViewById(R.id.icon)
    val card: CardView = itemView.findViewById(R.id.card)
}
